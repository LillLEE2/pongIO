package com.example.pingpong.game.dto;

import lombok.Getter;

@Getter
public enum GameMode {
    SPEED("SPEED"),
    NORMAL("NORMAL"),
    SOLO("SOLO"),
    RANKED("RANKED");

    private final String description;

    GameMode(String description) {
        this.description = description;
    }

    public String getDescription() { return this.description; }
    public static GameMode getMode(String mode) {
        return GameMode.valueOf(mode);
    }
}
