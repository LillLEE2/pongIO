package com.example.pingpong.chat.controller;

import com.example.pingpong.chat.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat")
    public Message handleChatMessage(@Payload Message message) {
        log.info("메시지 보내기");
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/chat")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor accessor) {
        log.info("유저 추가");
        accessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
