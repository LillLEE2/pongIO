package com.example.pingpong.room.controller;

import com.example.pingpong.room.dto.RoomsRequest;
import com.example.pingpong.room.model.Rooms;
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
    public ResponseEntity<Rooms> createRoom(@PathVariable String userNickname) {
        try {
            /** TODO: 생성 뒤에 들어가는 데이터 추가. */
            Rooms newRoom = roomService.createNewRoom(userNickname, null, null);
            return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Rooms>> getAllRooms() {
        List<Rooms> Rooms = roomService.findAllRooms();
        return ResponseEntity.ok(Rooms);
    }

    @PostMapping("/join/{roomId}/{userNickname}")
    public ResponseEntity<?> joinRooms(@PathVariable String roomId, @PathVariable String userNickname) {
        try {
            User user = roomService.joinRooms(roomId, userNickname);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/exit/{roomId}/{userNickname}")
    public ResponseEntity<?> exitRooms(@PathVariable String roomId, @PathVariable String userNickname) {
        try {
            User user = roomService.joinRooms(roomId, userNickname);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{roomId}/{userNickname}")
    public ResponseEntity<Rooms> updateRooms(@PathVariable String roomId, @PathVariable String userNickname, @RequestBody RoomsRequest updateRequest) {
        Rooms Rooms = roomService.updateRooms(roomId, userNickname, updateRequest);
        return ResponseEntity.ok(Rooms);
    }

}
