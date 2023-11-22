package com.example.pingpong.game.controller;

import com.example.pingpong.game.dto.GameInformations.GameInformationFactory;
import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameMatchingService;
import com.example.pingpong.room.model.RoomType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Test
    void matchingAndJoinRoom() {
    }

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameMatchingService gameMatchingService;

    @Mock
    private SimpMessageHeaderAccessor accessor;


    @Mock
    private SimpMessagingTemplate messagingTemplate;
    @Mock
    private GameInformationFactory gameInformationFactory;
    @Test
    public void test_matching_same_modes_and_types() throws JsonProcessingException {
        // Arrange
        GameController gameController = new GameController(
                messagingTemplate, gameMatchingService, gameInformationFactory
        );
        Map<String, String> payload = new HashMap<>();
        payload.put("mode", "NORMAL");
        payload.put("type", "ONE_ON_ONE");
        SimpMessageHeaderAccessor accessor = mock(SimpMessageHeaderAccessor.class);
        accessor.setSessionId("");
        MatchingResult matchingResult = new MatchingResult(true, GameMode.NORMAL, new ConcurrentLinkedQueue<>(), RoomType.ONE_ON_ONE);
        System.out.printf("======");
   }

}