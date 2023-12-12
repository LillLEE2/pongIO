package com.example.pingpong.game.dto.GameObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private double posX;
    private double posY;
    private double radius;
    private ItemEffect effect;

    public Item(double xPos, double yPos) {
        this.posX = xPos;
        this.posY = yPos;
        this.radius = 3;
        effect = ItemEffect.values()[(int) (Math.random() * ItemEffect.values().length)];
    }
}
