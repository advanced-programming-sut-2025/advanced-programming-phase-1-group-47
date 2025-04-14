package models.things.tools;

import models.Point;
import models.Result;
import models.things.Item;

public class Axe extends Tool{
    @Override
    public Result useTool(Tool tool, Point point) {
        super.useTool(tool, point);
        return null;
    }
 
}
