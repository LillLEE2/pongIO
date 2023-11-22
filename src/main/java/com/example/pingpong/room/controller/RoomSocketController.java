package com.example.pingpong.room.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class RoomSocketController {

    @MessageMapping("/joinRoom")
    public void joinRoom(@Payload Map<String, Object> payload) {

    }

}
