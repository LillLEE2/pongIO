package com.example.pingpong.user.model;

public enum UserStatus {
    ONLINE("온라인"),
    OFFLINE("오프라인"),
    INGAME("게임중"),
    PENDING("대기중");

    private final String description;


    UserStatus(String description) {
        this.description = description;
    }
}
