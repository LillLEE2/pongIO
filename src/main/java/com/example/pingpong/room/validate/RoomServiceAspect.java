package com.example.pingpong.room.validate;

import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class RoomServiceAspect {

    private final UserService userService;

    @Before("execution(* com.example.pingpong.room.service.RoomService.createNewRoom(..)) && args(userNickname,..)")
    public void beforeCreateNewRoom(String userNickname) {
        if (userNickname == null || userNickname.trim().isEmpty()) {
            throw new IllegalArgumentException("유저의 닉네임 이 비어 있습니다.");
        }
        if (userService.findByNickname(userNickname).isEmpty()) {
            throw new IllegalArgumentException("해당 유저 정보를 찾을수 없습니다.");
        }
    }
}
