package com.example.pingpong.queue.service;

import com.example.pingpong.global.Global;
import com.example.pingpong.queue.dto.QueueJoinResult;
import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserStatus;
import com.example.pingpong.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@AllArgsConstructor
public class QueueService {

    public static ConcurrentLinkedQueue<User> normalQueue = new ConcurrentLinkedQueue<>();
    private final UserService userService;

    public QueueJoinResult joinNormalQueue( String userNickname) {
        User user = userService.findByNickname(userNickname).get();
        user.setStatusCode(UserStatus.PENDING);
        userService.saveUser(user);

        int queueSize = normalQueue.size();
        if ((queueSize + 1 ) == 2 ) {
            normalQueue.add(user);
            return new QueueJoinResult(true, Global.NORMAL_MATCHING_SUCCESS);
        } else {
            normalQueue.add(user);
            return new QueueJoinResult(false, UserStatus.PENDING.getDescription());
        }

    }
}
