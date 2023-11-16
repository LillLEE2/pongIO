package com.example.pingpong.websocket;

import com.example.pingpong.user.model.UserStatus;
import com.example.pingpong.user.repository.UserRepository;
import com.example.pingpong.user.service.UserService;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

//connect & disconnect는 내가 처리했다구
@Component
@AllArgsConstructor
public class GameHandler implements ChannelInterceptor {
    private final Logger logger = LoggerFactory.getLogger(GameHandler.class);
    private final UserService userService;

    //websocket 요청 전후로 처리할 로직을 정의 가능
    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        Map<String, Object> attributes = accessor.getSessionAttributes();
        String nickname = Optional.ofNullable((String) attributes.get("username")).orElseThrow( () -> new IllegalArgumentException("nickname is empty"));
        String sessionId = accessor.getSessionId();
        StompCommand stompCommand = accessor.getCommand();

        switch (Objects.requireNonNull(stompCommand)) {
            case CONNECT:
                userService.setUserSocketId(nickname, sessionId);
                userService.updateUserStatus(nickname, UserStatus.ONLINE);
                break;
            case DISCONNECT:
                userService.setUserSocketId(nickname, sessionId);
                userService.updateUserStatus(nickname, UserStatus.OFFLINE);
                break;
        }

        logger.debug("CURRENT CONNECT STATUS : {}, GUEST_ID : {}, SOCKET_ID : {}", stompCommand, nickname, sessionId);
    }
}
