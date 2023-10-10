package com.example.pingpong.websocket;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

//connect & disconnect는 내가 처리했다구
@Component
@RequiredArgsConstructor
public class GameHandler implements ChannelInterceptor {
    private final Logger logger = LoggerFactory.getLogger(GameHandler.class);
    //websocket 요청 전후로 처리할 로직을 정의 가능
    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        // connect시
        if (StompCommand.CONNECT == (accessor.getCommand())) {
            // sessionID매핑되는 유저 DB에서 업데이트
            logger.info("CONNECTED : " + sessionId);
        } else if (StompCommand.DISCONNECT == (accessor.getCommand())) {
            // sessionID매핑되는 유저 DB에서 업데이트
            logger.info("DISCONNECTED : " + sessionId);
        }

    }
}
