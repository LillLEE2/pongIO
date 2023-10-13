package com.example.pingpong.global;

import com.example.pingpong.game.model.GameInfomation;
import com.example.pingpong.user.dto.UserQueue;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Global {
    public static final String[] ALLOW_ORIGIN_PATTERNS = loadAllowOriginPatterns();
    public static final String NORMAL_MATCHING_SUCCESS = "normalModeMatchingSuccess";
    public static final String SPEED_MATCHING_SUCCESS = "speed_matching";
    public static ConcurrentLinkedQueue<UserQueue> NORMAL_MODE_QUEUE = new ConcurrentLinkedQueue<>();
    public static ConcurrentLinkedQueue<UserQueue> SPEED_MODE_QUEUE = new ConcurrentLinkedQueue<>();
    public static Map<String, GameInfomation> GAME_ROOMS = new HashMap<>();
    public static final String MATCHING_SUCCESS_DESTINATION = "/topic/matching-success";

    private static String[] loadAllowOriginPatterns () {
        return Collections.unmodifiableList(Arrays.asList("http://localhost:3000")).toArray(new String[0]);
    }
}
