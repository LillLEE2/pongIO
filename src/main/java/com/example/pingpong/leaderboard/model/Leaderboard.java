package com.example.pingpong.leaderboard.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "leaderboard")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leaderboard_id")
    private Long leaderboardId;

    @Column(name = "nickname", nullable = false, length = 255)
    private String nickname;

    @Column(name = "game_mode", nullable = false, length = 255)
    private String gameMode;

    @Column(name = "total_wins", columnDefinition = "integer default 0")
    private Integer totalWins;

    @Column(name = "total_losses", columnDefinition = "integer default 0")
    private Integer totalLosses;

    @Column(name = "total_score", columnDefinition = "integer default 0")
    private Integer totalScore;

    @Column(name = "total_games_played", columnDefinition = "integer default 0")
    private Integer totalGamesPlayed;

    @Column(name = "average_score")
    private BigDecimal averageScore;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "last_updated", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Timestamp lastUpdated;

}
