package com.example.pingpong.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;


public class UUIDGenerator {
	public static String Generate(String targetName) {
		String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")) + new Random().nextLong();
		String uuid = UUID.nameUUIDFromBytes(formattedDate.getBytes()).toString().substring(0, 13).toUpperCase();
		return targetName + "_" + uuid;
	}
}
