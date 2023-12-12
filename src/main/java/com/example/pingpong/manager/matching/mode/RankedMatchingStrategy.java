package com.example.pingpong.manager.matching.mode;

import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.game.dto.MatchingRequest;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameMatchingService;
import com.example.pingpong.global.Global;
import com.example.pingpong.manager.matching.GameMatchingStrategy;
import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.user.dto.UserQueue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RankedMatchingStrategy implements GameMatchingStrategy {
    @Override
    public MatchingResult match(MatchingRequest request) {
        UserQueue userQueue = request.getUserQueue();
        GameMode mode = request.getGameMode();
        RoomType type = request.getRoomType();
        ConcurrentLinkedQueue<UserQueue> userQueues = new ConcurrentLinkedQueue<>();
        userQueues.add(userQueue);
        return new MatchingResult(true, mode, userQueues, type);
    }
}
