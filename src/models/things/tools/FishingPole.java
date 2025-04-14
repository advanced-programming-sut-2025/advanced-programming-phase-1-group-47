package models.things.tools;

import models.Point;
import models.Result;
import models.enums.Rods;

public class FishingPole extends Tool{
    private Rods rod;
    public FishingPole(Rods rod) {
        this.rod = rod;
    }
    public Result useTool(Tool tool, Point point) {
        super.useTool(tool, point);
        return null;
    }
} 
