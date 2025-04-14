package models;

import models.enums.Season;
import models.things.products.Crop;

public class CropPlant extends Plant {
    private int growthModifier;
    private Season season;
    private boolean canBeGiant;

    public CropPlant(Point point, Map map, String name) {
        super(point, map, name);
    }

    public int getGrowthModifier() {
        return growthModifier;
    }

    public void setGrowthModifier(int growthModifier) {
        this.growthModifier = growthModifier;
    }

    public Crop Harvest() {
        return null;
    }

}
