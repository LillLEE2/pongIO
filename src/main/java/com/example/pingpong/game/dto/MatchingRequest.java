package com.example.pingpong.game.dto;


import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.user.dto.UserQueue;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchingRequest {

    UserQueue userQueue;
    RoomType roomType;
    GameMode gameMode;
}
