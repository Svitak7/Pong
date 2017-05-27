package com.konieczny_adam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.konieczny_adam.utils.Assets;
import com.konieczny_adam.utils.Constants;

import static com.badlogic.gdx.Gdx.app;
import static com.konieczny_adam.utils.Utils.drawTextureRegion;
import static com.konieczny_adam.utils.Utils.secondsSince;

/**
 * Created by Adam on 23.04.2017.
 */

public class Ball {


    Vector2 velocity;
    Vector2 position;
    Viewport viewport;
    Player player;
    Enemy enemy;
    boolean gameStart = false;
    boolean leftStart = true;
    boolean rightStart = false;
    Sound sound;
    float rotationAcceleration = 0;


    static boolean hitPlayer = false;
    static boolean hitEnemy = false;
    static boolean hitPlayerAnimation = false;
    static boolean hitEnemyAnimation = false;
    boolean hitWall = false;
    long animationStartTime;
    float rotationSpeedAnimation;

    BallState ballState = BallState.STATIC;

    TextureRegion region = Assets.instance.ballAssets.ballImage;

    public Ball(Viewport viewport, Player player, Enemy enemy, Sound sound) {
        this.viewport = viewport;
        this.velocity = new Vector2(0, 0);
        this.player = player;
        this.enemy = enemy;
        this.sound = sound;
        init();
    }


    public void render(SpriteBatch batch) {

        if (ballState == BallState.STATIC) region = Assets.instance.ballAssets.ballImage;

        if (ballState == BallState.LEFT) {
            rotationSpeedAnimation = (float) (rotationAcceleration);
            rotationSpeedAnimation = 100 / ((-1 * rotationSpeedAnimation) * 3);
            region = (TextureRegion) Assets.instance.ballAssets.rotatingLeftAnimation.getKeyFrame(secondsSince(animationStartTime));
            Assets.instance.ballAssets.rotatingLeftAnimation.setFrameDuration(rotationSpeedAnimation);
        }

        if (ballState == BallState.RIGHT) {
            rotationSpeedAnimation = (float) (rotationAcceleration);
            rotationSpeedAnimation = 100 / ((rotationSpeedAnimation) * 3);
            region = (TextureRegion) Assets.instance.ballAssets.rotatingRightAnimation.getKeyFrame(secondsSince(animationStartTime));
            Assets.instance.ballAssets.rotatingRightAnimation.setFrameDuration(rotationSpeedAnimation);
        }

        if (hitEnemyAnimation || hitPlayerAnimation) {
            region = (TextureRegion) Assets.instance.ballAssets.ballHit.getKeyFrame(secondsSince(animationStartTime));
            if (Assets.instance.ballAssets.ballHit.isAnimationFinished(secondsSince(animationStartTime))) {
                hitEnemyAnimation = false;
                hitPlayerAnimation = false;
            }
        }
        if (hitWall) {
            region = (TextureRegion) Assets.instance.ballAssets.ballHit.getKeyFrame(secondsSince(animationStartTime));
            if (Assets.instance.ballAssets.ballHit.isAnimationFinished(secondsSince(animationStartTime))) {
                hitWall = false;
            }
            drawTextureRegion(batch, region, position.x, position.y, 0, -2 * Constants.BALL_RADIUS, 90, region.getRegionHeight() / 2, region.getRegionWidth() / 2);
            return;

        }


        drawTextureRegion(batch, region, position.x, position.y, 0, -2 * Constants.BALL_RADIUS);
    }


    public void update(float delta) {
        start();
        if (gameStart) {

            //LEWO - rotacja na minussie
            //PRAWO - rotacja na plusie
            // rotationAcceleration-=15;
            if (rotationAcceleration > 0 && ballState == BallState.LEFT) {
                rotationAcceleration = 0;
            } else if (rotationAcceleration < 0 && ballState == BallState.LEFT) {
                rotationAcceleration += 2;
                app.log("LEWO", "rotacja zmnieszana");
            }

            if (rotationAcceleration < 0 && ballState == BallState.RIGHT) {
                rotationAcceleration = 0;
            } else if (rotationAcceleration > 0 && ballState == BallState.RIGHT) {
                app.log("PRAWO", "rotacja zmnieszana");
                rotationAcceleration -= 2;
            }


            velocity.x += delta * rotationAcceleration;
            position.y += delta * velocity.y;
            position.x += delta * velocity.x;
            app.log("KIERUNEK", " " + ballState);
            app.log("Rotacja", " " + rotationAcceleration);
            collision();
            playerCollision(delta);
            enemyCollision(delta);
        } else {
            position.x = player.position.x + com.konieczny_adam.utils.Constants.PLAYER_WIDTH / 2;
        }
    }

    public void start() {
        if (!gameStart) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                leftStart = true;
                rightStart = false;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                rightStart = true;
                leftStart = false;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

                if (rightStart) {
                    velocity.x = com.konieczny_adam.utils.Constants.BALL_SPEED;
                    velocity.y = com.konieczny_adam.utils.Constants.BALL_SPEED;
                }
                if (leftStart) {
                    velocity.x = -com.konieczny_adam.utils.Constants.BALL_SPEED;
                    velocity.y = com.konieczny_adam.utils.Constants.BALL_SPEED;
                }


                gameStart = true;
            }

            if (Gdx.input.getAccelerometerY() < 0) {
                leftStart = true;
                rightStart = false;
            }

            if (Gdx.input.getAccelerometerY() > 0) {
                rightStart = true;
                leftStart = false;
            }

            if (Gdx.input.isTouched()) {

                if (rightStart) {
                    velocity.x = com.konieczny_adam.utils.Constants.BALL_SPEED;
                    velocity.y = com.konieczny_adam.utils.Constants.BALL_SPEED;
                }
                if (leftStart) {
                    velocity.x = -com.konieczny_adam.utils.Constants.BALL_SPEED;
                    velocity.y = com.konieczny_adam.utils.Constants.BALL_SPEED;
                }
                gameStart = true;
            }
        }
    }


    public void init() {
        position = new Vector2(player.position.x + com.konieczny_adam.utils.Constants.PLAYER_WIDTH, com.konieczny_adam.utils.Constants.PLAYER_HEIGHT * 2 + com.konieczny_adam.utils.Constants.BALL_RADIUS);
        gameStart = false;
        rotationAcceleration = 0;
        ballState = BallState.STATIC;
        hitPlayer = false;
        hitPlayerAnimation = false;
        hitEnemy = false;
        hitEnemyAnimation = false;
    }

    public void collision() {
        if (position.x < -Constants.BALL_RADIUS) //odbicie z lewej
        {
            animationStartTime = TimeUtils.nanoTime();
            hitWall = true;
            position.x = -Constants.BALL_RADIUS;
            velocity.x = -1 * velocity.x;
            if (rotationAcceleration == 0) {
                rotationAcceleration = 0;
            } else if (rotationAcceleration + com.konieczny_adam.utils.Constants.WALL_FRICTION < 0) {
                rotationAcceleration = (rotationAcceleration + com.konieczny_adam.utils.Constants.WALL_FRICTION);
            } else {
                rotationAcceleration = 0;
            }
            sound.play();
        }

        if (position.x > viewport.getWorldWidth() - 2 * com.konieczny_adam.utils.Constants.BALL_RADIUS) //odbicie z prawej
        {
            animationStartTime = TimeUtils.nanoTime();
            hitWall = true;
            position.x = viewport.getWorldWidth() - 2 * com.konieczny_adam.utils.Constants.BALL_RADIUS;
            velocity.x = -1 * velocity.x;
            if (rotationAcceleration == 0) {
                rotationAcceleration = 0;
            } else if (rotationAcceleration - com.konieczny_adam.utils.Constants.WALL_FRICTION > 0) {
                rotationAcceleration = (rotationAcceleration - com.konieczny_adam.utils.Constants.WALL_FRICTION);
            } else {
                rotationAcceleration = 0;
            }
            sound.play();
        }


    }

    public void playerCollision(float delta) {
        if (position.y < Constants.PLAYER_HEIGHT * 2 + Constants.BALL_RADIUS - 5 && hitPlayer == false
                && position.x > player.position.x && position.x < player.position.x + Constants.PLAYER_WIDTH) {
            hitPlayerAnimation = true;
            hitPlayer = true;
            hitEnemy = false;
            animationStartTime = TimeUtils.nanoTime();
            position.y = Constants.PLAYER_HEIGHT * 2 + Constants.BALL_RADIUS - 5;
            velocity.y = -1 * velocity.y;
            sound.play();
            if (player.leftDirection) {

                rotationAcceleration += (player.velocity - Constants.PLAYER_SPEED) * 2.5;
            } else {
                rotationAcceleration += -1 * (player.velocity - Constants.PLAYER_SPEED) * 2.5;
            }

            if (rotationAcceleration > 0) {
                ballState = BallState.RIGHT;
            } else ballState = BallState.LEFT;

        } else if (position.y < Constants.PLAYER_HEIGHT * 2 + Constants.BALL_RADIUS - 5) {
            hitPlayer = true;
        }

        if (position.y < -Constants.BALL_RADIUS) {
            init();
        }
    }


    public void enemyCollision(float delta) {
        if (position.y > viewport.getWorldHeight() - Constants.ENEMY_HEIGHT * 2 - Constants.BALL_RADIUS + 5
                && position.x > enemy.position.x && position.x < enemy.position.x + Constants.ENEMY_WIDTH && hitEnemy == false) {
            hitEnemyAnimation = true;
            hitEnemy = true;
            animationStartTime = TimeUtils.nanoTime();
            hitPlayer = false;
            position.y = viewport.getWorldHeight() - Constants.ENEMY_HEIGHT * 2 - Constants.BALL_RADIUS + 5;
            velocity.y = -1 * velocity.y;
            sound.play();

        } else if (position.y > viewport.getWorldHeight() - Constants.ENEMY_HEIGHT * 2 - Constants.BALL_RADIUS + 5) {
            hitEnemy = true;
        }

        if (position.y > viewport.getWorldHeight() + Constants.BALL_RADIUS) {
            init();
        }

    }

    enum BallState {
        STATIC,
        RIGHT,
        LEFT,
    }


}
