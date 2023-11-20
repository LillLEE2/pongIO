package com.example.pingpong.manager.matching.mode;

import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.game.dto.MatchingRequest;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameMatchingService;
import com.example.pingpong.manager.matching.GameMatchingStrategy;
import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.user.dto.UserQueue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SoloMatchingStrategy implements GameMatchingStrategy {

    private final GameMatchingService GameMatchingService;

    public SoloMatchingStrategy(GameMatchingService gameMatchingService) {
        GameMatchingService = gameMatchingService;
    }

    @Override
    public MatchingResult match(MatchingRequest request) {
        UserQueue userQueue = request.getUserQueue();
        GameMode mode = request.getGameMode();
        RoomType type = request.getRoomType();
        UserQueue aiUser = GameMatchingService.createAiUserQueue();

        ConcurrentLinkedQueue<UserQueue> userQueues = new ConcurrentLinkedQueue<>();
        userQueues.add(userQueue);
        userQueues.add(aiUser);
        return new MatchingResult(true, mode, userQueues, type);
    }
}
