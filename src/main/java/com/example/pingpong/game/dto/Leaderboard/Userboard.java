package com.example.pingpong.game.dto.Leaderboard;

import javax.persistence.*;

@Entity
@Table(name = "user_leaderboard")
public class Userboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "game_mode", nullable = false)
    private String gameMode;

    //User 엔티티 연결?
    @Column(name = "player_id", nullable = false)
    private Integer playerId;

    @Column(name = "win_count", nullable = false)
    private Integer winCount;

    @Column(name = "lose_count", nullable = false)
    private Integer loseCount;

    @Column(name = "win_rate", nullable = false)
    private Double winRate;
}
