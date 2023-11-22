package com.example.pingpong.room.controller;

import com.example.pingpong.room.dto.RoomsRequest;
import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.room.model.Rooms;
import com.example.pingpong.room.service.RoomService;
import com.example.pingpong.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/room")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RoomController {

    private final RoomService roomService;
    @PostMapping("/create/{userNickname}/{roomType}")
    public ResponseEntity<RoomsRequest> createRoom(@PathVariable String userNickname, @PathVariable RoomType roomType) {
        try {
            Rooms newRoom = roomService.createNewRoom(userNickname, null, roomType);
            RoomsRequest roomsRequest = RoomsRequest.builder().roomType(newRoom.getRoomType()).roomId(newRoom.getRoomId())
                    .ownerNickname(newRoom.getRoomOwnerNickname()).maxPlayers(newRoom.getMaxPlayers())
                    .gameMode(newRoom.getGameMode()).roomName(newRoom.getRoomName()).build();

            return new ResponseEntity<>(roomsRequest, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<RoomsRequest>> getAllRooms() {
        List<RoomsRequest> Rooms = roomService.findAllRooms();
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
