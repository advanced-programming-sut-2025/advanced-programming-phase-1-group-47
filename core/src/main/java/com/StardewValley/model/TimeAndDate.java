package com.StardewValley.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;


public class TimeAndDate {
    private static Sprite mainClock = new Sprite(GameAssetManager.CLOCK_MAIN);
    private static Sprite arrow = new Sprite(GameAssetManager.CLOCK_ARROW);
    private static Sprite seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[1]);
    private static Sprite weather = new Sprite(GameAssetManager.ClOCK_MANNERS[6]);
    private static Game currentGame = App.getCurrentGame();
    private static Time time = currentGame.getTime();
    public static TextureRegion renderClockToTexture() {
        float width = 366;
        float height = 300;
        setSeasonSprite();
        setWeatherSprite();
        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int) width, (int) height, false);
        fbo.begin();

        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        mainClock.setSize(width, height);
        mainClock.setPosition(0, 0);
        mainClock.draw(batch);
        arrow.setSize(width * 0.1f, height * 0.28f);
        arrow.setRotation(180 - 180f / 60 / 13 * ((time.getHourOfDay() - 9)*60 + 0));
        arrow.setPosition(mainClock.getX() + width * 0.3082f - arrow.getWidth()/2 * (float) Math.cos(arrow.getRotation()/180 * Math.PI),
                mainClock.getY() + height / 2 + arrow.getHeight()/2 - arrow.getWidth()/2 * (float) Math.sin(arrow.getRotation()/180 * Math.PI));
        arrow.draw(batch);
        GameAssetManager.MAIN_FONT.getData().setScale(2.43f);
        GameAssetManager.MAIN_FONT.setColor(Color.RED);
        GameAssetManager.MAIN_FONT.draw(batch, time.getSeason().name() + " " + time.getDayOfSeason(), width * 0.43f, height * 0.90f);
        GameAssetManager.MAIN_FONT.draw(batch, time.getHourOfDay()%12 + ":00" + (time.getHourOfDay() <= 11 ? "a.m." : "p.m."), width * 0.50f, height * 0.50f);

        weather.setSize(width * 0.180f, height * 0.200f);
        weather.setPosition(0.405f * width, 0.55f * height);
        weather.draw(batch);

        seasonSprite.setSize(weather.getWidth(), weather.getHeight());
        seasonSprite.setPosition(weather.getX() + 0.33f * width, weather.getY());
        seasonSprite.draw(batch);

        int golds = App.currentGame.currentPlayer.getMoney();
        for (int i = 0; golds > 0; golds /= 10, i++) {
            GameAssetManager.MAIN_FONT.draw(batch, "" + golds % 10,
                    width * (0.826f - 0.082f*i), height * 0.17f);
        }

        batch.end();
        fbo.end();

        TextureRegion textureRegion = new TextureRegion(fbo.getColorBufferTexture());
        textureRegion.flip(false, true);

        return textureRegion;
    }

    public static void setWeatherSprite() {
        switch (currentGame.weather) {
            case RAINY -> weather = new Sprite(GameAssetManager.ClOCK_MANNERS[6]);
            case SNOWY -> weather = new Sprite(GameAssetManager.ClOCK_MANNERS[9]);
            case STORMY -> weather = new Sprite(GameAssetManager.ClOCK_MANNERS[11]);
            case SUNNY -> weather = new Sprite(GameAssetManager.ClOCK_MANNERS[7]);
        }
    }
    public static void setSeasonSprite() {
        switch (time.getSeason()) {
            case SPRING -> seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[0]);
            case SUMMER -> seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[1]);
            case FALL -> seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[2]);
            case WINTER -> seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[4]);
        }
    }
}

