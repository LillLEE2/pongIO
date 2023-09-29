package com.example.pingpong.room.repository;

import com.example.pingpong.room.model.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<GameRoom, Integer> {
}
