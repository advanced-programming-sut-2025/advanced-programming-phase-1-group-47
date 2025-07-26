package com.StardewValley.model;

import com.StardewValley.View.GameScreen;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.enums.Weather;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import static com.StardewValley.model.Time.getHour;

public class TimeAndDate {
    private Sprite mainClock = new Sprite(GameAssetManager.CLOCK_MAIN);
    private Sprite arrow = new Sprite(GameAssetManager.CLOCK_ARROW);
    private Sprite seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[1]);
    private Sprite weather = new Sprite(GameAssetManager.ClOCK_MANNERS[6]);
    private Game currentGame = App.getCurrentGame();
    private Integer hour = 9;
    private Integer minute = 0;
    private Season season = Season.SPRING;
    private Integer day = 1;
    private Integer year = 0;
    public TimeAndDate(int day, int hour, Season season, int year) {
        this.hour = hour;
        this.day = day;
        this.season = season;
        this.year = year;
    }

    public String getDayOfTheWeek() {
        return switch (day % 7) {
            case 0 -> "Saturday";
            case 1 -> "Sunday";
            case 2 -> "Monday";
            case 3 -> "Tuesday";
            case 4 -> "Wednesday";
            case 5 -> "Thursday";
            case 6 -> "Friday";
            default -> "No day";
        };
    }
    public TextureRegion renderClockToTexture() {
        float width = 366;
        float height = 300;

        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int) width, (int) height, false);
        fbo.begin();

        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        // ساعت اصلی
        mainClock.setSize(width, height);
        mainClock.setPosition(0, 0);
        mainClock.draw(batch);

        // فلش ساعت
        arrow.setSize(width * 0.1f, height * 0.28f);
        arrow.setRotation(180 - 180f / 60 / 13 * ((hour - 9)*60 + minute));
        arrow.setPosition(mainClock.getX() + width * 0.3082f - arrow.getWidth()/2 * (float) Math.cos(arrow.getRotation()/180 * Math.PI),
                mainClock.getY() + height / 2 + arrow.getHeight()/2 - arrow.getWidth()/2 * (float) Math.sin(arrow.getRotation()/180 * Math.PI));
        arrow.draw(batch);
        GameAssetManager.MAIN_FONT.getData().setScale(2.43f);
        GameAssetManager.MAIN_FONT.setColor(Color.RED);
        GameAssetManager.MAIN_FONT.draw(batch, getDayOfTheWeek() + " " + 12, width * 0.43f, height * 0.90f);
        GameAssetManager.MAIN_FONT.draw(batch, getHour()%12 + ":00" + (getHour() > 11 ? "p.m." : "a.m."), width * 0.50f, height * 0.50f);

        // وضعیت آب‌وهوا
        weather.setSize(width * 0.180f, height * 0.200f);
        weather.setPosition(0.405f * width, 0.55f * height);
        weather.draw(batch);

        // فصل
        seasonSprite.setSize(weather.getWidth(), weather.getHeight());
        seasonSprite.setPosition(weather.getX() + 0.33f * width, weather.getY());
        seasonSprite.draw(batch);

        // طلا
        int golds = App.currentGame.currentPlayer.getMoney();
        for (int i = 0; golds > 0; golds /= 10, i++) {
            GameAssetManager.MAIN_FONT.draw(batch, "" + golds % 10,
                    width * (0.826f - 0.082f*i), height * 0.17f);
        }

        batch.end();
        fbo.end();

        TextureRegion textureRegion = new TextureRegion(fbo.getColorBufferTexture());
        textureRegion.flip(false, true); // لازم برای اصلاح وارونگی محور Y

        return textureRegion;
    }

    public void setWeatherSprite(Weather weather) {
        switch (weather) {
            case RAINY -> this.weather = new Sprite(GameAssetManager.ClOCK_MANNERS[6]);
            case SNOWY -> this.weather = new Sprite(GameAssetManager.ClOCK_MANNERS[9]);
            case STORMY -> this.weather = new Sprite(GameAssetManager.ClOCK_MANNERS[11]);
            case SUNNY -> this.weather = new Sprite(GameAssetManager.ClOCK_MANNERS[7]);
        }
    }
    public void setSeasonSprite() {
        switch (season) {
            case SPRING -> this.seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[0]);
            case SUMMER -> this.seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[1]);
            case FALL -> this.seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[2]);
            case WINTER -> this.seasonSprite = new Sprite(GameAssetManager.ClOCK_MANNERS[4]);
        }
    }
}

