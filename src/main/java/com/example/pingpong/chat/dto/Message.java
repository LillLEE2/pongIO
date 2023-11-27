package com.example.pingpong.chat.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	public enum MessageType {
		CHAT, JOIN, LEAVE
	}
	private MessageType messageType;
	private String content;
	private @Setter String sender;

}
