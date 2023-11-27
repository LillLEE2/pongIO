package com.example.pingpong.game.dto.GameObjects.sendDataDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaddleMoveData {
    private String gameRoomId;
    private int paddleStatus;
}
