package com.example.pingpong.game.dto;

import com.example.pingpong.room.model.Rooms;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "game_settings")
@AllArgsConstructor
@NoArgsConstructor
public class GameSetting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    private Long settingId;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "ball_speed")
    private Integer ballSpeed;

    @Column(name = "paddle_size")
    private Integer paddleSize;

    @Column(name = "game_duration")
    private Integer gameDuration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    private Rooms gameRoom;
}
