package com.example.pingpong.room.service;

import com.example.pingpong.room.model.GameParticipants;
import com.example.pingpong.room.repository.GameParticipantsRepository;
import com.example.pingpong.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service
@AllArgsConstructor
public class GameParticipantsService {


    private final GameParticipantsRepository gameParticipantsRepository;

    private List<GameParticipants> createAndJoinGameRoom(Queue<User> userQueue) {

        return null;
    }
}
