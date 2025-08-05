package com.StardewValley.model.enums;

import com.StardewValley.model.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import java.util.List;

public enum AnimalType {
    COW(
            "cow", 1500,
            List.of(AnimalProductType.BIG_MILK, AnimalProductType.MILK),
            1, true, 701,
            GameAssetManager.COW_TEXTURE
    ),
    DINOSAUR(
            "dinosaur", 1400,
            List.of(AnimalProductType.DINOSAUR_EGG),
            7, false, 702,
            GameAssetManager.DINOSAUR_TEXTURE
    ),
    DUCK(
            "duck", 1200,
            List.of(AnimalProductType.DUCK_EGG, AnimalProductType.DUCK_FEATHER),
            2, false, 703,
            GameAssetManager.DUCK_TEXTURE
    ),
    GOAT(
            "goat", 4000,
            List.of(AnimalProductType.BIG_GOAT_MILK, AnimalProductType.GOAT_MILK),
            2, true, 704,
            GameAssetManager.GOAT_TEXTURE
    ),
    HEN(
            "hen", 800,
            List.of(AnimalProductType.HEN_EGG, AnimalProductType.HEN_BIG_EGG),
            1, false, 705,
            GameAssetManager.HEN_TEXTURE
    ),
    PIG(
            "pig", 16000,
            List.of(AnimalProductType.TRUFFLE),
            0, true, 706,
            GameAssetManager.PIG_TEXTURE
    ),
    RABBIT(
            "rabbit", 8000,
            List.of(AnimalProductType.RABBIT_LEG, AnimalProductType.RABBIT_WOOL),
            4, false, 707,
            GameAssetManager.RABBIT_TEXTURE
    ),
    SHEEP(
            "sheep", 8000,
            List.of(AnimalProductType.SHEEP_WOOL),
            3, true, 708,
            GameAssetManager.SHEEP_TEXTURE
    );

    private final String name;
    private final int price;
    private final int itemId;
    private final List<AnimalProductType> products;
    private final int productionInterval;
    private final boolean isBarnAnimal;
    private final Texture texture;

    AnimalType(String name, int price, List<AnimalProductType> products,
               int productionInterval, boolean isBarnAnimal, int itemId, Texture texture) {
        this.name = name;
        this.price = price;
        this.products = products;
        this.productionInterval = productionInterval;
        this.isBarnAnimal = isBarnAnimal;
        this.itemId = itemId;
        this.texture = texture;
    }

    public String getName() {
        return name.toLowerCase();
    }

    public int getPrice() {
        return price;
    }

    public int getSellPrice() {
        return price;
    }

    public List<AnimalProductType> getProductList() {
        return products;
    }

    public int getProductPeriod() {
        return productionInterval;
    }

    public boolean isBarnAnimal() {
        return isBarnAnimal;
    }

    public int getEnergy() {
        return 0;
    }

    public int getContainingEnergy() {
        return 0;
    }

    public int getItemId() {
        return itemId;
    }

    public Texture getTexture() {
        return texture;
    }
}
