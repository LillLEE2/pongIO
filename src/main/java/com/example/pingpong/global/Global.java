package com.example.pingpong.global;

import java.util.Arrays;
import java.util.Collections;

public class Global {
    public static final String[] ALLOW_ORIGIN_PATTERNS = loadAllowOriginPatterns();
    public static final String NORMAL_MATCHING_SUCCESS = "normalModeMatchingSuccess";
    public static final String SPEED_MATCHING_SUCCESS = "speed_matching";

    private static String[] loadAllowOriginPatterns () {
        return Collections.unmodifiableList(Arrays.asList("http://localhost:3000")).toArray(new String[0]);
    }
}
