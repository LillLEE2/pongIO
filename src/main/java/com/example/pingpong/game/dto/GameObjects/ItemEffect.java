package com.example.pingpong.game.dto.GameObjects;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ItemEffect {
    FREEZE(3000),
    GRAVITY(8000);
//    SPIN,

    private final long durationMillis;

    ItemEffect(long durationMillis) {
        this.durationMillis = durationMillis;
    }
}
