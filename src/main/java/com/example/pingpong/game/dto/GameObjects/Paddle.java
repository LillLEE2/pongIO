package com.example.pingpong.game.dto.GameObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class Paddle {
    private double posX;
    private double posY;
    private double width;
    private double height;

    public Paddle() {
        // 기본 생성자
    }

    public Paddle(double x, double y, double width, double height) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}


