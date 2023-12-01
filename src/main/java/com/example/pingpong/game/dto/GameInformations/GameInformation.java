package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.dto.GameElements.GameElement;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.GameRoomIdMessage;
import com.example.pingpong.game.dto.GameObjects.sendDataDTO.PaddleMoveData;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameResultsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Getter
@Setter
@AllArgsConstructor
@Component
public abstract class GameInformation {

    protected GameElement gameElement;
    protected DependencyConfiguration dependencyConfiguration;
    GameInformation(DependencyConfiguration dependencyConfiguration) {
        this.dependencyConfiguration = dependencyConfiguration;
        this.gameElement = new GameElement();
    }
    public abstract void startGame(String roomName, String resultId);
    public abstract void paddleMove(SimpMessageHeaderAccessor accessor, PaddleMoveData data);

    public abstract void reStart(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data, ScheduledExecutorService executorService);
    public abstract void exitUser(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data);
    public Integer getWinnerScore() {
        return 0;
    }
    public Integer getLoserScore() {
        return 0;
    }
    public String getWinnerSocketId() { return null; }
    public String getLoserSocketId() { return null; }
}
