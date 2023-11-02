package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.dto.GameElements.GameElement;
import com.example.pingpong.game.dto.GameElements.GameElementFactory;
import com.example.pingpong.game.dto.GameObjects.PaddleMoveData;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameResultsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Getter
@Setter
@AllArgsConstructor
@Component
public abstract class GameInformation {
    protected GameElement gameElement;
    protected GameResultsService gameResultsService;
    protected SimpMessagingTemplate messagingTemplate;
    protected MatchingResult matchingResult;
    protected ScheduledFuture<?> timer;
    public GameInformation(MatchingResult matchingResult, SimpMessagingTemplate messagingTemplate, GameResultsService gameResultsService) {
        this.gameResultsService = gameResultsService;
        this.gameElement = GameElementFactory.createGameElement(matchingResult);
        this.messagingTemplate = messagingTemplate;
        this.matchingResult = matchingResult;
        this.timer = null;
    }
    public abstract void positionUpdate(String roomName, String resultId);
    public abstract void paddleMove(SimpMessageHeaderAccessor accessor, PaddleMoveData data);

    public Integer getWinnerScore() {
        return 0;
    }
    public Integer getLoserScore() {
        return 0;
    }
    public String getWinnerSocketId() { return null; }
    public String getLoserSocketId() {
        return null;
    }

}
