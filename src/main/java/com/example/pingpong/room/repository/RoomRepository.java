package com.example.pingpong.room.repository;

import com.example.pingpong.room.model.GameRoom;
import com.example.pingpong.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<GameRoom, Integer> {

    boolean existsByRoomId(String roomId);
    void deleteByRoomId(String roomId);
    Optional<GameRoom> findByRoomId(String roomId);

}
