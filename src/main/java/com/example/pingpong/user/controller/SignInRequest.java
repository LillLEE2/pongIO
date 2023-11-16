package com.example.pingpong.user.controller;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequest {

	private String nickname;
	private String password;
	private String email;
}
