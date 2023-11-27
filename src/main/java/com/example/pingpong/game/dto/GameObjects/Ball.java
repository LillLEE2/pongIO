package com.example.pingpong.game.dto.GameObjects;

import com.example.pingpong.game.dto.GameElements.GameElement;
import com.example.pingpong.game.dto.GameObjects.BallLogicStrategy.BallLogicStrategy;
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
//    private BallLogicStrategy updateStrategy;

//    public void setUpdateStrategy(BallLogicStrategy updateStrategy) {
//        this.updateStrategy = updateStrategy;
//    }
    public void update() {
        this.posX += velocityX;
        this.posY += velocityY;
        velocityX = velocityX < 0 ? velocityX - 0.01 : velocityX + 0.01;
        velocityY = velocityY < 0 ? velocityY - 0.01 : velocityY + 0.01;
//        updateStrategy.update(new GameElement());
    }

    public void update(Integer score) {
        if (this.effect != null && isItemExpired()) {
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

    public void collision(GameElement gameElement) {
        Ball ball = gameElement.getBallList().get(0);
        Paddle leftPaddle = gameElement.getPaddleList().get(0);
        Paddle rightPaddle = gameElement.getPaddleList().get(1);

        // 천장과 바닥 충돌 확인
        if (ball.getPosY() - ball.getRadius() < 0) {
            ball.setPosY(ball.getRadius());
            ball.setVelocityY(-ball.getVelocityY());
        } else if (ball.getPosY() + ball.getRadius() > 100) {
            ball.setPosY(100 - ball.getRadius());
            ball.setVelocityY(-ball.getVelocityY());
        }

        // 패들과 공의 충돌 확인
        if (ball.getPosX() - ball.getRadius() < leftPaddle.getPosX() + leftPaddle.getWidth() &&
                ball.getPosX() + ball.getRadius() > leftPaddle.getPosX() &&
                ball.getPosY() + ball.getRadius() >= leftPaddle.getPosY() &&
                ball.getPosY() - ball.getRadius() <= leftPaddle.getPosY() + leftPaddle.getHeight()) {
            double deltaY = ball.getPosY() - (leftPaddle.getPosY() + leftPaddle.getHeight() / 2);
            if (ball.getPosX() + ball.getRadius() > leftPaddle.getPosX() + leftPaddle.getWidth() / 2) {
                ball.setVelocityX(ball.getVelocityX() < 0 ? -ball.getVelocityX() : ball.getVelocityX());
            }
            ball.setVelocityY(deltaY * 0.2);
        }

        if (ball.getPosX() + ball.getRadius() > rightPaddle.getPosX() &&
                ball.getPosX() - ball.getRadius() < rightPaddle.getPosX() + rightPaddle.getWidth() &&
                ball.getPosY() + ball.getRadius() >= rightPaddle.getPosY() &&
                ball.getPosY() - ball.getRadius() <= rightPaddle.getPosY() + rightPaddle.getHeight()) {
            double deltaY = ball.getPosY() - (rightPaddle.getPosY() + rightPaddle.getHeight() / 2);
            if (ball.getPosX() - ball.getRadius() < rightPaddle.getPosX() + rightPaddle.getWidth() / 2) {
                ball.setVelocityX(ball.getVelocityX() > 0 ? -ball.getVelocityX() : ball.getVelocityX());
            }
            ball.setVelocityY(deltaY * 0.2);
        }
    }

    public void collision(GameElement gameElement, Paddle leftPaddle) {
        if (posY - radius < 0) {
            posY = radius;
            velocityY = -velocityY;
        } else if (posY +radius > 100) {
            posY = 100 - radius;
            velocityY = -velocityY;
        }

        // 패들과 공의 충돌 확인
        if (posX -radius < leftPaddle.getPosX() + leftPaddle.getWidth() &&
                posX +radius > leftPaddle.getPosX() &&
                posY +radius >= leftPaddle.getPosY() &&
                posY -radius <= leftPaddle.getPosY() + leftPaddle.getHeight()) {
            double deltaY = posY - (leftPaddle.getPosY() + leftPaddle.getHeight() / 2);
            if (posX +radius > leftPaddle.getPosX() + leftPaddle.getWidth() / 2) {
                velocityX = (-velocityX);
                gameElement.getScore().setRankScore(gameElement.getScore().getRankScore() + 1);
            }
            velocityY = (deltaY * 0.2);
        }

        // 오른쪽 벽과 공의 충돌 확인
        if (posX +radius > 100) {
            posX = (100 -radius);
            velocityX = (-velocityX);
        }
    }

    public void resetPosition() {
        posX = (50 - radius);
        posY = (50 - radius);

        double directX = Math.random() < 0.5 ? -1.0 : 1.0;
        double directY = Math.random() < 0.5 ? -1.0 : 1.0;

        velocityX = (0.5 * directX);
        velocityY = (0.25 * directY);
    }

    public void applyItemEffect(ItemEffect effect) {
        this.effect = effect;
        this.effectStartTime = System.currentTimeMillis();
        // 아이템 효과에 따라 속도 등을 조절할 수 있음
    }

    private boolean isItemExpired() {
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

