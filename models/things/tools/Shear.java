package models.things.tools;

import models.Point;
import models.Result;

public class Shear extends Tool {

    public Shear(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }
    public Result useTool(Point point) {
        // منطق ابزار Shear برای اصلاح حیوان
        return new Result(true, "Shear used successfully at point: " + point);
    }
}
