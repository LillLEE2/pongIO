package com.example.pingpong.user.repository;

import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.userStatus = :status, u.updateDate = now() WHERE u.nickname = :nickname")
    int updateUserStatus(@Param("nickname") String nickname, @Param("status") UserStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.socketId = :socketId WHERE u.nickname = :nickname AND (u.socketId IS NULL OR u.socketId = '')")
    int updateUserSocketId(@Param("nickname") String nickname, @Param("socketId") String socketId);

}
