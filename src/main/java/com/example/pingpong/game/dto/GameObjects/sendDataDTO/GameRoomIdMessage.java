package com.example.pingpong.game.dto.GameObjects.sendDataDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomIdMessage {
    private String gameRoomId;
    private String resultId;
}
