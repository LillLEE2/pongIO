package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameLogic.GameLogicService;
import com.example.pingpong.game.service.GameResultsService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
@AllArgsConstructor
public class GameInformationFactory {
    private final DependencyConfiguration dependencyConfiguration;
    public GameInformation createGameInformation(MatchingResult matchingResult) {
        switch (matchingResult.getRoomType()) {
            case ONE_ON_ONE:
                return createOneOnOneGameInformation(matchingResult);
            case SOLO:
                return createSoloGameInformation(matchingResult);
//            case MULTIPLAYER:
//                return createMultiplayerGameInformation(matchingResult);
            default:
                throw new IllegalArgumentException("Invalid room type: " + matchingResult.getRoomType());
        }
    }

    private GameInformation createOneOnOneGameInformation(MatchingResult matchingResult) {
        switch (matchingResult.getGameMode()) {
            case NORMAL:
                return new OneOnOneNormalGameInformation(matchingResult, dependencyConfiguration);
            default:
                throw new IllegalArgumentException("Invalid game mode: " + matchingResult.getGameMode());
        }
    }

    private GameInformation createSoloGameInformation(MatchingResult matchingResult) {
        switch (matchingResult.getGameMode()) {
//            case SOLO:
//                return new SoloSoloGameInformation(matchingResult, messagingTemplate, gameResultsService);
//            case RANKED:
//                return new SoloRankedGameInformation(matchingResult, messagingTemplate, gameResultsService);
            default:
                throw new IllegalArgumentException("Invalid game mode: " + matchingResult.getGameMode());
        }
    }
}
