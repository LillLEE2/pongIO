package com.example.pingpong.user.model;

public enum UserStatus {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE"),
    IN_GAME("IN_GAME"),
    PENDING("PENDING");
    private final String description;
    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() { return this.description; }
}
