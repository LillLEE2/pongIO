package com.example.pingpong.room.model;

import lombok.Getter;

@Getter
public enum GameMode {
    ACCELERATED("가속모드"),
    NORMAL("일반모드"),
    RANKED("랭크모드");

    private final String description;

    GameMode(String description) {
        this.description = description;
    }

}
