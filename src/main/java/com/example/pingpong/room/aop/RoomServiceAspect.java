package com.example.pingpong.room.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.pingpong.room.validate.RoomValidator;
import com.example.pingpong.user.validate.UserValidator;

@Aspect
@Component
@RequiredArgsConstructor
public class RoomServiceAspect {

    private final UserValidator userValidator;
    private final RoomValidator roomValidator;

    private static final String CREATE_NEW_ROOM_POINTCUT = "execution(* com.example.pingpong.room.service.RoomService.createNewRoom(..)) && args(userNickname,..)";
    private static final String JOIN_GAME_ROOM_POINTCUT = "execution(* com.example.pingpong.room.service.RoomService.joinGameRoom(..)) && args(roomId, userNickname,..)";
    private static final String EXIT_GAME_ROOM_POINTCUT = "execution(* com.example.pingpong.room.service.RoomService.exitGameRoom(..)) && args(roomId, userNickname,..)";

    @Before(CREATE_NEW_ROOM_POINTCUT)
    public void beforeCreateNewRoom(String userNickname) throws Throwable {
        userValidator.validateNickname(userNickname);
    }

    @Before(JOIN_GAME_ROOM_POINTCUT)
    public void beforeUserJoinRoom(String roomId, String userNickname) throws Throwable {
        validateAnyRoom(roomId, userNickname);
    }

    @Before(EXIT_GAME_ROOM_POINTCUT)
    public void beforeUserExitRoom(String roomId, String userNickname) throws Throwable {
        validateAnyRoom(roomId, userNickname);
    }

    private void validateAnyRoom ( String roomId, String userNickname ) {
        userValidator.validateNickname(userNickname);
        roomValidator.validateRoomInfo(roomId);
        roomValidator.validateRoomUserCount(roomId);
    }
}
