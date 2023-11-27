package com.example.pingpong.room.repository;

import com.example.pingpong.room.model.RoomStatus;
import com.example.pingpong.room.model.Rooms;
import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Rooms, Integer> {
    @Query("SELECT u FROM Rooms u WHERE u.isPrivate = :isPrivate and u.roomStatus = :roomStatus")
    List<Rooms> findAllByIsPrivate(@Param("isPrivate") Boolean isPrivate, @Param("roomStatus")RoomStatus roomStatus);

    boolean existsByRoomId(String roomId);
    void deleteByRoomId(String roomId);
    Optional<Rooms> findByRoomId(String roomId);

}
