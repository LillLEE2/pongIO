package com.example.pingpong.room.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_rooms")
@Getter
@ToString
@NoArgsConstructor(force = true)
public class GameRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Long pk;

    @Column(name = "room_id", nullable = false, unique = true)
    private final String roomId;

    @Column(name = "room_name", nullable = false)
    private final String roomName;

    @Column(name = "room_owner_nickname", nullable = false)
    private final String roomOwnerNickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode", nullable = false)
    private GameMode gameMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status", nullable = false)
    private RoomStatus roomStatus;

    @Column(name = "max_players", nullable = false)
    private Integer maxPlayers;

    @Column(name = "cur_players_cnt", nullable = false)
    private Integer curPlayersCnt = 0;

    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    @Column(name = "started_dt")
    private LocalDateTime startedDateTime;

    @Column(name = "ended_dt")
    private LocalDateTime endedDateTime;

}
