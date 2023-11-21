package com.example.pingpong.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private String sender;

}
