package com.example.pingpong.game.dto.GameObjects;

public class PaddleMoveData {
    private String gameRoomId;
    private int paddleStatus;

    public String getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(String gameRoomId) {
        this.gameRoomId = gameRoomId;
    }

    public int getPaddleStatus() {
        return paddleStatus;
    }

    public void setPaddleStatus(int paddleStatus) {
        this.paddleStatus = paddleStatus;
    }



}
