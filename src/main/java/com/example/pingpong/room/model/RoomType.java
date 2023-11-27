package com.example.pingpong.room.model;

import lombok.Getter;

@Getter
public enum RoomType {
    ONE_ON_ONE("ONE_ON_ONE"),
    SOLO("SOLO"),
    MULTIPLAYER("MULTIPLAYER");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }

    public static RoomType getRoomType(String type) {
        return RoomType.valueOf(type);
    }
}
