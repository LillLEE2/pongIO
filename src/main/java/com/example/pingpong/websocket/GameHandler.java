package com.example.pingpong.websocket;

import com.example.pingpong.user.service.UserService;
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
public class GameHandler extends TextWebSocketHandler {

    //현재 연결된 클라이언트 배열
    private static List<WebSocketSession> list = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(GameHandler.class);
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("payload : " + payload);

        if ("matching".equals(payload)) {
            //매칭 버튼을 누르면 여기로 이벤트가 온다
            logger.error("확인");
        }
//
//        for(WebSocketSession sess: list) {
//            if (sess != session)
//                sess.sendMessage(message);
//        }
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("Welcome to the WebSocket server!"));
        logger.error("연결됨");
        // DB update
        list.add(session);
        //DB에 유저와 매핑 로직 추가 예정
    }

    //소켓 끊기면 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        list.remove(session);
        logger.error("연결 끊김");
        //DB에 유저 업데이트 로직 추가 예정
    }
}
