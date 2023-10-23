package com.example.pingpong.game.service;

import com.example.pingpong.game.dto.GameInfomation;
import com.example.pingpong.global.Global;
import com.example.pingpong.user.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.game.dto.result.GameResults;
import com.example.pingpong.game.dto.result.GameResultsId;
import com.example.pingpong.game.repository.GameResultsRepository;
import com.example.pingpong.global.util.UUIDGenerator;
import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.room.model.Rooms;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GameResultsService {
	private final GameResultsRepository gameResultsRepository;
	private final UserService userService;
	private final GameResult1v1Service gameResult1v1Service;
	    public GameResultsId saveParticipation( List<String> nickNameList, Rooms gameRoom) throws JsonProcessingException {
	        String matchingRoomId = UUIDGenerator.Generate("MATCHING");
		    Integer participantsCount = nickNameList.size();

		    GameResultsId gameResultsId = createGameParticipantsId(matchingRoomId, gameRoom.getRoomId());
		    GameResults gameResult = createGameResult(gameResultsId, gameRoom.getRoomType(), gameRoom.getGameMode(), participantsCount, nickNameList);

			GameResults results = gameResultsRepository.save(gameResult);
			return results.getId();
		}

	    private GameResultsId createGameParticipantsId( String matchingRoomId, String roomId) {
	        return new GameResultsId().builder()
	                .resultId(matchingRoomId)
	                .roomId(roomId)
	                .build();
	    }

	    private GameResults createGameResult( GameResultsId gameResultsId, RoomType roomType, GameMode gameMode, Integer participantsCount, List<String> userList) throws JsonProcessingException {
		    ObjectMapper objectMapper = new ObjectMapper();
		    String summaryJson = objectMapper.writeValueAsString(userList);
			return GameResults.builder()
	                .id(gameResultsId)
			        .gameMode(gameMode)
			        .gameType(roomType)
			        .startTime(Timestamp.valueOf(LocalDateTime.now()))
			        .participantsCount(participantsCount)
			        .summary(summaryJson)
	                .build();
	    }

		public void finishGame(String roomName, String resultId) {
			//gameMode 별 분리 필요! 우선은 1대1 기준으로 처리
			ObjectMapper objectMapper = new ObjectMapper();
			GameInfomation gameRoom = Global.GAME_ROOMS.get(roomName);
			Integer winnerUserId = userService.getUserIdBySocketId(gameRoom.getWinnerSocketId());
			Integer loserUserId = userService.getUserIdBySocketId(gameRoom.getLoserSocketId());
			GameResultsId gameResultsId = GameResultsId.builder()
					.resultId(resultId)
					.roomId(roomName)
					.build();
			GameResults gameResults = gameResultsRepository.findById(gameResultsId);
			if (gameResults.getGameType().getDescription().equals("SOLO"))
				return;
			List<String> userList;
			try{
				userList = objectMapper.readValue(gameResults.getSummary(), new TypeReference<List<String>>() {});
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return ;
				// 에러 처리 추가
			}

			gameResults.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
			gameResults.setWinnerScore(gameRoom.getWinnerScore());
			gameResults.setLowScore(gameRoom.getLoserScore());
			gameResults.setWinnerId(winnerUserId);
			gameResults.setLowScorerId(loserUserId);
			gameResultsRepository.save(gameResults);
			System.out.println("gameType: " + gameResults.getGameType().getDescription() );
			if (gameResults.getGameType().getDescription().equals("ONE_ON_ONE")) {
				System.out.println("its one on one " );
				gameResult1v1Service.saveGameResult1v1(gameResults, userList, gameRoom);
			}
		}

}
