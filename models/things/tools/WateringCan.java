package models.things.tools;

import models.Point;
import models.Result;

public class WateringCan extends Tool {

    public WateringCan(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }

    public Result useTool(Point point) {
        return new Result(true, "Watering can used at point " + point);
    }
}
