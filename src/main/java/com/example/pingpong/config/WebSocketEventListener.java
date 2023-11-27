package com.example.pingpong.config;

import com.example.pingpong.chat.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username == null || username.isBlank()) {
            log.info("User disconnected");
            return;
        }
        log.info("USER disconnected: {}", username);
        Message message = Message.builder()
                .messageType(Message.MessageType.LEAVE)
                .sender(username)
                .build();

        messageSendingOperations.convertAndSend("/topic/chat", message);
    }
}
