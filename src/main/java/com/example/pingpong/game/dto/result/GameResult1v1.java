package com.example.pingpong.game.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "game_result_1v1")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResult1v1 {

    @Id
    @Column(name = "result_1v1_id", nullable = false)
    private String result1v1Id;

    @Column(name = "result_id", nullable = false)
    private String resultId;

    @Column(name = "game_mode", nullable = false)
    private String gameMode;

    //String 의도한게 맞는지?
    @Column(name = "player1_id", nullable = false)
    private Integer player1Id;

    @Column(name = "player2_id", nullable = false)
    private Integer player2Id;

    //winner 랑 loser 스코어로 저장하면 안될까나?
    @Column(name = "player1_score", nullable = false)
    private Integer player1Score;

    @Column(name = "player2_score", nullable = false)
    private Integer player2Score;

    @Column(name = "winning_player_id", nullable = false)
    private Integer winningPlayerId;

    @Column(name = "losing_player_id", nullable = false)
    private Integer losingPlayerId;

    @Column(name = "match_duration")
    private Duration matchDuration;

}
