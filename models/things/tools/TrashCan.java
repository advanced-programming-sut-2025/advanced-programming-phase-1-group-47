package models.things.tools;

import models.Point;
import models.Result;
import models.enums.Quality;

public class TrashCan extends Tool {
    private Quality quality;

    public TrashCan(String name, int itemID, int value, int parentItemID, int amount, Quality quality) {
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
        return 0;
    }

    @Override
    public Result<String> useTool(Point point) {
        return new Result<>(true, "TrashCan used at point " + point);
    }

}
