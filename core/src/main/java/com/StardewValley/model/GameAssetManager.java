package com.StardewValley.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    // ذخیره تکسچرها برای آزادسازی حافظه
    private final List<Texture> loadedTextures = new ArrayList<>();

    // ذخیره تکسچرهای سلاح‌ها
    // نام کاراکترها (ثابت برای دسترسی راحت‌تر)
    public final String dasher = "dasher";
    public final String diamond = "diamond";
    public final String lilith = "lilith";
    public final String scarlet = "scarlet";
    public final String shana = "shana";

    // سازنده خصوصی برای الگوی Singleton
    private GameAssetManager() {
    }
    // دریافت Singleton Instance
    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }
    
    public Skin getSkin() {
        return skin;
    }

    public void dispose() {
        for (Texture tex : loadedTextures) {
            tex.dispose();
        }

        skin.dispose();
    }
}
