package com.example.pingpong.game.model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameResultsId implements Serializable {

    @Column(name = "result_id", nullable = false)
    private String resultId;

    @Column(name = "room_id", nullable = false)
    private String roomId;

}
