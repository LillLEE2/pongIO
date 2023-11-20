package com.example.pingpong.game.service;

import com.example.pingpong.game.dto.MatchingRequest;
import com.example.pingpong.game.dto.MatchingResult;
import com.example.pingpong.game.dto.result.GameResultsId;
import com.example.pingpong.global.Global;
import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.manager.MatchingManager;
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
import java.util.Iterator;
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
		MatchingManager manager = new MatchingManager(this);
		MatchingRequest matchingRequest = MatchingRequest.builder()
				.roomType(type)
				.userQueue(buildUserQueue(accessor))
				.gameMode(mode)
				.build();

		return manager.createMatchingResult(matchingRequest);
	}

	public void matchingCancel ( SimpMessageHeaderAccessor accessor ) {
		String socketId = accessor.getSessionId();
		Iterator<UserQueue> iterator = Global.NORMAL_MODE_QUEUE.iterator();
		while ( iterator.hasNext() ) {
			UserQueue userQueue = iterator.next();
			if ( userQueue.getSocketId().equals(socketId) ) {
				iterator.remove();
				break;
			}
		}
	}

	public GameResultsId joinRooms( MatchingResult matchingResult ) throws JsonProcessingException {
		List<String> userList = createUserList(matchingResult);
		Rooms room = createRooms(matchingResult);
		setUserStatusCodeInGame(userList);
		GameResultsId gameResultsId = gameResultsService.saveParticipation(userList, room);
		return gameResultsId;
	}


	private UserQueue buildUserQueue ( SimpMessageHeaderAccessor accessor ) {
		String socketId = accessor.getSessionId();
		String nickname = String.valueOf(accessor.getSessionAttributes().get("username"));
		return UserQueue.builder().nickname(nickname).socketId(socketId).roomId("").build();
	}

	public UserQueue createAiUserQueue () {
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
