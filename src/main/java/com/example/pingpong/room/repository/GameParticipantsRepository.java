package com.example.pingpong.room.repository;


import com.example.pingpong.room.model.GameParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameParticipantsRepository extends JpaRepository<GameParticipants, Long> {
}
