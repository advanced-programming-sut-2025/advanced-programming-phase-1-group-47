package models;

import models.enums.FishType;
import models.enums.ProductQuality;
import models.enums.Season;
import models.things.Item;

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