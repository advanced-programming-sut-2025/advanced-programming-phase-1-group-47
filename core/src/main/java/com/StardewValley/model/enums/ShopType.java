package com.StardewValley.model.enums;

import com.StardewValley.model.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public enum ShopType {
    BlackSmith(GameAssetManager.BLACKSMITHOUT, GameAssetManager.BLACKSMITHIN, new Vector2(1675, 3375), 400, 256),
    JojaMart(GameAssetManager.JOJOMARTOUT, GameAssetManager.JOJOMARTIN, new Vector2(2975, 1875), 256, 256),
    Pierres(GameAssetManager.PIERRESOut, GameAssetManager.PIERRESIN, new Vector2(525, 1800), 256, 256),
    Carpenters(GameAssetManager.CARPENTEROUT, GameAssetManager.CARPENTERIN, new Vector2(1575, 975), 256, 256),
    FishShop(GameAssetManager.FISHSHOPOUT, GameAssetManager.FISHSHOPOT, new Vector2(1875, 675), 256, 256),
    Marnies(GameAssetManager.MARNIESRANCHEOUT, GameAssetManager.MARNIESRANCHIN, new Vector2(1975, 2775), 400, 256),
    TheSaloon(GameAssetManager.SALOONOUT, GameAssetManager.SALOONIN, new Vector2(825, 2175), 256, 256);

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
