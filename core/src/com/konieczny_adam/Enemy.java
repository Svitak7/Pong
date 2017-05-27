package com.konieczny_adam;

import com.badlogic.gdx.Gdx;
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

public class Enemy {


    public Viewport viewport;
    public Vector2 position;
    float velocity;
    private Texture enemyTexture;
    private long animationTime;
    private boolean animationStart;
    private Animation enemyHitAnimation;
    private Texture enemyHitTexture;

    public Enemy(Viewport viewport) {
        this.viewport = viewport;
        this.velocity = com.konieczny_adam.utils.Constants.ENEMY_SPEED;
        init();

    }

    public void update(float delta) {
        if (Ball.hitEnemyAnimation) {
           animationTime = TimeUtils.nanoTime();
            animationStart=true;
        } else {
            enemyTexture = new Texture(Gdx.files.internal("images/enemyBar.png"));
        }
    }

    public void render(SpriteBatch batch) {
        if (!animationStart) {
            batch.draw(enemyTexture, position.x, viewport.getWorldHeight() - Constants.ENEMY_HEIGHT * 2, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
        } else {
            batch.draw((Texture) enemyHitAnimation.getKeyFrame(Utils.secondsSince(animationTime)), position.x, viewport.getWorldHeight() - Constants.ENEMY_HEIGHT * 2, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
            if (enemyHitAnimation.isAnimationFinished(Utils.secondsSince(animationTime))) {
                animationStart = false;
            }
        }
    }

    public void init() {
        enemyTexture = new Texture(Gdx.files.internal("images/enemyBar.png"));
        position = new Vector2(viewport.getWorldWidth() / 2 - com.konieczny_adam.utils.Constants.ENEMY_WIDTH / 2, viewport.getWorldHeight() - 2 * com.konieczny_adam.utils.Constants.ENEMY_HEIGHT);
        Array<Texture> animationFrames = new Array<Texture>();
        animationFrames.add(new Texture(Gdx.files.internal("images/enemyBarHit.png")));
        animationFrames.add(new Texture(Gdx.files.internal("images/enemyBarHit.png")));
        enemyHitAnimation = new Animation(0.02f, animationFrames, Animation.PlayMode.NORMAL);
    }

}


