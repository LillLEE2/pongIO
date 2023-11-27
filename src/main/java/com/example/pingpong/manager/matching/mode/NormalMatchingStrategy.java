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

public class NormalMatchingStrategy implements GameMatchingStrategy {
    @Override
    public MatchingResult match(MatchingRequest request) {
        UserQueue userQueue = request.getUserQueue();
        GameMode mode = request.getGameMode();
        RoomType type = request.getRoomType();

        Global.NORMAL_MODE_QUEUE.add(userQueue);
        if (Global.NORMAL_MODE_QUEUE.size() == 2) {
            ConcurrentLinkedQueue<UserQueue> queue = new ConcurrentLinkedQueue<>(Global.NORMAL_MODE_QUEUE);
            Global.NORMAL_MODE_QUEUE.clear();
            return new MatchingResult(true, mode, queue, type);
        } else {
            return new MatchingResult(false, mode, Global.NORMAL_MODE_QUEUE, type);
        }
    }
}
