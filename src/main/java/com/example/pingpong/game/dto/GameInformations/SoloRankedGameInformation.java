package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.dto.GameElements.SoloGameElement;
import com.example.pingpong.game.dto.GameObjects.*;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameResultsService;
import com.example.pingpong.user.dto.UserQueue;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;

public class SoloRankedGameInformation extends GameInformation {
    private String userSocketId;
    private int leftPaddleStatus;
    private SoloGameElement gameElement;

    public SoloRankedGameInformation(MatchingResult matchingResult, SimpMessagingTemplate messagingTemplate, GameResultsService gameResultsService) {
        super(matchingResult, messagingTemplate, gameResultsService);
        settingUserSocketIds(matchingResult.getUserQueue());
        this.leftPaddleStatus = 0;
        this.gameElement = (SoloGameElement) super.getGameElement();
    }

    private void settingUserSocketIds(ConcurrentLinkedQueue<UserQueue> userQueue) {
        Iterator<UserQueue> iterator = userQueue.iterator();
        this.userSocketId = iterator.next().getSocketId();
    }

    public void positionUpdate(String roomName, String resultId) {
        ballUpdate();
        ballCollision();
        boolean gameFinished = gameScoreCheck();
        messagingTemplate.convertAndSend("/topic/position_update/" + roomName, this.gameElement);
        if (gameFinished)
            finishGame(roomName, resultId);
    }

    private void ballUpdate() {
        updatePaddlePosition(this.leftPaddleStatus, this.gameElement.getPaddleList().get(0));

        for (Ball ball : this.gameElement.getBallList()) {
            ball.update(this.gameElement.getScore());
        }
    }

    private void ballCollision() {
//        Ball ball = this.gameElement.getBallList().get(0);
        Paddle leftPaddle = this.gameElement.getPaddleList().get(0);

        for (Ball ball : this.gameElement.getBallList()) {
            for (Item item : this.gameElement.getItemList()) {
                if (isBallCollidingWithItem(ball, item)) {
                    ball.applyItemEffect(item.getEffect());
                    this.gameElement.getItemList().remove(item);
                    break; // 볼이 한 번에 하나의 아이템만 획득하도록
                }
            }

            // 천장과 바닥 충돌 확인
            if (ball.getPosY() - ball.getRadius() < 0) {
                ball.setPosY(ball.getRadius());
                ball.setVelocityY(-ball.getVelocityY());
            } else if (ball.getPosY() + ball.getRadius() > 100) {
                ball.setPosY(100 - ball.getRadius());
                ball.setVelocityY(-ball.getVelocityY());
            }

            // 패들과 공의 충돌 확인
            if (ball.getPosX() - ball.getRadius() < leftPaddle.getPosX() + leftPaddle.getWidth() &&
                    ball.getPosX() + ball.getRadius() > leftPaddle.getPosX() &&
                    ball.getPosY() + ball.getRadius() >= leftPaddle.getPosY() &&
                    ball.getPosY() - ball.getRadius() <= leftPaddle.getPosY() + leftPaddle.getHeight()) {
                double deltaY = ball.getPosY() - (leftPaddle.getPosY() + leftPaddle.getHeight() / 2);
                if (ball.getPosX() + ball.getRadius() > leftPaddle.getPosX() + leftPaddle.getWidth() / 2) {
                    ball.setVelocityX(-ball.getVelocityX());
                    this.gameElement.setScore(this.gameElement.getScore() + 1);
                }
                ball.setVelocityY(deltaY * 0.2);
            }

            // 오른쪽 벽과 공의 충돌 확인
            if (ball.getPosX() + ball.getRadius() > 100) {
                ball.setPosX(100 - ball.getRadius());
                ball.setVelocityX(-ball.getVelocityX());
            }
        }
    }

    private boolean gameScoreCheck() {
//        Ball ball = gameElement.getBallList().get(0);
        Integer score = gameElement.getScore();
        Integer tempScore = score;
        for (Ball ball : this.gameElement.getBallList()) {
            tempScore -= 5;
            if (ball.getPosX() < 0) {
                return true;
            }
        }
        if (tempScore > 0) {
            gameElement.addBall();
            addRandomItem();
        }
        return false;
    }

    private void updatePaddlePosition(int status, Paddle paddle) {
        double speed = 3.0;
        if (status == 1 && paddle.getPosY() > 0) {
            paddle.setPosY(paddle.getPosY() - speed);
        } else if (status == 2 && paddle.getPosY() < 100 - paddle.getHeight()) {
            paddle.setPosY(paddle.getPosY() + speed);
        }
    }

    public void paddleMove(SimpMessageHeaderAccessor accessor, PaddleMoveData data) {
        System.out.println("/paddle_move/" + data.getGameRoomId() + " " + accessor.getSessionId());
        this.leftPaddleStatus = data.getPaddleStatus();
    }

    private boolean isBallCollidingWithItem(Ball ball, Item item) {
        double distance = Math.sqrt(Math.pow(ball.getPosX() - item.getPosX(), 2) + Math.pow(ball.getPosY() - item.getPosY(), 2));
        return distance <= ball.getRadius() + item.getRadius();
    }

    private void addRandomItem() {
        double randomX = 10 + Math.random() * (100 - 10);
        double randomY = Math.random() * 100;
        Item item = new Item(randomX, randomY);
        this.gameElement.getItemList().add(item);
    }

    private void finishGame(String roomName, String resultId) {
        if (this.getTimer() != null) {
            this.getTimer().cancel(false);
        }
        gameResultsService.finishGame(roomName, resultId);
        messagingTemplate.convertAndSend("/topic/finish_game/" + roomName, 0);
        System.out.println("game finished");
    }

    public void reStart(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data, ScheduledExecutorService executorService) {

    }

    public void exitUser(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data) {
    }
}

