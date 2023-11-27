package com.example.pingpong.chat.controller;

import com.example.pingpong.chat.dto.Message;
import com.example.pingpong.room.model.Rooms;
import com.example.pingpong.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatControllerTest {

	@BeforeEach
	void setUp () {
	}

	@Test
	public void test_sendRoomChatMessage_messageReceived_messageSentToCorrectRoom() {
	 // Arrange
	 RoomRepository roomRepository = mock(RoomRepository.class);
	 SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);
	 ChatController chatController = new ChatController(roomRepository, messagingTemplate);
	 Message message = new Message(Message.MessageType.CHAT, "Hello", "User1");
	 String roomId = "123";

	 Rooms room = Rooms.builder()
	         .roomId(roomId)
	         .roomName("Room1")
	         .roomOwnerNickname("User1")
	         .build();

	 when(roomRepository.findByRoomId(roomId)).thenReturn(Optional.of(room));

	 // Act
	 chatController.sendRoomChatMessage(message, roomId);

	 // Assert
	 verify(messagingTemplate).convertAndSend("/channel/123", message);
	}
}