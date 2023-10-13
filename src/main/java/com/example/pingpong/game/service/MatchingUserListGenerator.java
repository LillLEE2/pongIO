package com.example.pingpong.game.service;

import com.example.pingpong.game.model.GameMode;
import com.example.pingpong.user.dto.UserQueue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

@Service
public class MatchingUserListGenerator {

	private final Map<GameMode, Function<ConcurrentLinkedQueue<UserQueue>, List<String>>> generator;

	public MatchingUserListGenerator () {
		this.generator = new HashMap<>();
		generator.put(GameMode.NORMAL, this::createNormalModeUserList);
		/*generator.put(GameMode.SPEED, null);
		generator.put(GameMode.RANKED, null);*/
	}

	public List<String> getMatchingUserList(GameMode mode, ConcurrentLinkedQueue<UserQueue> queue) {
		Function<ConcurrentLinkedQueue<UserQueue>, List<String>> gameModeListFunction = generator.get(mode);
		return gameModeListFunction.apply(queue);
	}

	private List<String> createNormalModeUserList(ConcurrentLinkedQueue<UserQueue> userQueues) {
		return new ArrayList<String>(){
			{
				userQueues.forEach(el -> {
					add(el.getNickname());
				});
			}
		};
	}
}
