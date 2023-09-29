package com.example.pingpong.room.model;

import lombok.*;

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
    private String roomId;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "room_owner_nickname", nullable = false)
    private String roomOwnerNickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode")
    private GameMode gameMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status")
    private RoomStatus roomStatus;

    @Column(name = "max_players")
    private Integer maxPlayers;

    @Column(name = "cur_players_cnt")
    private Integer curPlayersCnt = 0;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "started_dt")
    private LocalDateTime startedDateTime;

    @Column(name = "ended_dt")
    private LocalDateTime endedDateTime;

    private GameRoom(Builder builder) {
        this.roomId = builder.roomId;
        this.roomName = builder.roomName;
        this.roomOwnerNickname = builder.roomOwnerNickname;
    }

    public static class Builder {
        private String roomId;
        private String roomName;
        private String roomOwnerNickname;

        public Builder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder roomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public Builder roomOwnerNickname(String roomOwnerNickname) {
            this.roomOwnerNickname = roomOwnerNickname;
            return this;
        }

        public GameRoom build() {
            return new GameRoom(this);
        }
    }

}
