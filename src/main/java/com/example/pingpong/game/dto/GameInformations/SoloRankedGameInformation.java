package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.dto.GameElements.GameElement;
import com.example.pingpong.game.dto.GameObjects.*;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.GameRoomIdMessage;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.PaddleMoveData;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.SoloRankedDto;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameResultsService;
import com.example.pingpong.user.dto.UserQueue;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.*;

public class SoloRankedGameInformation extends GameInformation {
    private String userSocketId;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);;
    private MatchingResult matchingResult;
    private ScheduledFuture<?> timer;

    public SoloRankedGameInformation(MatchingResult matchingResult, DependencyConfiguration dependencyConfiguration) {
        super(dependencyConfiguration);
        settingUserSocketIds(matchingResult.getUserQueue());
        this.matchingResult = matchingResult;
        settingGameElement();
    }

    private void settingGameElement() {
        this.gameElement.addBall();
        this.gameElement.addPaddle(5, 40, 2, 15);
    }

    private void settingUserSocketIds(ConcurrentLinkedQueue<UserQueue> userQueue) {
        Iterator<UserQueue> iterator = userQueue.iterator();
        this.userSocketId = iterator.next().getSocketId();
    }

    public void startGame(String roomName, String resultId) {
        System.out.println("startGame");
        this.gameElement.getScore().startRankScoreUp();
        Runnable positionUpdateTask = () -> positionUpdate(roomName, resultId);
        long initialDelay = 0;
        long period = 1000 / 60;
        this.timer = executorService.scheduleAtFixedRate(positionUpdateTask, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    private void positionUpdate(String roomName, String resultId) {
        System.out.println("/position_update/" + roomName);
        ballUpdate();
        ballCollision();
        boolean gameFinished = gameScoreCheck();
        dependencyConfiguration.getMessagingTemplate().
                convertAndSend("/topic/position_update/" + roomName, new SoloRankedDto(this.gameElement));
        if (gameFinished)
            finishGame(roomName, resultId);
    }

    private void ballUpdate() {
        this.gameElement.getPaddleList().get(0).update();

        for (Ball ball : this.gameElement.getBallList()) {
            ball.update(this.gameElement.getScore().getRankScore());
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
            ball.collision(gameElement, leftPaddle);
        }
    }

    private boolean gameScoreCheck() {
        if (this.gameElement.getBallList().isEmpty())
            return true;
        ArrayList<Ball> removeBallList = new ArrayList<>();
        for (Ball ball : this.gameElement.getBallList()) {
            if (ball.getPosX() < 0) {
                removeBallList.add(ball);
            }
        }
        for (Ball ball : removeBallList) {
            this.gameElement.getBallList().remove(ball);
        }
//        if (tempScore > 0) {
//            gameElement.addBall();
//            gameElement.addRandomPositionItem();
//        }
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
        this.gameElement.getPaddleList().get(0).setPaddleStatus(data.getPaddleStatus());
    }

    private boolean isBallCollidingWithItem(Ball ball, Item item) {
        double distance = Math.sqrt(Math.pow(ball.getPosX() - item.getPosX(), 2) + Math.pow(ball.getPosY() - item.getPosY(), 2));
        return distance <= ball.getRadius() + item.getRadius();
    }

    private void finishGame(String roomName, String resultId) {
        System.out.println("game finished");
        this.timer.cancel(true);
        this.gameElement.getScore().stopRankScoreUp();
//        dependencyConfiguration.getGameResultsService().finishGame(matchingResult, roomName, resultId);
        dependencyConfiguration.getMessagingTemplate().convertAndSend("/topic/finish_game/" + roomName, 0);
    }

    public void reStart(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data, ScheduledExecutorService executorService) {
        System.out.println("reStart game");
        String gameRoomId = data.getGameRoomId();
        gameElement.getScore().setRankScore(0);
        gameElement.getBallList().clear();
        gameElement.getItemList().clear();
        gameElement.getScore().startRankScoreUp();
        gameElement.addBall();
        // 여기서 DB에 있는 resultId를 가져와야함
        // String gameResultsId = gameResultsService.getGameResultsId(gameRoomId);
        String gameResultsId = "1123";
        Runnable positionUpdateTask = () -> positionUpdate(gameRoomId, gameResultsId);
        long initialDelay = 0;
        long period = 1000 / 60;
        ScheduledFuture<?> timer = executorService.scheduleAtFixedRate(positionUpdateTask, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    public void exitUser(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data) {
    }
}

