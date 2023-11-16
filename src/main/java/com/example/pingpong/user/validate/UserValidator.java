package com.example.pingpong.user.validate;

import com.example.pingpong.user.service.UserService;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
@AllArgsConstructor
public class UserValidator {

    private final UserService userService;

    public void validateNickname(String userNickname) {
        if ( StringUtils.isEmpty(userNickname) ) {
            throw new IllegalArgumentException("유저의 닉네임이 비어 있습니다.");
        }
//        if (userService.findByNickname(userNickname).isEmpty()) {
//            throw new IllegalArgumentException("해당 유저 정보를 찾을수 없습니다.");
//        }
    }
}
