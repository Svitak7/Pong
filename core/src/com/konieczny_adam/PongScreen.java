package com.konieczny_adam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.konieczny_adam.utils.Assets;


/**
 * Created by Adam on 23.04.2017.
 */

public class PongScreen extends InputAdapter implements Screen {

    PongGame game;

    ExtendViewport pongViewport;

    ShapeRenderer renderer;

    SpriteBatch batch;

    Sound sound;

    Ball ball;

    Player player;

    Enemy enemy;

    AI ai;

    @Override
    public void show() {

        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        pongViewport = new ExtendViewport(com.konieczny_adam.utils.Constants.SCREEN_WIDTH, com.konieczny_adam.utils.Constants.SCREEN_HEIGHT);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        sound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        player = new Player(pongViewport);
        enemy = new Enemy(pongViewport);
        ball = new Ball(pongViewport,player,enemy,sound);
        ai = new AI(ball,enemy,pongViewport);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ball.update(delta);
        player.update(delta);
        enemy.update(delta);
        ai.update(delta);
        pongViewport.apply();
        Gdx.gl.glClearColor(com.konieczny_adam.utils.Constants.BACKGROUND_COLOR.r, com.konieczny_adam.utils.Constants.BACKGROUND_COLOR.g, com.konieczny_adam.utils.Constants.BACKGROUND_COLOR.b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(pongViewport.getCamera().combined);
        batch.begin();
        ball.render(batch);
        player.render(batch);
        enemy.render(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        pongViewport.update(width, height, true);
        ball.init();
        player.init();
        enemy.init();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        renderer.dispose();
        sound.dispose();
        batch.dispose();
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();

    }
}
