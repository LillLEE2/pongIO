package com.example.pingpong.game.repository;

import com.example.pingpong.game.dto.result.GameResults;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultsRepository extends JpaRepository<GameResults, Long> {
}
