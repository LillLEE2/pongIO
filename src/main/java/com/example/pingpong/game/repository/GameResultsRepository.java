package com.example.pingpong.game.repository;

import com.example.pingpong.game.dto.result.GameResults;
import com.example.pingpong.game.dto.result.GameResultsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameResultsRepository extends JpaRepository<GameResults, Long> {
    GameResults findById(GameResultsId gameResultsId);
}
