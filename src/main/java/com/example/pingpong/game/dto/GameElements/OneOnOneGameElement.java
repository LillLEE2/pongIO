package com.example.pingpong.game.dto.GameElements;

import com.example.pingpong.game.dto.GameMode;
import com.example.pingpong.game.dto.GameObjects.Ball;
import com.example.pingpong.game.dto.GameObjects.Paddle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class OneOnOneGameElement extends GameElement {
    private Integer leftScore;
    private Integer rightScore;
    public OneOnOneGameElement(GameMode gameMode) {
        super(new ArrayList<>(),new ArrayList<>());
        if (gameMode.getDescription().equals("NORMAL")) {
            Ball ball = Ball.builder().posX(49).posY(49).radius(1).velocityX(0.5).velocityY(0.25).build();
            Paddle paddle = Paddle.builder().posX(5).posY(40).width(2).height(15).build();
            Paddle paddle1 = Paddle.builder().posX(93).posY(40).width(2).height(15).build();
            ballList.add(ball);
            paddleList.add(paddle);
            paddleList.add(paddle1);
            leftScore = 0;
            rightScore = 0;
        }
    }
}

