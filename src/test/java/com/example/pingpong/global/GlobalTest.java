package com.example.pingpong.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GlobalTest {
	@Test
	@DisplayName("배열 테스트좀")
	void arrayTest() {
		String[] allowOriginPatterns = Global.ALLOW_ORIGIN_PATTERNS;
//		String[] array = (String[]) strings.toArray();
//		String[] array1 = (String[]) strings.toArray(new String[0]);
//		String[] array2 = strings.toArray(new String[0]);


		System.out.printf("====");
	}
}