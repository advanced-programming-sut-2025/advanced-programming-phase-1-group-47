package com.StardewValley.model;

import com.StardewValley.model.enums.FishType;
import com.StardewValley.model.enums.ProductQuality;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.things.Item;

public class Fish extends Item {
    private FishType fishType;
    private Integer containingEnergy = 20; //TODO Energy not found
    private ProductQuality productQuality = ProductQuality.NORMAL;
    public int getSellPrice() {
        return (int) (this.fishType.getSellPrice() * productQuality.getPriceCoefficient());
    }
    @Override
    public Season getSeason() {
        return fishType.getSeason();
    }
    public Fish(FishType fishType) {
        super(fishType.getName(), fishType.getId(), fishType.getSellPrice(), 10500,1);
        this.fishType = fishType;
    }
    public Fish(FishType fishType, ProductQuality productQuality) {
        super(String.valueOf(fishType), fishType.getId(), fishType.getSellPrice(), 10500,1);
        this.fishType = fishType;
        this.productQuality = productQuality;
    }

    @Override
    public String getName() {
        return this.fishType.getName();
    }
}