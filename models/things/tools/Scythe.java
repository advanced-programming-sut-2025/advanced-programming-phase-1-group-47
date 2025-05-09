package models.things.tools;

import models.Point;
import models.Result;

public class Scythe extends Tool {

    public Scythe(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }

    @Override
    public int getEnergyCost() {
        return 2;
    }

    @Override
    public Result<String> useTool(Point point) {
        return new Result<>(true, "Scythe used at point " + point);
    }
}
