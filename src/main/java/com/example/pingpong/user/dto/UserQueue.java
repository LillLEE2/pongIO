package com.example.pingpong.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQueue {

	private String nickname;
	private String socketId;
	private String roomId;

}
