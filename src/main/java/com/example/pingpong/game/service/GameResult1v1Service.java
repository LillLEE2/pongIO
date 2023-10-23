package com.example.pingpong.game.service;

import com.example.pingpong.game.dto.GameInfomation;
import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.game.dto.result.GameResult1v1;
import com.example.pingpong.game.dto.result.GameResults;
import com.example.pingpong.game.dto.result.GameResultsId;
import com.example.pingpong.game.repository.GameResult1v1Repository;
import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.user.model.User;
import com.example.pingpong.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameResult1v1Service {
    final UserService userService;
    final GameResult1v1Repository gameResult1v1Repository;
    public void saveGameResult1v1(GameResults gameResults, List<String> userList, GameInfomation gameroom) {
        GameResult1v1 gameResult1v1 = createGameResult1v1(gameResults, userList, gameroom);
        System.out.println("save gameResult1v1: " + gameResult1v1);
        gameResult1v1Repository.save(gameResult1v1);
    }
    private GameResult1v1 createGameResult1v1(GameResults gameResults, List<String> userList, GameInfomation gameroom) {
        //player id 가 pk 식별자 의도한건지 닉네임을 의도한건지?
        //player id 와 각 score를
        //winner, loser id 와 winner score, loser score 로 저장하는게 어떤지?

        return GameResult1v1.builder()
                .result1v1Id(gameResults.getId().getRoomId())
                .resultId(gameResults.getId().getResultId())
                .gameMode(gameResults.getGameMode().getDescription())
                .player1Id(userService.findByNickname(userList.get(0)).get().getUserPk())
                .player2Id(userService.findByNickname(userList.get(1)).get().getUserPk())
                .winningPlayerId(gameResults.getWinnerId())
                .losingPlayerId(gameResults.getLowScorerId())
                .player1Score(gameroom.getGameElement().getLeftScore())
                .player2Score(gameroom.getGameElement().getRightScore())
                .matchDuration(Duration.between(gameResults.getStartTime().toLocalDateTime(), gameResults.getEndTime().toLocalDateTime()))
                .build();
    }

}
