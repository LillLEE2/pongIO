package com.example.pingpong.room.dto;

import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.room.model.RoomType;
import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RoomsRequest {
    private RoomType roomType;
    private GameMode gameMode;
    private String roomId;
    private String ownerNickname;
    private String roomName;
    @Min(value = 1, message = "Max players must be at least 1")
    private Integer maxPlayers;

}
