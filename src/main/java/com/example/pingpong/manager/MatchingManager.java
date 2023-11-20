package com.example.pingpong.manager;


import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.room.model.RoomType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class MatchingManager {
	private final Map<RoomType, Function<Map<String, Objects>, MatchingResult>> generator;
	private final Object lock;

	public MatchingManager () {
		this.generator = new HashMap<>();
		generator.put(RoomType.ONE_ON_ONE, this::createOneToOne);
		generator.put(RoomType.SOLO, this::createSolo);
		generator.put(RoomType.MULTIPLAYER, this::createMulti);
		this.lock = new Object();
	}

	/**
	 * 타입별 매칭 결과 Object 생성
	 * @param type
	 * @param map
	 * @return
	 */
	public MatchingResult createMatchingResult(RoomType type, Map<String, Objects> map) {
		synchronized (lock) {
			Function<Map<String, Objects>, MatchingResult> resultFunction = generator.get(type);
			if ( resultFunction == null ) throw new IllegalArgumentException("check");
			MatchingResult apply = resultFunction.apply(map);
			return apply;
		}
	}

	private MatchingResult createSolo(Map<String, Objects> map) {
		return null;
	}
	private MatchingResult createOneToOne(Map<String, Objects> map) {
		return null;
	}
	private MatchingResult createMulti(Map<String, Objects> map) {
		return null;
	}
}
