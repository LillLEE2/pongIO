package com.example.pingpong.room.service;

import com.example.pingpong.room.model.GameRoom;
import com.example.pingpong.room.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    public List<GameRoom> findAllGameRooms() {
        return roomRepository.findAll();
    }
    public GameRoom createNewRoom(String userNickname) {
        String roomId = generateUniqueRoomId();
        String roomName = userNickname + "'s room";
        GameRoom room = setBaseRoomInfo(userNickname, roomId, roomName);
        return roomRepository.save(room);
    }
    public void deleteGameRoom(String roomId) {
        roomRepository.deleteByRoomId(roomId);
    }

    private static GameRoom setBaseRoomInfo(String userNickname, String roomId, String roomName) {
        return new GameRoom.Builder()
                .roomId(roomId)
                .roomName(roomName)
                .roomOwnerNickname(userNickname)
                .build();
    }

    private String generateUniqueRoomId() {
        String roomId;
        do { roomId = "ROOM_" + new Random().nextLong();
        } while( roomRepository.existsByRoomId(roomId) );
        return roomId;
    }

}
