package com.example.pingpong.user.repository;

import com.example.pingpong.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickname);
}
