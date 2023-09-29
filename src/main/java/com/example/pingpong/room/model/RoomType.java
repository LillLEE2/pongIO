package com.example.pingpong.room.model;

import lombok.Getter;

@Getter
public enum RoomType {
    ONE_ON_ONE("1대1방"),
    MULTIPLAYER("다수방");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }

}
