package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameLogic.GameLogicService;
import com.example.pingpong.game.service.GameResultsService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameInformationFactory {
    private final GameResultsService gameResultsService;
    public GameInformation createGameInformation(MatchingResult matchingResult, SimpMessagingTemplate messagingTemplate) {
        switch (matchingResult.getRoomType()) {
            case ONE_ON_ONE:
                return createOneOnOneGameInformation(matchingResult, messagingTemplate);
//            case SOLO:
//                return createSoloGameInformation(matchingResult, messagingTemplate);
//            case MULTIPLAYER:
//                return createMultiplayerGameInformation(matchingResult);
            default:
                throw new IllegalArgumentException("Invalid room type: " + matchingResult.getRoomType());
        }
    }

    private GameInformation createOneOnOneGameInformation(MatchingResult matchingResult, SimpMessagingTemplate messagingTemplate) {
        switch (matchingResult.getGameMode()) {
            case NORMAL:
                return new OneOnOneNormalGameInformation(matchingResult, messagingTemplate, gameResultsService);
            default:
                throw new IllegalArgumentException("Invalid game mode: " + matchingResult.getGameMode());
        }
    }

//    private GameInformation createSoloGameInformation(MatchingResult matchingResult, SimpMessagingTemplate messagingTemplate) {
//        switch (matchingResult.getGameMode()) {
//            case SOLO:
//                return new SoloSoloGameInformation(matchingResult, messagingTemplate);
//            case RANKED:
//                return new SoloRankedGameInformation(matchingResult);
//            default:
//                throw new IllegalArgumentException("Invalid game mode: " + matchingResult.getGameMode());
//        }
//    }
}
