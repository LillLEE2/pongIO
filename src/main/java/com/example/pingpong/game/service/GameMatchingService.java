package com.example.pingpong.game.service;

import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.global.Global;
import com.example.pingpong.game.model.GameMode;
import com.example.pingpong.room.model.RoomType;
import com.example.pingpong.room.model.Rooms;
import com.example.pingpong.room.service.RoomService;
import com.example.pingpong.user.dto.UserQueue;
import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserStatus;
import com.example.pingpong.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@AllArgsConstructor
public class GameMatchingService {

	private final Logger logger = LoggerFactory.getLogger(GameMatchingService.class);

	private final RoomService roomService;
	private final UserService userService;
	private final MatchingUserListGenerator matchingUserListGenerator;
	private final GameResultsService gameResultsService;

	public MatchingResult matchingCheck( GameMode mode, RoomType type, SimpMessageHeaderAccessor accessor ) {
		return isMatchingCheckByMode(mode, type, buildUserQueue(accessor));
	}

	public String joinRooms( MatchingResult matchingResult ) throws JsonProcessingException {
		List<String> userList = createUserList(matchingResult);
		Rooms room = createRooms(matchingResult);
		setUserStatusCodeInGame(userList);
		gameResultsService.saveParticipation(userList, room);
		return room.getRoomId();
	}


	private UserQueue buildUserQueue ( SimpMessageHeaderAccessor accessor ) {
		String socketId = accessor.getSessionId();
		String nickname = String.valueOf(accessor.getSessionAttributes().get("nickname"));
		return UserQueue.builder().nickname(nickname).socketId(socketId).roomId("").build();
	}

	private MatchingResult isMatchingCheckByMode ( GameMode mode, RoomType type, UserQueue userQueue ) {
		MatchingResult result = null;
		switch ( mode ) {
			case NORMAL: {
				Global.NORMAL_MODE_QUEUE.add(userQueue);
				if (Global.NORMAL_MODE_QUEUE.size() == 2) {
					ConcurrentLinkedQueue<UserQueue> queue = new ConcurrentLinkedQueue<>(Global.NORMAL_MODE_QUEUE);
					Global.NORMAL_MODE_QUEUE.clear();
					result = new MatchingResult(true, mode, queue, type);
				} else {
					result = new MatchingResult(false, mode, Global.NORMAL_MODE_QUEUE, type);
				}
			}
			case SOLO: {
				ConcurrentLinkedQueue<UserQueue> userQueues = new ConcurrentLinkedQueue<>();
				userQueues.add(userQueue);
				userQueues.add(createAiUserQueue());
				result = new MatchingResult(true, mode, userQueues, type);
			}
		/*
			TODO: 추후 처리
			case SPEED: break;
			case RANKED: break;
			default:
	     */
		}
		userService.updateUserStatus(userQueue.getNickname(), UserStatus.PENDING);
		return result;
	}

	private UserQueue createAiUserQueue () {
		User aiUser = userService.createAIAccount();
		return UserQueue.builder().nickname(aiUser.getNickname()).build();
	}

	private Rooms createRooms ( MatchingResult matchingResult ) {
		ConcurrentLinkedQueue<UserQueue> userQueue = matchingResult.getUserQueue();
		RoomType roomType = matchingResult.getRoomType();
		GameMode gameMode = matchingResult.getGameMode();
		return roomService.createMatchingRoom(matchingResult.getUserQueue().peek().getNickname(), gameMode, roomType, userQueue.size());
	}

	private List<String> createUserList (MatchingResult matchingResult) {
		return new ArrayList<String>() {
			{
				matchingResult.getUserQueue().forEach( el -> add ( el.getNickname() ));
			}
		};
	}

	private void setUserStatusCodeInGame ( List<String> userList ) {
		userList.forEach( user -> {
			userService.updateUserStatus(user, UserStatus.IN_GAME);
		});
	}
}
