package com.example.pingpong.manager.matching;

import com.example.pingpong.game.dto.MatchingRequest;
import com.example.pingpong.game.dto.MatchingResult;

public interface GameMatchingStrategy {
    MatchingResult match(MatchingRequest request);
}
