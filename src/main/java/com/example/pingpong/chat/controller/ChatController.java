package com.example.pingpong.chat.controller;


import com.example.pingpong.chat.dto.Message;
import com.example.pingpong.room.model.Rooms;
import com.example.pingpong.room.repository.RoomRepository;
import com.example.pingpong.room.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Controller
@AllArgsConstructor
public class ChatController {

	private final RoomRepository roomRepository;
	private final SimpMessagingTemplate messagingTemplate;
	@MessageMapping("/chat/{roomId}/chatRoom")
	public void sendRoomChatMessage( @Payload Message message, @DestinationVariable String roomId ) {
		Optional<Rooms> isRoom = roomRepository.findByRoomId(roomId);
		if (!isRoom.isPresent()) {
			messagingTemplate.convertAndSend(format("/channel/%s", roomId), message);
		}
	}
}
