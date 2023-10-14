package com.example.pingpong.game.dto.result;

import com.example.pingpong.game.dto.GameMode;
//import com.example.pingpong.global.util.StringListToJsonConverter;
import com.example.pingpong.room.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "game_results")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResults {

    @EmbeddedId
    private GameResultsId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode", nullable = false)
    private GameMode gameMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type", nullable = false)
    private RoomType gameType;

    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "participants_count")
    private Integer participantsCount;

    @Column(name = "winner_id")
    private Integer winnerId;

    @Column(name = "winner_score")
    private Integer winnerScore;

    @Column(name = "top_scorer_id")
    private Integer topScorerId;

    @Column(name = "top_score")
    private Integer topScore;

    @Column(name = "low_scorer_id")
    private Integer lowScorerId;

    @Column(name = "low_score")
    private Integer lowScore;

    @Column(name = "summary")
    private String summary;

}
