package com.example.pingpong.room.model;

import lombok.Getter;

@Getter
public enum RoomStatus {
    ONGOING("진행"),
    ENDED("종료"),
    WAIT("대기");

    private final String description;

    RoomStatus(String description) {
        this.description = description;
    }

}
