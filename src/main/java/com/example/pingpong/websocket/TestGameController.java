package com.example.pingpong.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestGameController {
    private static List<String> list = new ArrayList<>();
    private static Map<String, List<String>> gameRooms = new HashMap<>();

    @MessageMapping("/matchingJoin")
    public void test(SimpMessageHeaderAccessor accessor) {
        list.add(accessor.getSessionId());
        // 2명이면 게임방 만들고 2명에 이벤트 전송 후 list 초기화
        if (list.size() == 2) {
            String gameRoomId = String.valueOf(System.currentTimeMillis());
            gameRooms.put(gameRoomId, new ArrayList<>(list));
            matchingSuccess(gameRoomId);
            list.clear();
        }
        System.out.println("accessor : " + accessor.getSessionId());
    }
    @SendTo("/topic/1")
    public void matchingSuccess(String gameRoomId) {
        System.out.println("옥상으로 따라와");
    }
}
