package com.konieczny_adam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.konieczny_adam.utils.Constants;
import com.konieczny_adam.utils.Utils;

/**
 * Created by Adam on 23.04.2017.
 */

public class Player   {

    public Viewport viewport;
    public float  velocity;
    public Vector2  position;
    public boolean leftDirection = false;
    private Texture playerTexture;
    private Texture playerHitTexture;
    private Animation playerHitAnimation;
    private long   animationTime;
    private boolean animationStart=false;

    public Player(Viewport viewport)
    {
        this.viewport = viewport;
        this.velocity = com.konieczny_adam.utils.Constants.PLAYER_SPEED;
        init();
    }


    public void render(SpriteBatch batch)
    {
       // renderer.setColor(com.konieczny_adam.utils.Constants.PLAYER_COLOR);
        //.rect(position.x, com.konieczny_adam.utils.Constants.PLAYER_HEIGHT, com.konieczny_adam.utils.Constants.PLAYER_WIDTH, com.konieczny_adam.utils.Constants.PLAYER_HEIGHT);
        if(!animationStart) {
            batch.draw(playerTexture,position.x,Constants.PLAYER_HEIGHT,Constants.PLAYER_WIDTH,Constants.PLAYER_HEIGHT);
        }else{
            batch.draw((Texture)playerHitAnimation.getKeyFrame(Utils.secondsSince(animationTime)),position.x,Constants.PLAYER_HEIGHT,Constants.PLAYER_WIDTH,Constants.PLAYER_HEIGHT);
           if( playerHitAnimation.isAnimationFinished(Utils.secondsSince(animationTime)))
           {
               animationStart=false;
           }
        }

    }

    public void init()
    {
        position = new Vector2(viewport.getWorldWidth()/2 - com.konieczny_adam.utils.Constants.PLAYER_WIDTH/2, com.konieczny_adam.utils.Constants.PLAYER_HEIGHT*2);
        playerTexture = new Texture(Gdx.files.internal("images/playerBar.png"));
        playerHitTexture = new Texture(Gdx.files.internal("images/playerBarHit.png"));
        Array<Texture> playerHitFrames = new Array<Texture>();
        playerHitFrames.add(playerHitTexture);
        playerHitFrames.add(playerHitTexture);
        playerHitAnimation = new Animation(0.02f,playerHitFrames, Animation.PlayMode.NORMAL);
    }

    public void update(float delta)
    {
        if(Ball.hitPlayerAnimation)
        {
            animationTime = TimeUtils.nanoTime();
            animationStart = true;
        }



        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            if(!leftDirection)
            {
                velocity= com.konieczny_adam.utils.Constants.PLAYER_SPEED;
            }
            velocity += delta + com.konieczny_adam.utils.Constants.PLAYER_ACCELERATION;
            position.x += -velocity * delta;
            leftDirection = true;

        } else if  (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if(leftDirection)
            {
                velocity= com.konieczny_adam.utils.Constants.PLAYER_SPEED;
            }
            velocity += delta + com.konieczny_adam.utils.Constants.PLAYER_ACCELERATION;
            position.x += velocity * delta;
            leftDirection = false;
        } else

        if(Gdx.input.getAccelerometerY()<-0.2)
        {

            if(!leftDirection)
            {
                velocity= com.konieczny_adam.utils.Constants.PLAYER_SPEED;
            }
            velocity += delta + com.konieczny_adam.utils.Constants.PLAYER_ACCELERATION;
            position.x += -velocity * delta;
            leftDirection = true;
        }else if (Gdx.input.getAccelerometerY()>0.2)
        {
            if(leftDirection)
            {
                velocity= com.konieczny_adam.utils.Constants.PLAYER_SPEED;
            }
            velocity += delta + com.konieczny_adam.utils.Constants.PLAYER_ACCELERATION;
            position.x += velocity * delta;
            leftDirection = false;
        }else   velocity= com.konieczny_adam.utils.Constants.PLAYER_SPEED;

        ensureInBounds();

    }

    public void ensureInBounds()
    {
        if(position.x<0)
        {
            position.x = 0;
            velocity = com.konieczny_adam.utils.Constants.PLAYER_SPEED;
        }

        if (position.x > viewport.getWorldWidth() - com.konieczny_adam.utils.Constants.PLAYER_WIDTH)
        {
            position.x = viewport.getWorldWidth() - com.konieczny_adam.utils.Constants.PLAYER_WIDTH;
            velocity = com.konieczny_adam.utils.Constants.PLAYER_SPEED;
        }

    }

}
