package com.konieczny_adam.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Adam on 21.05.2017.
 */

public class Utils {

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, float x, float y,float xOffset, float yOffset) {
        batch.draw(
                region.getTexture(),
                x + xOffset,
                y + yOffset,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, float x, float y,float xOffset, float yOffset, int rotation,float originX, float originY ) {
        batch.draw(
                region.getTexture(),
                x + xOffset,
                y + yOffset,
                originX,
                originY,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                rotation,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public static float secondsSince(long timeNanos) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeNanos);
    }
}
