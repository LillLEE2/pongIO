package com.example.pingpong.game.dto.GameObjects.sendDataDTO;

import com.example.pingpong.game.dto.GameElements.GameElement;
import com.example.pingpong.game.dto.GameObjects.Ball;
import com.example.pingpong.game.dto.GameObjects.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SoloRankedDto {
    private double leftPaddlePosX;
    private double leftPaddlePosY;
    private double paddleWidth;
    private double paddleHeight;
    private Integer score;
    private List<Ball> ballList;
    private List<Item> itemList;
    public SoloRankedDto(GameElement gameElement) {
//        ballList = gameElement.copyBallList();
//        itemList = gameElement.copyItemList();
        ballList = gameElement.getBallList();
        itemList = gameElement.getItemList();
        leftPaddlePosX = gameElement.getPaddleList().get(0).getPosX();
        leftPaddlePosY = gameElement.getPaddleList().get(0).getPosY();
        paddleWidth = gameElement.getPaddleList().get(0).getWidth();
        paddleHeight = gameElement.getPaddleList().get(0).getHeight();
        score = gameElement.getScore().getRankScore();
    }
}
