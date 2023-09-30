package com.example.pingpong.room.validate;

import com.example.pingpong.room.model.GameRoom;
import com.example.pingpong.room.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RoomValidator {

    private final RoomRepository roomRepository;

    public void validateRoomInfo(String roomId) {
        Optional<GameRoom> byRoomId = roomRepository.findByRoomId(roomId);
        if (roomRepository.findByRoomId(roomId).isEmpty()) {
            throw new IllegalArgumentException("해당 방의 정보를 찾을수 없습니다.");
        }
    }

    public void validateRoomUserCount(String roomId) {
        GameRoom gameRoom = roomRepository.findByRoomId(roomId).get();
        if (gameRoom.getCurPlayersCnt() + 1 >= gameRoom.getMaxPlayers()) {
            throw new IllegalStateException("방의 정원이 다찾습니다.");
        }
    }
}
