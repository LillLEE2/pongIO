package com.example.pingpong.game.dto.GameElements;

import com.example.pingpong.game.dto.MatchingResult;
import org.springframework.stereotype.Component;

@Component
public class GameElementFactory {
    public static GameElement createGameElement(MatchingResult matchingResult) {
        switch (matchingResult.getRoomType()) {
            case ONE_ON_ONE:
                OneOnOneGameElement oneGameElement = new OneOnOneGameElement(matchingResult.getGameMode());
                return oneGameElement;
            case SOLO:
                OneOnOneGameElement soloGameElement = new OneOnOneGameElement(matchingResult.getGameMode());
            default:
                throw new IllegalArgumentException("Invalid room type: " + matchingResult.getRoomType());
        }
    }
}
