package com.example.pingpong.user.service;

import com.example.pingpong.user.model.User;
import com.example.pingpong.user.model.UserStatus;
import com.example.pingpong.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


//@SpringBootTest
class UserServiceTest {

//	@Spy
//	@InjectMocks
//	private UserService userService;
//
//	@Mock
//	private UserRepository userRepository;
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//	}
//
//	@Test
//    void setUserSocketId() {
//        // Given
//        String nickname = "nickname";
//        String socketId = "socketId";
//
//        // Mock the return value of userRepository.updateUserSocketId()
//        when(userRepository.updateUserSocketId(anyString(), anyString())).thenReturn(1);
//
//        // When
//        boolean result = userService.setUserSocketId(nickname, socketId);
//
//        // Then
//        assertTrue(result, "Expected to return true when updateUserSocketId() returns 1");
//    }
//
//    @Test
//    void setUserSocketIdShouldReturnFalse() {
//        // Given
//        String nickname = "nickname";
//        String socketId = "socketId";
//
//        // Mock the return value of userRepository.updateUserSocketId()
//        when(userRepository.updateUserSocketId(anyString(), anyString())).thenReturn(0);
//
//        // When
//        boolean result = userService.setUserSocketId(nickname, socketId);
//
//        // Then
//        assertFalse(result, "Expected to return false when updateUserSocketId() returns 0");
//    }
//
//    @Test
//    void updateUserStatus() {
//        // Given
//        String nickname = "nickname";
//        UserStatus status = UserStatus.ONLINE;
//
//        // Mock the return value of userRepository.updateUserStatus()
//        when(userRepository.updateUserStatus(anyString(), any(UserStatus.class))).thenReturn(1);
//
//        // When
//        boolean result = userService.updateUserStatus(nickname, status);
//
//        // Then
//        assertTrue(result, "Expected to return true when updateUserStatus() returns 1");
//    }
//
//    @Test
//    void updateUserStatusShouldReturnFalse() {
//        // Given
//        String nickname = "nickname";
//        UserStatus status = UserStatus.ONLINE;
//
//        // Mock the return value of userRepository.updateUserStatus()
//        when(userRepository.updateUserStatus(anyString(), any(UserStatus.class))).thenReturn(0);
//
//        // When
//        boolean result = userService.updateUserStatus(nickname, status);
//
//        // Then
//        assertFalse(result, "Expected to return false when updateUserStatus() returns 0");
//    }
}