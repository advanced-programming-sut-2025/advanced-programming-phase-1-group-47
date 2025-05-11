package models.things.tools;

import models.Point;
import models.Result;
import models.enums.Quality;

public class Hoe extends Tool {
    private Quality quality;

    public Hoe(String name, int itemID, int value, int parentItemID, int amount, Quality quality) {
        super(name, itemID, value, parentItemID, amount);
        this.quality = quality;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }
    public Result useTool(Point point) {
        // فرض کنیم Hoe زمین رو شخم می‌زنه
        return new Result(true, "Used hoe at " + point + " with quality: " + quality);
    }
}
