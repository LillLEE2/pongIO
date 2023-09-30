package com.example.pingpong.room.service;

import com.example.pingpong.room.model.GameRoom;
import com.example.pingpong.room.repository.RoomRepository;
import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserStatus;
import com.example.pingpong.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public List<GameRoom> findAllGameRooms() {
        return roomRepository.findAll();
    }

    public GameRoom createNewRoom(String userNickname) {
        String roomId = generateUniqueRoomId();
        String roomName = userNickname + "'s room";
        GameRoom room = setBaseRoomInfo(userNickname, roomId, roomName);
        setUserStatusCode(userNickname);
        return roomRepository.save(room);
    }

    private void setUserStatusCode(String userNickname) {
        userRepository.findByNickname(userNickname).get().setStatusCode(UserStatus.PENDING);
    }

    public User joinGameRoom(String roomId, String userNickname) throws Exception {
        GameRoom gameRoom = roomRepository.findByRoomId(roomId).get();
        gameRoom.setCurPlayersCnt(gameRoom.getCurPlayersCnt() + 1);

        User user = userRepository.findByNickname(userNickname).get();
        user.setStatusCode(UserStatus.INGAME);
        return user;
    }

    public User exitGameRoom(String roomId, String userNickname) throws Exception{
        GameRoom gameRoom = roomRepository.findByRoomId(roomId).get();
        gameRoom.setCurPlayersCnt(gameRoom.getCurPlayersCnt() - 1);

        User user = userRepository.findByNickname(userNickname).get();
        user.setStatusCode(UserStatus.ONLINE);
        return user;
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
