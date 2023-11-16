package com.example.pingpong.room.model;

import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.game.dto.GameSetting;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Getter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Rooms {

    @Id
    @Column(name = "room_id", nullable = false, unique = true)
    private String roomId;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "room_owner_nickname", nullable = false)
    private String roomOwnerNickname;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(name = "game_mode")
    private GameMode gameMode;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(name = "room_type")
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status")
    @Setter
    private RoomStatus roomStatus;

    @Column(name = "max_players")
    @Setter
    private Integer maxPlayers;

    @Column(name = "cur_players_cnt")
    private Integer curPlayersCnt = 1;

    @Column(name = "is_private")
    @Setter
    private Boolean isPrivate;

    @Column(name = "started_dt")
    @Setter
    private LocalDateTime startedDateTime;

    @Column(name = "ended_dt")
    @Setter
    private LocalDateTime endedDateTime;

    @OneToOne(mappedBy = "gameRoom", cascade = CascadeType.ALL)
    private GameSetting gameSetting;

    public void setCurPlayersCnt(int curPlayersCnt) {
        this.curPlayersCnt = curPlayersCnt;
    }

}
