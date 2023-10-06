package com.example.pingpong.websocket;

import com.example.pingpong.game.model.GameInfomation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestGameController {
    private final SimpMessagingTemplate messagingTemplate;
    public TestGameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    private static List<String> list = new ArrayList<>();
    private static Map<String, List<String>> matchUsers = new HashMap<>();

    private static Map<String, GameInfomation> gameRooms = new HashMap<>();
    @MessageMapping("/matchingJoin")
    public void test(SimpMessageHeaderAccessor accessor) {
        System.out.println("matching join accessor: " + accessor.getSessionId());
        list.add(accessor.getSessionId());
        // 2명이면 게임방 만들고 2명에 이벤트 전송 후 list 초기화
        if (list.size() == 2) {
            System.out.println("matching success 2 User");
            matchingSuccess(list);
            list.clear();
        }
    }

    @MessageMapping("/cancelMatching")
    public void cancelMatching(SimpMessageHeaderAccessor accessor) {
        System.out.println("matching cancle aceessor: " + accessor.getSessionId());
        list.remove(accessor.getSessionId());
    }

    @SendTo("/topic/matching-success")
    public void matchingSuccess(List<String> userList) {
        String gameRoomId = String.valueOf(System.currentTimeMillis());
        System.out.println(gameRoomId);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("gameRoomId", gameRoomId);
        matchUsers.put(gameRoomId, userList);
        String destination = "/topic/matching-success";
        this.gameRooms.put(gameRoomId, new GameInfomation());
        messagingTemplate.convertAndSend(destination, jsonObject);
    }
}
