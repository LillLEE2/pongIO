package com.example.pingpong.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum UserRole {
	USER("USER"), GUEST("GUEST"), AI("AI");

	private final String description;
	UserRole(String description) {
	 this.description = description;
	}

	public String getDescription() { return this.description; }
}
