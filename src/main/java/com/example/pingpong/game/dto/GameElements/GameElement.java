package com.example.pingpong.game.dto.GameElements;

import com.example.pingpong.game.dto.GameObjects.Ball;
import com.example.pingpong.game.dto.GameObjects.Paddle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public abstract class GameElement {
    protected ArrayList<Paddle> paddleList;
    protected ArrayList<Ball> ballList;
}

