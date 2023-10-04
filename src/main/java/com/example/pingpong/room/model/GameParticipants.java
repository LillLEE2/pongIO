package com.example.pingpong.room.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "game_participants")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
public class GameParticipants {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "room_id", nullable = false, unique = true)
    private String roomId;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode")
    private GameMode gameMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

}
