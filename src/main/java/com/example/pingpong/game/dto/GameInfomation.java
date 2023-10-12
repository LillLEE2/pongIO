package com.example.pingpong.game.dto;

import java.util.concurrent.ScheduledFuture;

public class GameInfomation {
    private String[] users;
    private int maxScore;
    private GameElement gameElement;
    private double velocityX;
    private double velocityY;
    private int leftPaddleStatus;
    private int rightPaddleStatus;
    private ScheduledFuture<?> timer;
    private boolean singleMode;
    public GameInfomation() {
        this.users = new String[2];
        this.gameElement = new GameElement();
        this.maxScore = 11;
        this.velocityX = 0.5;
        this.velocityY = 0.25;
        this.leftPaddleStatus = 0;
        this.rightPaddleStatus = 0;
        this.timer = null;
        this.singleMode = false;
    }
    public GameInfomation(String users[], int maxScore, GameElement gameElement, double velocityX, double velocityY, int leftPaddleStatus, int rightPaddleStatus) {
        this.users = users;
        this.maxScore = maxScore;
        this.gameElement = gameElement;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.leftPaddleStatus = leftPaddleStatus;
        this.rightPaddleStatus = rightPaddleStatus;
    }
    public String getUser(int index) {
        return users[index];
    }

    public void setUser(int index, String user) {
        this.users[index] = user;
    }
    public int getMaxScore() {
        return maxScore;
    }
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
    public GameElement getElement() {
        return gameElement;
    }

    public void setElement(GameElement element) {
        this.gameElement = element;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getLeftPaddleStatus() {
        return leftPaddleStatus;
    }

    public void setLeftPaddleStatus(int leftPaddleStatus) {
        this.leftPaddleStatus = leftPaddleStatus;
    }

    public int getRightPaddleStatus() {
        return rightPaddleStatus;
    }

    public void setRightPaddleStatus(int rightPaddleStatus) {
        this.rightPaddleStatus = rightPaddleStatus;
    }

    public ScheduledFuture<?> getTimer() {
        return timer;
    }

    public void setTimer(ScheduledFuture<?> timer) {
        this.timer = timer;
    }

    // 타이머를 취소하는 메서드 추가
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel(false);
        }
    }

    public void setSingleMode(boolean singleMode) {
        this.singleMode = singleMode;
    }

    public Boolean getSingleMode() {
        return singleMode;
    }
}
