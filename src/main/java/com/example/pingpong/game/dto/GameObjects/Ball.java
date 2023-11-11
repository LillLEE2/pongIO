package com.example.pingpong.game.dto.GameObjects;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ball {
    private double posX;
    private double posY;
    private double radius;
    private double velocityX;
    private double velocityY;
    private ItemEffect effect;
    private long effectStartTime;

    public void update() {
        this.posX += velocityX;
        this.posY += velocityY;
        velocityX = velocityX < 0 ? velocityX - 0.01 : velocityX + 0.01;
        velocityY = velocityY < 0 ? velocityY - 0.01 : velocityY + 0.01;
    }

    public void update(Integer score) {
        if (this.effect != null && isEffectExpired()) {
            this.effect = null;
        }
        if (effect == null) {
            this.posX += velocityX;
            this.posY += velocityY;

            this.velocityX = velocityX < 0 ? -0.5 - score * 0.002 : 0.5 + score * 0.002;
            this.velocityY = velocityY < 0 ? -0.25 - score * 0.001 : 0.25 + score * 0.001;
        } else {
            switch (effect) {
                case FREEZE:
                    break;
                case GRAVITY:
                    gravityUpdate();
                    break;
            }
        }
    }

    public void applyItemEffect(ItemEffect effect) {
        this.effect = effect;
        this.effectStartTime = System.currentTimeMillis();
        // 아이템 효과에 따라 속도 등을 조절할 수 있음
    }

    private boolean isEffectExpired() {
        long currentTime = System.currentTimeMillis();
        long durationTime = currentTime - effectStartTime;
        return durationTime >= effect.getDurationMillis();
    }

    public void gravityUpdate() {
        this.posX += velocityX;
        this.posY += velocityY;
        this.velocityX -= 0.01;
    }

}

