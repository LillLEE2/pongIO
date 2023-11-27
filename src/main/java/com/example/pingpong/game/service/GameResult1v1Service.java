package com.example.pingpong.game.service;

import com.example.pingpong.game.dto.GameElements.GameElement;
import com.example.pingpong.game.dto.GameInformations.GameInformation;
import com.example.pingpong.game.dto.GameInformations.OneOnOneNormalGameInformation;
import com.example.pingpong.game.dto.result.GameResult1v1;
import com.example.pingpong.game.dto.result.GameResults;
import com.example.pingpong.game.repository.GameResult1v1Repository;
import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
public class GameResult1v1Service {
    final UserService userService;
    final GameResult1v1Repository gameResult1v1Repository;
    public void saveGameResult1v1(GameResults gameResults, List<String> userList, GameInformation gameroom) {
        GameResult1v1 gameResult1v1 = createGameResult1v1(gameResults, userList, gameroom);
        System.out.println("save gameResult1v1: " + gameResult1v1);
        gameResult1v1Repository.save(gameResult1v1);
    }
    private GameResult1v1 createGameResult1v1(GameResults gameResults, List<String> userList, GameInformation gameroom) {
        GameElement gameElement = gameroom.getGameElement();
        return GameResult1v1.builder()
                .result1v1Id(gameResults.getId().getRoomId())
                .resultId(gameResults.getId().getResultId())
                .gameMode(gameResults.getGameMode().getDescription())
                .player1Id(userService.findByNickname(userList.get(0)).get().getUserPk())
                .player2Id(userService.findByNickname(userList.get(1)).get().getUserPk())
                .winningPlayerId(gameResults.getWinnerId())
                .losingPlayerId(gameResults.getLowScorerId())
                .player1Score(gameElement.getScore().getLeftScore())
                .player2Score(gameElement.getScore().getRightScore())
                .matchDuration(Duration.between(gameResults.getStartTime().toLocalDateTime(), gameResults.getEndTime().toLocalDateTime()))
                .build();
    }
}
