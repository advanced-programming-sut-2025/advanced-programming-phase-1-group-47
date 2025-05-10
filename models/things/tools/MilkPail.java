package models.things.tools;

import models.Point;
import models.Result;

public class MilkPail extends Tool {

    public MilkPail(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }

    @Override
    public int getEnergyCost() {
        return 4;
    }

    @Override
    public Result<String> useTool(Point point) {
        // فرض کنیم این ابزار برای جمع‌آوری شیر از یک نقطه استفاده می‌شود
        return new Result<>(true, "Milk pail used at " + point);
    }
}
