package com.example.pingpong.game.dto.GameInformations;

import com.example.pingpong.game.service.GameResultsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
@Component
public class DependencyConfiguration {
    private final GameResultsService gameResultsService;
    private final SimpMessagingTemplate messagingTemplate;
}
