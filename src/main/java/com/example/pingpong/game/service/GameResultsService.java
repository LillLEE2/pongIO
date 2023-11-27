package com.example.pingpong.game.service;

import com.example.pingpong.game.dto.GameInformations.GameInformation;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameResultSaveStrategy.GameResultSaveStrategy;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
@AllArgsConstructor
public class GameResultsService {
    private final GameResultsRepository gameResultsRepository;
    private final UserService userService;
    private final GameResult1v1Service gameResult1v1Service;

    public GameResultsId saveParticipation(List<String> nickNameList, Rooms gameRoom) throws JsonProcessingException {
        String matchingRoomId = UUIDGenerator.Generate("MATCHING");
        Integer participantsCount = nickNameList.size();

        GameResultsId gameResultsId = createGameParticipantsId(matchingRoomId, gameRoom.getRoomId());
        GameResults gameResult = createGameResult(gameResultsId, gameRoom.getRoomType(), gameRoom.getGameMode(), participantsCount, nickNameList);

        GameResults results = gameResultsRepository.save(gameResult);
        return results.getId();
    }

    private GameResultsId createGameParticipantsId(String matchingRoomId, String roomId) {
        return new GameResultsId().builder()
                .resultId(matchingRoomId)
                .roomId(roomId)
                .build();
    }

    private GameResults createGameResult(GameResultsId gameResultsId, RoomType roomType, GameMode gameMode, Integer participantsCount, List<String> userList) throws JsonProcessingException {
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

    //Gamefinish 에서 GameResultUpdate 부분 분리
    private GameResults updateGameResultOnGameFinish(GameInformation gameRoom, String roomName, String resultId) {
        Integer winnerUserId = userService.getUserIdBySocketId(gameRoom.getWinnerSocketId());
        Integer loserUserId = userService.getUserIdBySocketId(gameRoom.getLoserSocketId());
        GameResultsId gameResultsId = GameResultsId.builder()
                .resultId(resultId)
                .roomId(roomName)
                .build();
        GameResults gameResults = gameResultsRepository.findById(gameResultsId);
        gameResults.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        gameResults.setWinnerScore(gameRoom.getWinnerScore());
        gameResults.setLowScore(gameRoom.getLoserScore());
        gameResults.setWinnerId(winnerUserId);
        gameResults.setLowScorerId(loserUserId);
        gameResultsRepository.save(gameResults);
        return gameResults;
    }

    public void finishGame(String roomName, String resultId) {
        //gameMode 별 분리 필요! 우선은 1대1 기준으로 처리
        GameInformation gameRoom = Global.GAME_ROOMS.get(roomName);
        GameResults gameResults = updateGameResultOnGameFinish(gameRoom, roomName, resultId);
        MatchingResult matchingResult = gameRoom.getMatchingResult();
        String roomType = matchingResult.getRoomType().getDescription();
        String gameMode = matchingResult.getGameMode().getDescription();
        List<String> userList = getUserList(gameResults);
        if (userList == null) {
            System.out.println("userList is null");
            return;
        }

        // 여기서부터 GameResultSaveStrategy 패턴 적용 예정, 우선은 더러운 if 문으로 땜빵 칠게요
        if (roomType.equals("ONE_ON_ONE")) {
            if (gameMode.equals("NORMAL")) {
                System.out.println("its one on one ");
                gameResult1v1Service.saveGameResult1v1(gameResults, userList, gameRoom);
            }
        } else if (roomType.equals("SOLO")) {
            if (gameMode.equals("RANKED")) {
                System.out.println("its solo ranked");
                //gameResultSoloRankedService.saveGameResultSoloRanked(gameResults, userList, gameRoom);
            }
        }
    }

    private List<String> getUserList(GameResults gameResults) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> userList;
        try {
            userList = objectMapper.readValue(gameResults.getSummary(), new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
            // 에러 처리 추가
        }
        return userList;
    }
}
