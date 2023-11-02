package com.example.pingpong.game.service.GameLogic;

import com.example.pingpong.game.dto.GameElements.GameElement;
import com.example.pingpong.game.dto.GameInformations.GameInformation;
import com.example.pingpong.game.dto.GameObjects.Ball;
import com.example.pingpong.game.dto.GameObjects.Paddle;
import com.example.pingpong.game.dto.GameObjects.PaddleMoveData;
import com.example.pingpong.game.service.GameResultsService;
import com.example.pingpong.global.Global;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameLogicService {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameResultsService gameResultsService;

//    public void positionUpdate(String roomName, String resultId) {
//        System.out.println("/position_update/" + roomName);
//        GameInformation gameRoom = Global.GAME_ROOMS.get(roomName);
//        ballUpdate(gameRoom);
//        ballCollision(gameRoom);
//        boolean gameFinished = gameScoreCheck(gameRoom);
//        messagingTemplate.convertAndSend("/topic/position_update/" + roomName, gameRoom.getElement());
//        if (gameFinished)
//            finishGame(gameRoom, roomName, resultId);
//    }
//
//    public void paddleMove(SimpMessageHeaderAccessor accessor, PaddleMoveData data) {
//        System.out.println("/paddle_move/" + data.getGameRoomId() + " " + accessor.getSessionId());
//        GameInformation gameRoom = Global.GAME_ROOMS.get(data.getGameRoomId());
//        if (gameRoom == null) {
//            return;
//        }
//
//        if (accessor.getSessionId().equals(gameRoom.getUser(0))) {
//            System.out.println("left paddle move");
//            gameRoom.setLeftPaddleStatus(data.getPaddleStatus());
//        } else {
//            System.out.println("right paddle move");
//            gameRoom.setRightPaddleStatus(data.getPaddleStatus());
//        }
//    }
//
//    private void ballUpdate(GameInformation gameRoom) {
//        GameElement element = gameRoom.getElement();
//        Ball ball = element.getBall();
//
//        updatePaddlePosition(gameRoom.getLeftPaddleStatus(), element.getLeftPaddle());
//        if (gameRoom.getSingleMode()) {
//            updateAiPaddlePosition(gameRoom.getRightPaddleStatus(), element.getRightPaddle(), ball);
//        } else
//            updatePaddlePosition(gameRoom.getRightPaddleStatus(), element.getRightPaddle());
//        ball.setX(ball.getX() + gameRoom.getVelocityX());
//        ball.setY(ball.getY() + gameRoom.getVelocityY());
//
//        double velocityX = gameRoom.getVelocityX();
//        double velocityY = gameRoom.getVelocityY();
//        gameRoom.setVelocityX(velocityX < 0 ? velocityX - 0.01 : velocityX + 0.01);
//        gameRoom.setVelocityY(velocityY < 0 ? velocityY - 0.01 : velocityY + 0.01);
//    }

//    private void ballCollision(GameInformation gameRoom) {
//        Ball ball = gameRoom.getElement().getBall();
//        Paddle leftPaddle = gameRoom.getElement().getLeftPaddle();
//        Paddle rightPaddle = gameRoom.getElement().getRightPaddle();
//
//        // 천장과 바닥 충돌 확인
//        if (ball.getY() - ball.getRadius() < 0) {
//            ball.setY(ball.getRadius());
//            gameRoom.setVelocityY(-gameRoom.getVelocityY());
//        } else if (ball.getY() + ball.getRadius() > 100) {
//            ball.setY(100 - ball.getRadius());
//            gameRoom.setVelocityY(-gameRoom.getVelocityY());
//        }
//
//        // 패들과 공의 충돌 확인
//        if (ball.getX() - ball.getRadius() < leftPaddle.getX() + leftPaddle.getWidth() &&
//                ball.getX() + ball.getRadius() > leftPaddle.getX() &&
//                ball.getY() + ball.getRadius() >= leftPaddle.getY() &&
//                ball.getY() - ball.getRadius() <= leftPaddle.getY() + leftPaddle.getHeight()) {
//            double deltaY = ball.getY() - (leftPaddle.getY() + leftPaddle.getHeight() / 2);
//            if (ball.getX() + ball.getRadius() > leftPaddle.getX() + leftPaddle.getWidth() / 2) {
//                gameRoom.setVelocityX(gameRoom.getVelocityX() < 0 ? -gameRoom.getVelocityX() : gameRoom.getVelocityX());
//            }
//            gameRoom.setVelocityY(deltaY * 0.2);
//        }
//
//        if (ball.getX() + ball.getRadius() > rightPaddle.getX() &&
//                ball.getX() - ball.getRadius() < rightPaddle.getX() + rightPaddle.getWidth() &&
//                ball.getY() + ball.getRadius() >= rightPaddle.getY() &&
//                ball.getY() - ball.getRadius() <= rightPaddle.getY() + rightPaddle.getHeight()) {
//            double deltaY = ball.getY() - (rightPaddle.getY() + rightPaddle.getHeight() / 2);
//            if (ball.getX() - ball.getRadius() < rightPaddle.getX() + rightPaddle.getWidth() / 2) {
//                gameRoom.setVelocityX(gameRoom.getVelocityX() > 0 ? -gameRoom.getVelocityX() : gameRoom.getVelocityX());
//            }
//            gameRoom.setVelocityY(deltaY * 0.2);
//        }
//    }
//
//    private void updatePaddlePosition(int status, Paddle paddle) {
//        double speed = 3.0;
//        if (status == 1 && paddle.getY() > 0) {
//            paddle.setY(paddle.getY() - speed);
//        } else if (status == 2 && paddle.getY() < 100 - paddle.getHeight()) {
//            paddle.setY(paddle.getY() + speed);
//        }
//    }
//
//    private void updateAiPaddlePosition(int status, Paddle paddle, Ball ball) {
//        if (ball.getY() + ball.getRadius() < paddle.getY() + paddle.getHeight() / 2) {
//            // 공이 패들의 위쪽에 있으면, 패들을 위로 움직입니다.
//            double speed = 1.5;
//            if (paddle.getY() > 0) {
//                paddle.setY(paddle.getY() - speed);
//            }
//        } else {
//            // 공이 패들의 아래쪽에 있으면, 패들을 아래로 움직입니다.
//            double speed = 1.5;
//            if (paddle.getY() < 100 - paddle.getHeight()) {
//                paddle.setY(paddle.getY() + speed);
//            }
//        }
//    }
//
//    private boolean gameScoreCheck(GameInformation gameRoom) {
//        Ball ball = gameRoom.getElement().getBall();
//        Integer leftScore = gameRoom.getElement().getLeftScore();
//        Integer rightScore = gameRoom.getElement().getRightScore();
//
//        if (ball.getX() < 0 || ball.getX() + ball.getRadius() * 2 > 100) {
//            if (ball.getX() < 0) {
//                gameRoom.getElement().setRightScore(rightScore + 1);
//            } else {
//                gameRoom.getElement().setLeftScore(leftScore + 1);
//            }
//            resetBallPosition(gameRoom);
//        }
//
//        return gameRoom.getElement().getLeftScore() == gameRoom.getMaxScore() || gameRoom.getElement().getRightScore() == gameRoom.getMaxScore();
//    }
//
//    private void resetBallPosition(GameInformation gameRoom) {
//        Ball ball = gameRoom.getElement().getBall();
//        ball.setX(50 - ball.getRadius());
//        ball.setY(50 - ball.getRadius());
//
//        double directX = Math.random() < 0.5 ? -1.0 : 1.0;
//        double directY = Math.random() < 0.5 ? -1.0 : 1.0;
//
//        gameRoom.setVelocityX(0.5 * directX);
//        gameRoom.setVelocityY(0.25 * directY);
//    }
//
//    private void finishGame(GameInformation gameRoom, String roomName, String resultId) {
//        if (gameRoom.getTimer() != null) {
//            gameRoom.getTimer().cancel(false);
//        }
//        System.out.println("game finished");
//        String winnerSocketId = gameRoom.getUser(gameRoom.getElement().getLeftScore() == gameRoom.getMaxScore() ? 0 : 1);
//        gameResultsService.finishGame(roomName, resultId);
//        System.out.println("left score: " + gameRoom.getElement().getLeftScore() + ", right score: " + gameRoom.getElement().getRightScore());
//        messagingTemplate.convertAndSend( "/topic/finish_game" + roomName, 0);
//    }

}
