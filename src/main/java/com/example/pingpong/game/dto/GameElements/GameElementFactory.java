package com.example.pingpong.game.dto.GameElements;

import com.example.pingpong.game.dto.MatchingResult;
import org.springframework.stereotype.Component;

@Component
public class GameElementFactory {
//    public static GameElement createGameElement(MatchingResult matchingResult) {
//        switch (matchingResult.getRoomType()) {
//            case ONE_ON_ONE:
//                return createOneOnOneGameElement(matchingResult);
//            case SOLO:
//                return createSoloGameElement(matchingResult);
//            default:
//                throw new IllegalArgumentException("Invalid room type: " + matchingResult.getRoomType());
//        }
//    }

//    public static GameElement createOneOnOneGameElement(MatchingResult matchingResult) {
//        GameElement element = GameElement.builder().leftScore(0).rightScore(0).build();
//        element.addBall();
//        element.addPaddle(5, 40, 2, 15);
//        element.addPaddle(93, 40, 2, 15);
//        return element;
//    }

//    public static GameElement createSoloGameElement(MatchingResult matchingResult) {
//        GameElement element = GameElement.builder().build();
//        element.addBall();
//        element.addPaddle(5, 40, 2, 15);
//        switch (matchingResult.getGameMode()) {
//            case SOLO: {
//                element.setLeftScore(0);
//                element.setRightScore(0);
//                return element;
//            }
//            case RANKED: {
//                element.setScore(0);
//                return element;
//            }
//            default:
//                throw new IllegalArgumentException("Invalid game mode: " + matchingResult.getGameMode());
//        }
//    }
}
