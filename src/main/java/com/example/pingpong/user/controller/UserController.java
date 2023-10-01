package com.example.pingpong.user.controller;

import com.example.pingpong.user.model.User;
import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("호출은 됨");
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/guest-login")
    public ResponseEntity<User> createGuestAccount(HttpServletRequest request) {
        User guestUser = userService.createGuestAccount();
        HttpSession session = request.getSession();
        session.setAttribute("user", guestUser.getNickname());
        return ResponseEntity.ok(guestUser);
    }

    @GetMapping("/{nickName}")
    public ResponseEntity<User> getUserById(@PathVariable String nickName) {
        return userService.findByNickname(nickName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/{nickName}")
    public ResponseEntity<Boolean> doesUserExist(@PathVariable String nickName) {
        boolean nickNameExist;
        Optional<User> user = userService.findByNickname(nickName);
        nickNameExist = user.isPresent();

        return ResponseEntity.ok(nickNameExist);
//        return userService.findByNickname(nickName).isPresent() ?
//                ResponseEntity.ok().build() :
//                ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginUser) {
        return userService.login(loginUser.getNickname(), loginUser.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}