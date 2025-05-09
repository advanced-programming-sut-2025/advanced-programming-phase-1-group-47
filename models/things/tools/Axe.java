package models.things.tools;

import models.Point;
import models.Result;
import models.enums.Quality;

public class Axe extends Tool {

    private Quality quality;

    public Axe(String name, int itemID, int value, int parentItemID, int amount, Quality quality) {
        super(name, itemID, value, parentItemID, amount);
        this.quality = quality;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    @Override
    public int getEnergyCost() {
        return quality.getEnergy();
    }

    @Override
    public Result<String> useTool(Point point) {
        return new Result<>(true, "Axe used at point " + point);
    }
}
