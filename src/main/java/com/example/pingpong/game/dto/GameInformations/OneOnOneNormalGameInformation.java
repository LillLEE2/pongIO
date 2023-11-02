package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.dto.GameElements.OneOnOneGameElement;
import com.example.pingpong.game.dto.GameObjects.Ball;
import com.example.pingpong.game.dto.GameObjects.Paddle;
import com.example.pingpong.game.dto.GameObjects.PaddleMoveData;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameResultsService;
import com.example.pingpong.user.dto.UserQueue;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OneOnOneNormalGameInformation extends GameInformation {
    private int maxScore;
    private String[] userSocketIds;
    private int leftPaddleStatus;
    private int rightPaddleStatus;
    private OneOnOneGameElement gameElement;
    OneOnOneNormalGameInformation(MatchingResult matchingResult, SimpMessagingTemplate messagingTemplate, GameResultsService gameResultsService) {
        super(matchingResult, messagingTemplate, gameResultsService);
        this.maxScore = 2;
        this.userSocketIds = new String[2];
        settingUserSocketIds(matchingResult.getUserQueue());
        this.leftPaddleStatus = 0;
        this.rightPaddleStatus = 0;
        this.gameElement = (OneOnOneGameElement) super.getGameElement();
    }
    private void settingUserSocketIds(ConcurrentLinkedQueue<UserQueue> userQueue) {
        this.userSocketIds = new String[2];
        Iterator<UserQueue> iterator = userQueue.iterator();
        for (int i = 0; i < 2; i++) {
            this.userSocketIds[i] = iterator.next().getSocketId();
        }
    }

        public void positionUpdate(String roomName, String resultId) {
        System.out.println("/position_update/" + roomName);
        ballUpdate();
        ballCollision();
        boolean gameFinished = gameScoreCheck();
        messagingTemplate.convertAndSend("/topic/position_update/" + roomName, this.gameElement);
        if (gameFinished)
            finishGame(roomName, resultId);
    }

    public void paddleMove(SimpMessageHeaderAccessor accessor, PaddleMoveData data) {
        System.out.println("/paddle_move/" + data.getGameRoomId() + " " + accessor.getSessionId());
        if (accessor.getSessionId().equals(userSocketIds[0])) {
            System.out.println("left paddle move");
            this.leftPaddleStatus = data.getPaddleStatus();
        } else {
            System.out.println("right paddle move");
            this.rightPaddleStatus = data.getPaddleStatus();
        }
    }

    private void ballUpdate() {
        Ball ball = this.gameElement.getBallList().get(0);

        updatePaddlePosition(this.leftPaddleStatus, this.gameElement.getPaddleList().get(0));
        updatePaddlePosition(this.rightPaddleStatus, this.gameElement.getPaddleList().get(1));
        double velocityX = ball.getVelocityX();
        double velocityY = ball.getVelocityY();

        ball.setPosX(ball.getPosX() + velocityX);
        ball.setPosY(ball.getPosY() + velocityY);

        ball.setVelocityX(velocityX < 0 ? velocityX - 0.01 : velocityX + 0.01);
        ball.setVelocityY(velocityY < 0 ? velocityY - 0.01 : velocityY + 0.01);
    }

    private void ballCollision() {
        Ball ball = this.gameElement.getBallList().get(0);
        Paddle leftPaddle = this.gameElement.getPaddleList().get(0);
        Paddle rightPaddle = this.gameElement.getPaddleList().get(1);

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
                ball.setVelocityX(ball.getVelocityX() < 0 ? -ball.getVelocityX() : ball.getVelocityX());
            }
            ball.setVelocityY(deltaY * 0.2);
        }

        if (ball.getPosX() + ball.getRadius() > rightPaddle.getPosX() &&
                ball.getPosX() - ball.getRadius() < rightPaddle.getPosX() + rightPaddle.getWidth() &&
                ball.getPosY() + ball.getRadius() >= rightPaddle.getPosY() &&
                ball.getPosY() - ball.getRadius() <= rightPaddle.getPosY() + rightPaddle.getHeight()) {
            double deltaY = ball.getPosY() - (rightPaddle.getPosY() + rightPaddle.getHeight() / 2);
            if (ball.getPosX() - ball.getRadius() < rightPaddle.getPosX() + rightPaddle.getWidth() / 2) {
                ball.setVelocityX(ball.getVelocityX() > 0 ? -ball.getVelocityX() : ball.getVelocityX());
            }
            ball.setVelocityY(deltaY * 0.2);
        }
    }

    private void updatePaddlePosition(int status, Paddle paddle) {
        double speed = 3.0;
        if (status == 1 && paddle.getPosY() > 0) {
            paddle.setPosY(paddle.getPosY() - speed);
        } else if (status == 2 && paddle.getPosY() < 100 - paddle.getHeight()) {
            paddle.setPosY(paddle.getPosY() + speed);
        }
    }

    private boolean gameScoreCheck() {
        Ball ball = gameElement.getBallList().get(0);
        Integer leftScore = gameElement.getLeftScore();
        Integer rightScore = gameElement.getRightScore();

        if (ball.getPosX() < 0 || ball.getPosX() + ball.getRadius() * 2 > 100) {
            if (ball.getPosX() < 0) {
                this.gameElement.setRightScore(rightScore + 1);
            } else {
                this.gameElement.setLeftScore(leftScore + 1);
            }
            resetBallPosition();
        }

        return this.gameElement.getLeftScore() == this.maxScore || this.gameElement.getRightScore() == this.maxScore;
    }

    private void resetBallPosition() {
        Ball ball = gameElement.getBallList().get(0);
        ball.setPosX(50 - ball.getRadius());
        ball.setPosY(50 - ball.getRadius());

        double directX = Math.random() < 0.5 ? -1.0 : 1.0;
        double directY = Math.random() < 0.5 ? -1.0 : 1.0;

        ball.setVelocityX(0.5 * directX);
        ball.setVelocityY(0.25 * directY);
    }

    private void finishGame(String roomName, String resultId) {
        if (this.getTimer() != null) {
            this.getTimer().cancel(false);
        }
        System.out.println("game finished");
        String winnerSocketId = this.userSocketIds[this.gameElement.getLeftScore() == this.maxScore ? 0 : 1];
        gameResultsService.finishGame(roomName, resultId);
        System.out.println("left score: " + this.gameElement.getLeftScore() + ", right score: " + this.gameElement.getRightScore());
        messagingTemplate.convertAndSend("/topic/finish_game" + roomName, 0);
    }

    @Override
    public String getWinnerSocketId() {
        if (gameElement.getLeftScore() == maxScore) {
            return this.userSocketIds[0];
        } else if (gameElement.getRightScore() == maxScore) {
            return this.userSocketIds[1];
        } else {
            return null;
        }
    }

    @Override
    public String getLoserSocketId() {
        if (gameElement.getLeftScore() == maxScore) {
            return this.userSocketIds[1];
        } else if (gameElement.getRightScore() == maxScore) {
            return this.userSocketIds[0];
        } else {
            return null;
        }
    }

    @Override
    public Integer getWinnerScore() {
        return Math.max(gameElement.getLeftScore(), gameElement.getRightScore());
    }

    @Override
    public Integer getLoserScore() {
        return Math.min(gameElement.getLeftScore(), gameElement.getRightScore());
    }
}
