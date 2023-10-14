package com.example.pingpong.game.service;

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

	    public void saveParticipation( List<String> nickNameList, Rooms gameRoom) throws JsonProcessingException {
	        String matchingRoomId = UUIDGenerator.Generate("MATCHING");
		    Integer participantsCount = nickNameList.size();

		    GameResultsId gameResultsId = createGameParticipantsId(matchingRoomId, gameRoom.getRoomId());
		    GameResults gameResult = createGameResult(gameResultsId, gameRoom.getRoomType(), gameRoom.getGameMode(), participantsCount, nickNameList);

		    gameResultsRepository.save(gameResult);
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
}
