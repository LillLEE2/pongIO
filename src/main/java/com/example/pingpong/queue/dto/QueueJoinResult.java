package com.example.pingpong.queue.dto;

public class QueueJoinResult {
    private boolean success;
    private String message;

    public QueueJoinResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}