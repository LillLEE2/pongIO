package com.example.pingpong.game.dto.GameObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Paddle {
    private double posX;
    private double posY;
    private double width;
    private double height;
    private Integer paddleStatus;

    public Paddle(double x, double y, double width, double height, Integer paddleStatus) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.paddleStatus = paddleStatus;
    }

    public void update() {
        double speed = 3.0;
        if (paddleStatus == 1 && posY > 0) {
            posY -= speed;
        } else if (paddleStatus == 2 && posY < 100 - height) {
            posY += speed;
        }
    }
}


