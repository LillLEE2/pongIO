package com.example.pingpong.room.repository;

import com.example.pingpong.room.model.Rooms;
import com.example.pingpong.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Rooms, Integer> {

    boolean existsByRoomId(String roomId);
    void deleteByRoomId(String roomId);
    Optional<Rooms> findByRoomId(String roomId);

}
