package com.example.pingpong.user.validate;

import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidator {

    private final UserService userService;

    public void validateNickname(String userNickname) {
        if (userNickname == null || userNickname.trim().isEmpty()) {
            throw new IllegalArgumentException("유저의 닉네임이 비어 있습니다.");
        }
        if (userService.findByNickname(userNickname).isEmpty()) {
            throw new IllegalArgumentException("해당 유저 정보를 찾을수 없습니다.");
        }
    }
}
