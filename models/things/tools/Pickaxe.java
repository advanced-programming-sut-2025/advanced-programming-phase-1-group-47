package models.things.tools;

import models.Point;
import models.Result;

public class Pickaxe extends Tool {

    public Pickaxe(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }

    public Result useTool(Point point) {
        return new Result(true, "Pickaxe used at point " + point);
    }
}
