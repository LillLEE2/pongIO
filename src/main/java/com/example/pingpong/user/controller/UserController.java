package com.example.pingpong.user.controller;

import com.example.pingpong.user.model.User;
import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody SignInRequest signInRequest) {
        try {
            User user = userService.authenticateUser(signInRequest);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping("/guest-login")
    public ResponseEntity<String> createGuestAccount(HttpServletRequest request, @RequestHeader("X-Guest-ID") String guestId ) {
        User guestUser = userService.createGuestAccount(guestId);
        HttpSession session = request.getSession();
        session.setAttribute("nickname", guestUser.getNickname());
        return ResponseEntity.ok(guestUser.getNickname());
    }

    @GetMapping("/{nickName}")
    public ResponseEntity<User> getUserById(@PathVariable String nickName) {
        return userService.findByNickname(nickName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/{nickName}")
    public ResponseEntity<Boolean> doesUserExist(@PathVariable String nickName) {
        if (nickName.contains("GUEST_")) {
            throw new IllegalArgumentException("닉네임에 'GUEST_'를 포함할 수 없습니다.");
        }
        boolean present = userService.findByNickname(nickName).isPresent();
        return ResponseEntity.ok(present);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginUser) {
        return userService.login(loginUser.getNickname(), loginUser.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}