package com.example.pingpong.game.dto.Leaderboard;

import javax.persistence.*;

@Entity
@Table(name = "game_leaderboard")
public class GameLeaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //관계 설정?
    @Column(name = "game_mode", nullable = false)
    private String gameMode;

    @Column(name = "player_id", nullable = false)
    private Integer playerId;

    @Column(name = "player_score", nullable = false)
    private Integer playerScore;

//    @Column(name = "player_rank", nullable = false)
//    private Integer playerRank;

}
