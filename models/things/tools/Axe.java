package models.things.tools;

import models.Point;
import models.Result;

public class Axe extends Tool {

    public Axe(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }

    public Result useTool(Point point) {
        return new Result(true, "Axe used at point " + point);
    }
}
