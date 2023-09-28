package com.example.pingpong.repository;

import com.example.pingpong.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByNickname(String nickname);

    boolean existsByNickname(String nickname);
}
