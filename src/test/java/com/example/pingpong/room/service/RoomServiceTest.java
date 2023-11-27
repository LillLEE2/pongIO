package com.example.pingpong.room.service;

import com.example.pingpong.room.model.GameRoom;
import com.example.pingpong.room.repository.RoomRepository;
import com.example.pingpong.user.model.User;
import com.example.pingpong.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
class RoomServiceTest {
}