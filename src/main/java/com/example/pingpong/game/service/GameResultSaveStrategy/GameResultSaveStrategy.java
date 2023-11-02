package com.example.pingpong.game.service.GameResultSaveStrategy;

import com.example.pingpong.game.dto.result.GameResults;

public interface GameResultSaveStrategy {
    void save(GameResults gameResults);
}
