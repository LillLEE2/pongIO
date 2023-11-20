package com.example.pingpong.manager;


import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.game.dto.MatchingRequest;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.service.GameMatchingService;
import com.example.pingpong.global.Global;
import com.example.pingpong.manager.matching.GameMatchingStrategy;
import com.example.pingpong.manager.matching.mode.NormalMatchingStrategy;
import com.example.pingpong.manager.matching.mode.RankedMatchingStrategy;
import com.example.pingpong.manager.matching.mode.SoloMatchingStrategy;
import com.example.pingpong.manager.matching.mode.SpeedMatchingStrategy;
import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.user.dto.UserQueue;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

@Service
public class MatchingManager {

	private final Map<GameMode, GameMatchingStrategy> strategies = new HashMap<>();

	public MatchingManager(GameMatchingService gameMatchingService) {
		strategies.put(GameMode.SOLO, new SoloMatchingStrategy(gameMatchingService));
		strategies.put(GameMode.NORMAL, new NormalMatchingStrategy());
		strategies.put(GameMode.RANKED, new RankedMatchingStrategy());
		strategies.put(GameMode.SPEED, new SpeedMatchingStrategy());
	}

	public final MatchingResult createMatchingResult(MatchingRequest request) {
		GameMatchingStrategy strategy = strategies.get(request.getGameMode());
		if (strategy == null) {
			throw new IllegalArgumentException("Unsupported game mode");
		}
		return strategy.match(request);
	}
}
