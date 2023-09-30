package com.example.pingpong.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestHandler extends TextWebSocketHandler {

    //현재 연결된 클라이언트 배열
    private static List<WebSocketSession> list = new ArrayList<>();

    //소켓 연결 시 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("Welcome to the WebSocket server!"));
        list.add(session);
        //DB에 유저와 매핑 로직 추가 예정
    }

    //소켓 끊기면 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        list.remove(session);
        //DB에 유저 업데이트 로직 추가 예정
    }
}
