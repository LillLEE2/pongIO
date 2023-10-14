package com.example.pingpong.game.controller;

import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.dto.Ball;
import com.example.pingpong.game.dto.GameElement;
import com.example.pingpong.game.dto.GameInfomation;
import com.example.pingpong.game.dto.Paddle;
import com.example.pingpong.game.service.GameLogicService;
import com.example.pingpong.game.service.GameMatchingService;
import com.example.pingpong.global.Global;
import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.room.model.RoomType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Controller
@AllArgsConstructor
public class GameController {

	private final SimpMessagingTemplate messagingTemplate;
	private final GameMatchingService gameMatchingService;
    private final GameLogicService gameLogicService;
	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	@MessageMapping("/matchingJoin")
	public void matchingAndJoinRoom( @Payload Map<String, String> payload, SimpMessageHeaderAccessor accessor) throws JsonProcessingException {
		GameMode mode = GameMode.getMode(payload.get("mode"));
		RoomType type = RoomType.getRoomType(payload.get("type"));
		MatchingResult matchingResult = gameMatchingService.matchingCheck(mode, type, accessor);
		if ( matchingResult.isMatching() ) {
			matchingSuccess( matchingResult );
		}
	}

	@SendTo("/topic/matching-success")
    public void matchingSuccess(MatchingResult matchingResult) throws JsonProcessingException {
		//MATCHING_SUCCESS_DESTINATION
		String roomId = gameMatchingService.joinRooms(matchingResult);
		Global.GAME_ROOMS.put(roomId, new GameInfomation());
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonObject = objectMapper.createObjectNode();
		jsonObject.put("gameRoomId", roomId);
		Runnable positionUpdateTask = () -> {
            positionUpdate(roomId);
        };
		long initialDelay = 0;
		long period = 1000 / 60;
		ScheduledFuture<?> timer = executorService.scheduleAtFixedRate(positionUpdateTask, initialDelay, period, TimeUnit.MILLISECONDS);
		Global.GAME_ROOMS.get(roomId).setTimer(timer);
		messagingTemplate.convertAndSend(Global.MATCHING_SUCCESS_DESTINATION, jsonObject);
	}

	@MessageMapping("/positionUpdate")
    public void positionUpdate(String roomName) {
        System.out.println("/position_update/" + roomName);
        GameInfomation gameRoom = Global.GAME_ROOMS.get(roomName);
        ballUpdate(gameRoom);
        ballCollision(gameRoom);
        boolean gameFinished = gameScoreCheck(gameRoom);
        messagingTemplate.convertAndSend("/topic/position_update/" + roomName, gameRoom.getElement());
        if (gameFinished)
            finishGame(gameRoom, roomName);
    }

//    @MessageMapping("/paddleMove")
//    public void paddleMove(PaddleMoveRequest data) {
//        GameInformation gameRoom = gameService.getGameRoom(data.getRoomName());
//        if (gameRoom == null) {
//            return;
//        }
//        if (data.isOwner()) {
//            gameRoom.setLeftPaddleStatus(data.getPaddleStatus());
//        } else {
//            gameRoom.setRightPaddleStatus(data.getPaddleStatus());
//        }
//    }

    private void ballUpdate(GameInfomation gameRoom) {
        GameElement element = gameRoom.getElement();
        Ball ball = element.getBall();

        updatePaddlePosition(gameRoom.getLeftPaddleStatus(), element.getLeftPaddle());
        updatePaddlePosition(gameRoom.getRightPaddleStatus(), element.getRightPaddle());

        ball.setX(ball.getX() + gameRoom.getVelocityX());
        ball.setY(ball.getY() + gameRoom.getVelocityY());

        double velocityX = gameRoom.getVelocityX();
        double velocityY = gameRoom.getVelocityY();
        gameRoom.setVelocityX(velocityX < 0 ? velocityX - 0.01 : velocityX + 0.01);
        gameRoom.setVelocityY(velocityY < 0 ? velocityY - 0.01 : velocityY + 0.01);
    }

    private void ballCollision(GameInfomation gameRoom) {
        Ball ball = gameRoom.getElement().getBall();
        Paddle leftPaddle = gameRoom.getElement().getLeftPaddle();
        Paddle rightPaddle = gameRoom.getElement().getRightPaddle();

        // 천장과 바닥 충돌 확인
        if (ball.getY() - ball.getRadius() < 0) {
            ball.setY(ball.getRadius());
            gameRoom.setVelocityY(-gameRoom.getVelocityY());
        } else if (ball.getY() + ball.getRadius() > 100) {
            ball.setY(100 - ball.getRadius());
            gameRoom.setVelocityY(-gameRoom.getVelocityY());
        }

        // 패들과 공의 충돌 확인
        if (ball.getX() - ball.getRadius() < leftPaddle.getX() + leftPaddle.getWidth() &&
                ball.getX() + ball.getRadius() > leftPaddle.getX() &&
                ball.getY() + ball.getRadius() >= leftPaddle.getY() &&
                ball.getY() - ball.getRadius() <= leftPaddle.getY() + leftPaddle.getHeight()) {
            double deltaY = ball.getY() - (leftPaddle.getY() + leftPaddle.getHeight() / 2);
            if (ball.getX() + ball.getRadius() > leftPaddle.getX() + leftPaddle.getWidth() / 2) {
                gameRoom.setVelocityX(gameRoom.getVelocityX() < 0 ? -gameRoom.getVelocityX() : gameRoom.getVelocityX());
            }
            gameRoom.setVelocityY(deltaY * 0.2);
        }

        if (ball.getX() + ball.getRadius() > rightPaddle.getX() &&
                ball.getX() - ball.getRadius() < rightPaddle.getX() + rightPaddle.getWidth() &&
                ball.getY() + ball.getRadius() >= rightPaddle.getY() &&
                ball.getY() - ball.getRadius() <= rightPaddle.getY() + rightPaddle.getHeight()) {
            double deltaY = ball.getY() - (rightPaddle.getY() + rightPaddle.getHeight() / 2);
            if (ball.getX() - ball.getRadius() < rightPaddle.getX() + rightPaddle.getWidth() / 2) {
                gameRoom.setVelocityX(gameRoom.getVelocityX() > 0 ? -gameRoom.getVelocityX() : gameRoom.getVelocityX());
            }
            gameRoom.setVelocityY(deltaY * 0.2);
        }
    }

    private void updatePaddlePosition(int status, Paddle paddle) {
        double speed = 3.0;
        if (status == 1 && paddle.getY() > 0) {
            paddle.setY(paddle.getY() - speed);
        } else if (status == 2 && paddle.getY() < 100 - paddle.getHeight()) {
            paddle.setY(paddle.getY() + speed);
        }
    }

    private boolean gameScoreCheck(GameInfomation gameRoom) {
        Ball ball = gameRoom.getElement().getBall();
        Integer leftScore = gameRoom.getElement().getLeftScore();
        Integer rightScore = gameRoom.getElement().getRightScore();

        if (ball.getX() < 0 || ball.getX() + ball.getRadius() * 2 > 100) {
            if (ball.getX() < 0) {
                gameRoom.getElement().setRightScore(rightScore + 1);
            } else {
                gameRoom.getElement().setLeftScore(leftScore + 1);
            }
            resetBallPosition(gameRoom);
        }

        return gameRoom.getElement().getLeftScore() == gameRoom.getMaxScore() || gameRoom.getElement().getRightScore() == gameRoom.getMaxScore();
    }

    private void resetBallPosition(GameInfomation gameRoom) {
        Ball ball = gameRoom.getElement().getBall();
        ball.setX(50 - ball.getRadius());
        ball.setY(50 - ball.getRadius());

        double directX = Math.random() < 0.5 ? -1.0 : 1.0;
        double directY = Math.random() < 0.5 ? -1.0 : 1.0;

        gameRoom.setVelocityX(0.5 * directX);
        gameRoom.setVelocityY(0.25 * directY);
    }

    private void finishGame(GameInfomation gameRoom, String roomName) {
        if (gameRoom.getTimer() != null) {
            gameRoom.getTimer().cancel(false);
        }

        System.out.println("game finished");
        System.out.println("left score: " + gameRoom.getElement().getLeftScore() + ", right score: " + gameRoom.getElement().getRightScore());
        messagingTemplate.convertAndSend( "/topic/finish_game" + roomName, 0);
    }
}
