package com.example.pingpong.game.dto.GameElements;

import com.example.pingpong.game.dto.GameObjects.Ball;
import com.example.pingpong.game.dto.GameObjects.Item;
import com.example.pingpong.game.dto.GameObjects.Paddle;
import com.example.pingpong.game.dto.GameObjects.Score;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameElement {
    protected ArrayList<Paddle> paddleList = new ArrayList<>();
    protected ArrayList<Ball> ballList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();
    private Score score = new Score();

    public void addBall() {
        Ball ball = Ball.builder().posX(49).posY(49).radius(1).velocityX(0.5).velocityY(0.25).build();
        ballList.add(ball);
    }

    public void addPaddle(double posX, double posY, double width, double height) {
        Paddle paddle = Paddle.builder().posX(posX).posY(posY).width(width).height(height).paddleStatus(0).build();
        paddleList.add(paddle);
    }

    public void addItem() {
        Item item = new Item(50, 50);
        itemList.add(item);
    }

    public void addRandomPositionItem() {
        double randomX = 10 + Math.random() * (100 - 10);
        double randomY = Math.random() * 100;
        Item item = new Item(randomX, randomY);
        itemList.add(item);
    }

    public ArrayList<Ball> copyBallList() {
        ArrayList<Ball> copyBallList = new ArrayList<>();
        for (Ball ball : ballList) {
            copyBallList.add(Ball.builder()
                    .posX(ball.getPosX())
                    .posY(ball.getPosY())
                    .radius(ball.getRadius())
//                    .velocityX(ball.getVelocityX())
//                    .velocityY(ball.getVelocityY())
//                    .effect(ball.getEffect())
//                    .effectStartTime(ball.getEffectStartTime())
                    .build());
        }
        return copyBallList;
    }

    public ArrayList<Item> copyItemList() {
        ArrayList<Item> copyItemList = new ArrayList<>();
        for (Item item : itemList) {
            copyItemList.add(new Item(item.getPosX(), item.getPosY()));
        }
        return copyItemList;
    }
}
