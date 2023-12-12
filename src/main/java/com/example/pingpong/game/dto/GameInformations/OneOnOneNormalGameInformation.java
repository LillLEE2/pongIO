package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.dto.GameElements.GameElement;
import com.example.pingpong.game.dto.GameObjects.*;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.GameRoomIdMessage;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.OneOnOneNormalGameDto;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.PaddleMoveData;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.dto.result.GameResultsId;
import com.example.pingpong.user.dto.UserQueue;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.*;

public class OneOnOneNormalGameInformation extends GameInformation {
    private String[] userSocketIds;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private MatchingResult matchingResult;
    private ScheduledFuture<?> timer;
    OneOnOneNormalGameInformation(MatchingResult matchingResult, DependencyConfiguration dependencyConfiguration) {
        super(dependencyConfiguration);
        this.userSocketIds = new String[2];
        settingUserSocketIds(matchingResult.getUserQueue());
        this.matchingResult = matchingResult;
        settingGameElement();
    }

    private void settingGameElement() {
        this.gameElement = new GameElement();
        this.gameElement.addBall();
        this.gameElement.addPaddle(5, 40, 2, 15);
        this.gameElement.addPaddle(93, 40, 2, 15);
    }

    private void settingUserSocketIds(ConcurrentLinkedQueue<UserQueue> userQueue) {
        this.userSocketIds = new String[2];
        Iterator<UserQueue> iterator = userQueue.iterator();
        for (int i = 0; i < 2; i++) {
            this.userSocketIds[i] = iterator.next().getSocketId();
        }
    }

    public void startGame(String roomName, String resultId) {
        System.out.println("startGame");
        Runnable positionUpdateTask = () -> positionUpdate(roomName, resultId);
        long initialDelay = 0;
        long period = 1000 / 60;
        this.timer = executorService.scheduleAtFixedRate(positionUpdateTask, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    private void positionUpdate(String roomName, String resultId) {
        System.out.println("/position_update/" + roomName);
        Ball ball = this.gameElement.getBallList().get(0);
        this.gameElement.getPaddleList().get(0).update();
        this.gameElement.getPaddleList().get(1).update();
        ball.update();
        ball.collision(gameElement);
        boolean gameFinished = gameScoreCheck();
        dependencyConfiguration.getMessagingTemplate().convertAndSend("/topic/position_update/" + roomName, new OneOnOneNormalGameDto(this.gameElement));
        if (gameFinished)
            finishGame(roomName, resultId);
    }

    public void paddleMove(SimpMessageHeaderAccessor accessor, PaddleMoveData data) {
        System.out.println("/paddle_move/" + data.getGameRoomId() + " " + accessor.getSessionId());
        if (accessor.getSessionId().equals(userSocketIds[0])) {
            System.out.println("left paddle move");
            this.gameElement.getPaddleList().get(0).setPaddleStatus(data.getPaddleStatus());
        } else {
            System.out.println("right paddle move");
            this.gameElement.getPaddleList().get(1).setPaddleStatus(data.getPaddleStatus());
        }
    }

    private boolean gameScoreCheck() {
        Ball ball = gameElement.getBallList().get(0);
        Integer leftScore = gameElement.getScore().getLeftScore();
        Integer rightScore = gameElement.getScore().getRightScore();

        if (ball.getPosX() < 0 || ball.getPosX() + ball.getRadius() * 2 > 100) {
            if (ball.getPosX() < 0) {
                this.gameElement.getScore().setRightScore(rightScore + 1);
            } else {
                this.gameElement.getScore().setLeftScore(leftScore + 1);
            }
            ball.resetPosition();
        }

        return this.gameElement.getScore().getLeftScore() == this.gameElement.getScore().getMaxScore() || this.gameElement.getScore().getRightScore() == this.gameElement.getScore().getMaxScore();
    }



    private void finishGame(String roomName, String resultId) {
        this.timer.cancel(true);
        String winnerSocketId = this.userSocketIds[this.gameElement.getScore().getLeftScore() == this.gameElement.getScore().getMaxScore() ? 0 : 1];
        dependencyConfiguration.getGameResultsService().finishGame(matchingResult, roomName, resultId);
        System.out.println("left score: " + this.gameElement.getScore().getLeftScore() + ", right score: " + this.gameElement.getScore().getRightScore());

        HashMap<String, String> map = new HashMap<>();
        map.put("resultId", resultId);
        dependencyConfiguration.getMessagingTemplate().convertAndSend("/topic/finish_game/" + roomName, map);
        System.out.println("game finished");
    }

    @Override
    public String getWinnerSocketId() {
        if (gameElement.getScore().getLeftScore() == this.gameElement.getScore().getMaxScore()) {
            return this.userSocketIds[0];
        } else if (gameElement.getScore().getRightScore() == this.gameElement.getScore().getMaxScore()) {
            return this.userSocketIds[1];
        } else {
            return null;
        }
    }

    @Override
    public String getLoserSocketId() {
        if (gameElement.getScore().getLeftScore() == this.gameElement.getScore().getMaxScore()) {
            return this.userSocketIds[1];
        } else if (gameElement.getScore().getRightScore() == this.gameElement.getScore().getMaxScore()) {
            return this.userSocketIds[0];
        } else {
            return null;
        }
    }

    @Override
    public Integer getWinnerScore() {
        return Math.max(gameElement.getScore().getLeftScore(), gameElement.getScore().getRightScore());
    }

    @Override
    public Integer getLoserScore() {
        return Math.min(gameElement.getScore().getLeftScore(), gameElement.getScore().getRightScore());
    }


    public void reStart(SimpMessageHeaderAccessor accessor
            , GameResultsId gameResultsId
            , ScheduledExecutorService executorService) {
        System.out.println("reStart");
        if (accessor.getSessionId().equals(userSocketIds[0])) {
            gameElement.getScore().setLeftScore(-1);
        } else {
            gameElement.getScore().setRightScore(-1);
        }
        if (gameElement.getScore().getLeftScore() == -1 && gameElement.getScore().getRightScore() == -1) {
            System.out.println("reStart game");
            String gameRoomId = gameResultsId.getRoomId();
            String resultId = gameResultsId.getResultId();

            dependencyConfiguration.getMessagingTemplate().convertAndSend("/topic/restart_game/" + gameRoomId, 0);
            gameElement.getScore().setLeftScore(0);
            gameElement.getScore().setRightScore(0);
            gameElement.getBallList().get(0).resetPosition();

            Runnable positionUpdateTask = () -> positionUpdate(gameRoomId, resultId);
            long initialDelay = 0;
            long period = 1000 / 60;
            ScheduledFuture<?> timer = executorService.scheduleAtFixedRate(positionUpdateTask, initialDelay, period, TimeUnit.MILLISECONDS);
        }
    }

    public void exitUser(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data) {
        String remainSocketId = accessor.getSessionId().equals(userSocketIds[0]) ? userSocketIds[1] : userSocketIds[0];
        System.out.println("exit user: " + accessor.getSessionId() + ", remain user: " + remainSocketId);
        dependencyConfiguration.getMessagingTemplate().convertAndSend("/topic/exit_game/" + data.getGameRoomId(), 0);
    }
}
