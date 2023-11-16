package com.example.pingpong.game.dto;

import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.user.dto.UserQueue;
import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MatchingResult {
	private boolean success;
	@Getter
	private GameMode gameMode;
	@Getter
	private ConcurrentLinkedQueue<UserQueue> userQueue;
	@Getter
	private RoomType roomType;

	public MatchingResult(boolean success, GameMode gameMode, ConcurrentLinkedQueue<UserQueue> userQueue, RoomType roomType) {
		this.success = success;
		this.gameMode = gameMode;
		this.userQueue = userQueue;
		this.roomType = roomType;
	}

	public boolean isMatching() {
		return success;
	}
}
