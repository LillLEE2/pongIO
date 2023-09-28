package com.example.pingpong.service;

import com.example.pingpong.model.User;
import com.example.pingpong.repository.UserRepository;
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
        return Optional.ofNullable(userRepository.findByNickname(nickname));
    }

    public Optional<User> login(String nickname, String password) {
        Optional<User> user = findByNickname(nickname);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    public User createGuestAccount() {
        User guestUser = new User();
        guestUser.setNickname("GUEST" + new Random().nextInt(100000));
        guestUser.setPassword(UUID.randomUUID().toString());
        return userRepository.save(guestUser);
    }
}
