package com.example.pingpong.game.dto.GameObjects.BallLogicStrategy;

import com.example.pingpong.game.dto.GameElements.GameElement;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BallLogicStrategy {
    public abstract void update(GameElement gameElement);
    public abstract void collision(GameElement gameElement);
}
