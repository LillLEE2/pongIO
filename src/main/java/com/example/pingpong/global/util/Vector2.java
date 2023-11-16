package com.example.pingpong.global.util;


import com.sun.javafx.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.rmi.CORBA.Util;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vector2 {

    public float x;
    public float y;

    // Basic settings for common Vector2 instances
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 RIGHT = new Vector2(1, 0);
    public static final Vector2 UP = new Vector2(0, 1);
    public static final Vector2 DOWN = new Vector2(0, -1);
    public static final Vector2 ONE = new Vector2(1, 1);
    public static final Vector2 ZERO = new Vector2(0, 0);

    public Vector2 Lerp(Vector2 a, Vector2 b, float t) {
        t = Utils.clamp(t, 0, 1);

        if (a == null || b == null) {
            throw new IllegalArgumentException(" null.");
        }

        float x = a.getX() + (b.getX() - a.getX()) * t;
        float y = a.getY() + (b.getY() - a.getY()) * t;

        return new Vector2(x, y);
    }
    public float distance(Vector2 a, Vector2 b) {
        float v = a.getX() - b.getX();
        float v1 = a.getY() - b.getY();
        return (float) Math.sqrt(v * v + v1 * v1);
    }
}

