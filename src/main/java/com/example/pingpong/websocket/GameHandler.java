package com.example.pingpong.websocket;

import com.example.pingpong.queue.service.QueueService;
import com.example.pingpong.user.model.User;
import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class GameHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(GameHandler.class);

    private final QueueService queueService;
    private final UserService userService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("payload : " + payload);
        if ("normal_matching".equals(payload)) {
            String nickname = String.valueOf(session.getAttributes().get("nickname"));
            String addMessage = queueService.joinNormalQueue(nickname);
            if (addMessage.equals("normalModeMatchingSuccess")) {
                QueueService.normalQueue.clear();
                message = new TextMessage(addMessage);
            } else {
                logger.info("매칭 중");
            }
            logger.error("확인");
        } else if ("speed_matching".equals(payload)){ }

        session.sendMessage(message);

//        for(WebSocketSession sess: list) {
//            if (sess != session)
//                sess.sendMessage(message);
//        }
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("Welcome to the WebSocket server!"));
        String nickname = String.valueOf(session.getAttributes().get("nickname"));
        userService.setUserSocketId(nickname, session.getId());
        logger.error("연결됨");
    }

    //소켓 끊기면 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String nickname = String.valueOf(session.getAttributes().get("nickname"));
        userService.setUserSocketId(nickname, "");
        logger.error("연결 끊김");
    }
}
