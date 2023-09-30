package com.example.pingpong.room.controller;

import com.example.pingpong.room.model.GameRoom;
import com.example.pingpong.room.service.RoomService;
import com.example.pingpong.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create/{userNickname}")
    public ResponseEntity<GameRoom> createRoom(@PathVariable String userNickname) {
        try {
            GameRoom newRoom = roomService.createNewRoom(userNickname);
            return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<GameRoom>> getAllGameRooms() {
        List<GameRoom> gameRooms = roomService.findAllGameRooms();
        return ResponseEntity.ok(gameRooms);
    }

    @PostMapping("/join/{roomId}/{userNickname}")
    public ResponseEntity<?> joinGameRoom(@PathVariable String roomId, @PathVariable String userNickname) {
        try {
            User user = roomService.joinGameRoom(roomId, userNickname);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/exit/{roomId}/{userNickname}")
    public ResponseEntity<?> exitGameRoom(@PathVariable String roomId, @PathVariable String userNickname) {
        try {
            User user = roomService.joinGameRoom(roomId, userNickname);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
