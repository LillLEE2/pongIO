package com.example.pingpong.game.dto.GameObjects.sendDataDTO;

import com.example.pingpong.game.dto.GameElements.GameElement;
import lombok.Getter;
import lombok.Setter;


//Data 분리는 가장 마지막에 처리해주면 될 듯
@Setter
@Getter
public class OneOnOneNormalGameDto {
    //보내줘야 하는 데이터
    // 공의 좌표 및 크기
    // 패들의 좌표 및 크기
    // 점수
    private double ballPosX;
    private double ballPosY;
    private double ballRadius;
    private double leftPaddlePosX;
    private double leftPaddlePosY;
    private double rightPaddlePosX;
    private double rightPaddlePosY;
    private double paddleWidth;
    private double paddleHeight;
    private Integer leftScore;
    private Integer rightScore;
    public OneOnOneNormalGameDto(GameElement gameElement) {
        ballPosX = gameElement.getBallList().get(0).getPosX();
        ballPosY = gameElement.getBallList().get(0).getPosY();
        ballRadius = gameElement.getBallList().get(0).getRadius();
        leftPaddlePosX = gameElement.getPaddleList().get(0).getPosX();
        leftPaddlePosY = gameElement.getPaddleList().get(0).getPosY();
        rightPaddlePosX = gameElement.getPaddleList().get(1).getPosX();
        rightPaddlePosY = gameElement.getPaddleList().get(1).getPosY();
        paddleWidth = gameElement.getPaddleList().get(0).getWidth();
        paddleHeight = gameElement.getPaddleList().get(0).getHeight();
        leftScore = gameElement.getScore().getLeftScore();
        rightScore = gameElement.getScore().getRightScore();
    }
}
