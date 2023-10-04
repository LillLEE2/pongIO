package com.example.pingpong.queue.service;

import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserStatus;
import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
@AllArgsConstructor
public class QueueService {

    public static Queue<User> normalQueue = new LinkedList<>();
    private final UserService userService;

    public String joinNormalQueue(String userNickname) {
        User user = userService.findByNickname(userNickname).get();
        user.setStatusCode(UserStatus.PENDING);

        int queueSize = normalQueue.size();
        if ((queueSize + 1 ) == 2 ) {
            normalQueue.add(user);
            return "normalModeMatchingSuccess";
        } else {
            normalQueue.add(user);
        }

        return "Pending";
    }
}
