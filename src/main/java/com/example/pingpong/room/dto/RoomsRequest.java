package com.example.pingpong.room.dto;

import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.room.model.RoomType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public final class RoomsRequest {
    private RoomType roomType;
    private GameMode gameMode;
    @Min(value = 1, message = "Max players must be at least 1")
    private Integer maxPlayers;

    public RoomsRequest(RoomType roomType, GameMode gameMode, Integer maxPlayers) {
        this.roomType = roomType;
        this.gameMode = gameMode;
        this.maxPlayers = maxPlayers;
    }
}
