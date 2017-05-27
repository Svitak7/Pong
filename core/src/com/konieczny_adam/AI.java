package com.konieczny_adam;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Adam on 23.04.2017.
 */

public class AI {

    Ball ball;
    Enemy enemy;
    Viewport viewport;

    public AI(Ball ball, Enemy enemy, Viewport viewport)
    {
        this.viewport = viewport;
        this.ball = ball;
        this.enemy = enemy;
    }

    public void update(float delta)
    {
        if (enemy.position.x+ com.konieczny_adam.utils.Constants.ENEMY_WIDTH/2>ball.position.x- com.konieczny_adam.utils.Constants.AI_ERROR && enemy.position.x+ com.konieczny_adam.utils.Constants.ENEMY_WIDTH/2<ball.position.x+ com.konieczny_adam.utils.Constants.AI_ERROR)
        {
            return;
        }

        if(enemy.position.x+ com.konieczny_adam.utils.Constants.ENEMY_WIDTH/2 > ball.position.x)
        {
           enemy.position.x-=enemy.velocity*delta; //dobiegamy w lewo
        }
        else if(enemy.position.x+ com.konieczny_adam.utils.Constants.ENEMY_WIDTH/2 < ball.position.x)
        {
           enemy.position.x+=enemy.velocity*delta; //dobiegamyy w prawo
        }

        ensureInBounds();
    }

    public void ensureInBounds()
    {
            if(enemy.position.x<0) enemy.position.x = 0;
            if(enemy.position.x>viewport.getWorldWidth()- com.konieczny_adam.utils.Constants.ENEMY_WIDTH) enemy.position.x = viewport.getWorldWidth()- com.konieczny_adam.utils.Constants.ENEMY_WIDTH;
    }

}
