package com.example.pingpong.user.service;

import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserStatus;
import com.example.pingpong.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public Optional<User> login(String nickname, String password) {
        Optional<User> user = findByNickname(nickname);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            user.get().setStatusCode(UserStatus.ONLINE);
            return user;
        }
        return Optional.empty();
    }

    public User createGuestAccount() {
        User guestUser = new User();
        String guestNickName;
        do {
            guestNickName = "GUEST" + new Random().nextInt(100000);
        } while(userRepository.existsByNickname(guestNickName));

        guestUser.setNickname(guestNickName);
        guestUser.setPassword(UUID.randomUUID().toString());
        guestUser.setStatusCode(UserStatus.ONLINE);
        return userRepository.save(guestUser);
    }

    public void setUserSocketId(String nickName, String socketId) {
        User user = userRepository.findByNickname(nickName).get();
        user.setSocketId(socketId);
        userRepository.save(user);
    }
}
