package com.example.pingpong.game.controller;

import com.example.pingpong.game.dto.*;
import com.example.pingpong.game.dto.GameInformations.GameInformationFactory;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.GameRoomIdMessage;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.PaddleMoveData;
import com.example.pingpong.game.dto.result.GameResultsId;
import com.example.pingpong.game.service.GameMatchingService;
import com.example.pingpong.game.service.GameResultsService;
import com.example.pingpong.global.Global;
import com.example.pingpong.room.model.RoomType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Controller
@AllArgsConstructor
public class GameController {

	private final SimpMessagingTemplate messagingTemplate;
	private final GameMatchingService gameMatchingService;
	private final GameInformationFactory gameInformationFactory;
	private final GameResultsService gameResultsService;

	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	@MessageMapping("/matchingJoin")
	public void matchingAndJoinRoom(@Payload Map<String, String> payload, SimpMessageHeaderAccessor accessor) throws JsonProcessingException {
		GameMode mode = GameMode.getMode(payload.get("mode"));
		RoomType type = RoomType.getRoomType(payload.get("type"));
		MatchingResult matchingResult = gameMatchingService.matchingCheck(mode, type, accessor);
		if ( matchingResult.isMatching() ) {
			matchingSuccess( matchingResult );
		}
	}

    @MessageMapping("/cancelMatching")
    public void cancelMatching(SimpMessageHeaderAccessor accessor) {
        System.out.println("matching cancel accessor: " + accessor.getSessionId());
        gameMatchingService.matchingCancel(accessor);
    }

	@SendTo("/topic/matching-success")
    public void matchingSuccess(MatchingResult matchingResult) throws JsonProcessingException {
		//MATCHING_SUCCESS_DESTINATION
        GameResultsId gameResultsId = gameMatchingService.joinRooms(matchingResult);
		Global.GAME_ROOMS.put(gameResultsId.getRoomId(), gameInformationFactory.createGameInformation(matchingResult));
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonObject = objectMapper.createObjectNode();
		jsonObject.put("gameRoomId", gameResultsId.getRoomId());
		messagingTemplate.convertAndSend(Global.MATCHING_SUCCESS_DESTINATION, jsonObject);
		Global.GAME_ROOMS.get(gameResultsId.getRoomId()).startGame(gameResultsId.getRoomId(), gameResultsId.getResultId());
//		Runnable positionUpdateTask = () -> Global.GAME_ROOMS.get(gameResultsId.getRoomId()).positionUpdate(gameResultsId.getRoomId(), gameResultsId.getResultId());
//		long initialDelay = 0;
//		long period = 1000 / 60;
//		ScheduledFuture<?> timer = executorService.scheduleAtFixedRate(positionUpdateTask, initialDelay, period, TimeUnit.MILLISECONDS);
//		Global.GAME_ROOMS.get(gameResultsId.getRoomId()).setTimer(timer);
	}

    @MessageMapping("/paddle_move")
    public void paddleMove(SimpMessageHeaderAccessor accessor, PaddleMoveData data) {
		Global.GAME_ROOMS.get(data.getGameRoomId()).paddleMove(accessor, data);
    }

	@MessageMapping("/game_room_exit")
	public void gameRoomExit(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data) {
		String gameRoomId = data.getGameRoomId();
		System.out.println("/game_room_exit/" + gameRoomId);
		Global.GAME_ROOMS.get(gameRoomId).exitUser(accessor, data);
		Global.GAME_ROOMS.remove(gameRoomId);
	}

	@MessageMapping("/game_restart")
	public void gameReStart(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data) throws JsonProcessingException {
		String gameRoomId = data.getGameRoomId();
		System.out.println("/game_restart/" + gameRoomId);
		GameResultsId resultId = gameResultsService.getMatchResultId(data);
		// ToDo
		// 모든 사용자가 재시작을 눌렀을 때만 reStart 함수 호출하게 구현해줘

		Global.GAME_ROOMS.get(gameRoomId).reStart(accessor, resultId, executorService);
	}
}
