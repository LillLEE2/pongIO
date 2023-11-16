package com.example.pingpong.exception;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private int status;
	private String message;
	private long timeStamp;

}
