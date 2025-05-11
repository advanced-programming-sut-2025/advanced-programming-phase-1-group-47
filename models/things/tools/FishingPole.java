package models.things.tools;

import models.Point;
import models.Result;
import models.enums.Rods;

public class FishingPole extends Tool {
    private Rods rod;

    public FishingPole(String name, int itemID, int value, int parentItemID, int amount, Rods rod) {
        super(name, itemID, value, parentItemID, amount);
        this.rod = rod;
    }

    public Rods getRod() {
        return rod;
    }

    public void setRod(Rods rod) {
        this.rod = rod;
    }

    public Result useTool(Point point) {
        return new Result(true, "Fishing with rod: " + rod + " at point " + point);
    }
}
