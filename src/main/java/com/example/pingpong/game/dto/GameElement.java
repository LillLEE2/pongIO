package com.example.pingpong.game.dto;

public class GameElement {
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Ball ball;
    private Integer leftScore;
    private Integer rightScore;
    public GameElement() {
        ball = new Ball(49, 49, 1);
        leftPaddle = new Paddle(5, 40, 2, 15);
        rightPaddle = new Paddle(100 - 2 - 5, 40, 2, 15);
        leftScore = 0;
        rightScore = 0;
    }

    public GameElement(Paddle leftPaddle, Paddle rightPaddle, Ball ball, Integer leftScore, Integer rightScore) {
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.ball = ball;
        this.leftScore = leftScore;
        this.rightScore = rightScore;
    }

    public Paddle getLeftPaddle() {
        return leftPaddle;
    }

    public void setLeftPaddle(Paddle leftPaddle) {
        this.leftPaddle = leftPaddle;
    }

    public Paddle getRightPaddle() {
        return rightPaddle;
    }

    public void setRightPaddle(Paddle rightPaddle) {
        this.rightPaddle = rightPaddle;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Integer getLeftScore() {
        return leftScore;
    }

    public Integer getRightScore() {
        return rightScore;
    }
    public void setLeftScore(Integer leftScore) {
        this.leftScore = leftScore;
    }
    public void setRightScore(Integer rightScore) {
        this.rightScore = rightScore;
    }
}

