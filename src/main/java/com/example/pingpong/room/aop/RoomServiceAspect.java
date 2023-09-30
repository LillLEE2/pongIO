package com.example.pingpong.room.aop;

import com.example.pingpong.room.model.GameRoom;
import com.example.pingpong.room.repository.RoomRepository;
import com.example.pingpong.room.validate.RoomValidator;
import com.example.pingpong.user.service.UserService;
import com.example.pingpong.user.validate.UserValidator;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class RoomServiceAspect {

    private final UserValidator userValidator;
    private final RoomValidator roomValidator;
    @Before("execution(* com.example.pingpong.room.service.RoomService.createNewRoom(..)) && args(userNickname,..)")
    public void beforeCreateNewRoom(String userNickname) {
        userValidator.validateNickname(userNickname);
    }

    @Before(value = "execution(* com.example.pingpong.room.service.RoomService.joinGameRoom(..)) && args(roomId, userNickname,..)", argNames = "roomId,userNickname")
    public void beforeUserJoinRoom(String roomId, String userNickname) {
        userValidator.validateNickname(userNickname);
        roomValidator.validateRoomInfo(roomId);
        roomValidator.validateRoomUserCount(roomId);
    }

    @Before(value = "execution(* com.example.pingpong.room.service.RoomService.exitGameRoom(..)) && args(roomId, userNickname,..)", argNames = "roomId,userNickname")
    public void beforeUserExitRoom(String roomId, String userNickname) {
        userValidator.validateNickname(userNickname);
        roomValidator.validateRoomInfo(roomId);
        roomValidator.validateRoomUserCount(roomId);
    }
}
