package com.example.pingpong.user.service;

import com.example.pingpong.global.Global;
import com.example.pingpong.global.util.UUIDGenerator;
import com.example.pingpong.user.controller.SignInRequest;
import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserRole;
import com.example.pingpong.user.model.UserStatus;
import com.example.pingpong.user.repository.UserRepository;
import com.example.pingpong.user.validate.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    public User authenticateUser(SignInRequest signInRequest) {
        String nickname = signInRequest.getNickname();
        if (nickname.contains("GUEST_")) {
            throw new IllegalArgumentException("닉네임에 'GUEST_'를 포함할 수 없습니다.");
        }
        if(userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        String password = signInRequest.getPassword();
        String email = signInRequest.getEmail();
        User user = User.builder().nickname(nickname)
                .userRole(UserRole.USER)
                .password(password)
                .email(email)
                .userStatus(UserStatus.ONLINE)
            .build();
        return userRepository.save(user);
    }

    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public Integer getUserIdBySocketId(String socketId) {
        System.out.println("getUserIdBySocketId : " + socketId);
        return userRepository.getUserIdBySocketId(socketId);
    }

    public Optional<User> login(String nickname, String password) {
        Optional<User> user = findByNickname(nickname);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            updateUserStatus(user.get().getNickname(), UserStatus.ONLINE);
            return user;
        }
        return Optional.empty();
    }

    public User createGuestAccount(String guestId) {
        Optional<User> byNickname = userRepository.findByNickname(guestId);
	    return byNickname.orElseGet(() -> getUser("GUEST", UserRole.GUEST));
    }

    public User createAIAccount() {
        return getUser("AI", UserRole.AI);
    }

    private User getUser ( String nickname, UserRole role ) {
        User user = new User();
        String guestNickName = UUIDGenerator.Generate(nickname);
        user.setNickname(guestNickName);
        user.setPassword(UUID.randomUUID().toString());
        user.setUserStatus(UserStatus.ONLINE);
        user.setUserRole(role);
        return userRepository.save(user);
    }

    public boolean setUserSocketId(String nickName, String socketId) {
        return ( userRepository.updateUserSocketId(nickName, socketId) == 1 ) ? true : false;
    }

    public boolean updateUserStatus(String nickname, UserStatus status) {
        return ( userRepository.updateUserStatus(nickname, status) == 1 ) ? true : false;
    }
}
