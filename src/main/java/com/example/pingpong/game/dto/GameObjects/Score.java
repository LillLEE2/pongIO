package com.example.pingpong.game.dto.GameObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Score {
    Integer leftScore;
    Integer rightScore;
    Integer rankScore;
    Integer maxScore;
    private ScheduledExecutorService executorService;
    public Score() {
        this.leftScore = 0;
        this.rightScore = 0;
        this.rankScore = 0;
        this.maxScore = 2;
    }

    public void startRankScoreUp() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::rankScoreUp, 0, 1, TimeUnit.SECONDS);
    }

    public void stopRankScoreUp() {
        executorService.shutdown();
    }

    public void leftScoreUp() {
        this.leftScore++;
    }

    public void rightScoreUp() {
        this.rightScore++;
    }

    public void rankScoreUp() {
        this.rankScore++;
    }
}
