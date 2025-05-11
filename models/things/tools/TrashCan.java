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
    public Result useTool(Point point) {
        return new Result(true, "Trash can used at point " + point);
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }
}
