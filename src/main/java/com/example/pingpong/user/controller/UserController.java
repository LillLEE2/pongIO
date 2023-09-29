package com.example.pingpong.user.controller;

import com.example.pingpong.user.model.User;
import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    public ResponseEntity<User> createGuestAccount(HttpServletRequest request) {
        User guestUser = userService.createGuestAccount();

        request.getSession().setAttribute("user",guestUser);
        return ResponseEntity.ok(guestUser);
    }


    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> user = (Map<String, Object>) request.getAttribute("user");
        String nickname = user.get("nickname").toString();
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