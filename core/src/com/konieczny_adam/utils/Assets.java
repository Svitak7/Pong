package com.konieczny_adam.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Adam on 20.05.2017.
 */

public class Assets implements Disposable, AssetErrorListener{

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    public BallAssets ballAssets;

    private Assets(){

    }

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        ballAssets = new BallAssets(atlas);
    }



    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Nie moge zaladowac asseta: "+ asset.fileName, throwable);
    }

    @Override
    public void dispose() {assetManager.dispose();}


    public class BallAssets {

        public final AtlasRegion ballImage;
        public final Animation ballHit;
        public final Animation rotatingRightAnimation;
        public final Animation rotatingLeftAnimation;


        public BallAssets(TextureAtlas atlas) {

            ballImage = atlas.findRegion(Constants.PONG_BALL_RIGHT1);

            Array<AtlasRegion> ballHitFrames = new Array<AtlasRegion>();
            ballHitFrames.add(atlas.findRegion(Constants.PONG_BALL_HIT));
            ballHit = new Animation(Constants.HIT_LOOP_DURATION,ballHitFrames, PlayMode.NORMAL);




            Array<AtlasRegion> rotatingRightFrames = new Array<AtlasRegion>();
            rotatingRightFrames.add(atlas.findRegion(Constants.PONG_BALL_RIGHT1));
            rotatingRightFrames.add(atlas.findRegion(Constants.PONG_BALL_RIGHT2));
            rotatingRightFrames.add(atlas.findRegion(Constants.PONG_BALL_RIGHT3));
            rotatingRightFrames.add(atlas.findRegion(Constants.PONG_BALL_RIGHT4));
            rotatingRightAnimation = new Animation(Constants.HIT_LOOP_DURATION,rotatingRightFrames, PlayMode.LOOP);

            Array<AtlasRegion> rotatingLeftFrames = new Array<AtlasRegion>();
            rotatingLeftFrames.add(atlas.findRegion(Constants.PONG_BALL_LEFT1));
            rotatingLeftFrames.add(atlas.findRegion(Constants.PONG_BALL_LEFT2));
            rotatingLeftFrames.add(atlas.findRegion(Constants.PONG_BALL_LEFT3));
            rotatingLeftFrames.add(atlas.findRegion(Constants.PONG_BALL_LEFT4));
            rotatingLeftAnimation = new Animation(Constants.HIT_LOOP_DURATION,rotatingLeftFrames, PlayMode.LOOP);





        }


    }




}
