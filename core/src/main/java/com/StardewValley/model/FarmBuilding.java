package com.StardewValley.model;

import com.badlogic.gdx.graphics.Texture;

public enum FarmBuilding {
    BARN(1001, 3500, 4, 7, 4, true, false,
            GameAssetManager.BARN_OUT_TEXTURE, GameAssetManager.BARN_IN_TEXTURE),
    BIG_BARN(1002, 12000, 8, 7, 4, true, false,
            GameAssetManager.BIG_BARN_OUT_TEXTURE, GameAssetManager.BIG_BARN_IN_TEXTURE),
    DELUXE_BARN(1003, 25000, 12, 7, 4, true, false,
            GameAssetManager.DELUXE_BARN_OUT_TEXTURE, GameAssetManager.DELUXE_BARN_IN_TEXTURE),
    COOP(1004, 4000, 4, 6, 3, false, true,
            GameAssetManager.COOP_OUT_TEXTURE, GameAssetManager.COOP_IN_TEXTURE),
    BIG_COOP(1005, 10000, 8, 6, 3, false, true,
            GameAssetManager.BIG_COOP_OUT_TEXTURE, GameAssetManager.BIG_COOP_IN_TEXTURE),
    DELUXE_COOP(1006, 20000, 12, 6, 3, false, true,
            GameAssetManager.DELUXE_COOP_OUT_TEXTURE, GameAssetManager.DELUXE_COOP_IN_TEXTURE),
    WELL(1007, 1000, 10, 3, 3, false, false,
            GameAssetManager.WELL_OUT_TEXTURE, GameAssetManager.WELL_IN_TEXTURE),
    SHIPPING_BIN(1008, 0, 20, 1, 1, false, false,
            GameAssetManager.SHIPPING_BIN_OUT_TEXTURE, GameAssetManager.SHIPPING_BIN_IN_TEXTURE);

    private final int id;
    private final int price;
    private final Integer capacity;
    private final Integer height;
    private final Integer width;
    private final Boolean isBarn;
    private final Boolean isCoop;
    private final Texture outTexture;
    private final Texture inTexture;

    FarmBuilding(int id, int price, Integer capacity, Integer height, Integer width,
                 Boolean isBarn, Boolean isCoop, Texture outTexture, Texture inTexture) {
        this.id = id;
        this.price = price;
        this.capacity = capacity;
        this.height = height;
        this.width = width;
        this.isBarn = isBarn;
        this.isCoop = isCoop;
        this.outTexture = outTexture;
        this.inTexture = inTexture;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public Boolean getIsBarn() {
        return isBarn;
    }

    public Boolean getIsCoop() {
        return isCoop;
    }

    public Texture getOutTexture() {
        return outTexture;
    }

    public Texture getInTexture() {
        return inTexture;
    }
}
