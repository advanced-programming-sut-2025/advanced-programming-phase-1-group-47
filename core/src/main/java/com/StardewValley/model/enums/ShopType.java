package com.StardewValley.model.enums;

import com.StardewValley.model.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public enum ShopType {
    BlackSmith(GameAssetManager.BLACKSMITHOUT, GameAssetManager.BLACKSMITHIN, new Vector2(60, 110), 8, 8),
    Marnies(GameAssetManager.MARNIESRANCHEOUT, GameAssetManager.MARNIESRANCHIN, new Vector2(70, 90), 8, 8),
    Carpenters(GameAssetManager.CARPENTEROUT, GameAssetManager.CARPENTERIN, new Vector2(80, 30), 8, 8),
    FishShop(GameAssetManager.FISHSHOPOUT, GameAssetManager.FISHSHOPOT, new Vector2(60, 20), 8, 8),
    JojaMart(GameAssetManager.JOJOMARTOUT, GameAssetManager.JOJOMARTIN, new Vector2(120, 60), 8, 8),
    TheSaloon(GameAssetManager.SALOONOUT, GameAssetManager.SALOONIN, new Vector2(25, 70), 8, 8),
    Pierres(GameAssetManager.PIERRESOut, GameAssetManager.PIERRESIN, new Vector2(15, 50), 8, 8);

    private final Texture outTexture;
    private final Texture inTexture;
    private final Vector2 position;
    private final int width;
    private final int height;

    ShopType(Texture outTexture, Texture inTexture, Vector2 position, int width, int height) {
        this.outTexture = outTexture;
        this.inTexture = inTexture;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Texture getOutTexture() {
        return outTexture;
    }

    public Texture getInTexture() {
        return inTexture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
