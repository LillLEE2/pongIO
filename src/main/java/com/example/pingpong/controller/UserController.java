package com.example.pingpong.controller;

import com.example.pingpong.model.User;
import com.example.pingpong.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/guest-login")
    public ResponseEntity<User> createGuestAccount() {
        User guestUser = userService.createGuestAccount();
        return ResponseEntity.ok(guestUser);
    }

    @GetMapping("/{nickName}")
    public ResponseEntity<User> getUserById(@PathVariable String nickName) {
        return userService.findByNickname(nickName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/{nickName}")
    public ResponseEntity<Void> doesUserExist(@PathVariable String nickName) {
        return userService.findByNickname(nickName).isPresent() ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginUser) {
        return userService.login(loginUser.getNickname(), loginUser.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}