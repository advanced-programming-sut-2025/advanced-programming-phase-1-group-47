package models.things.tools;

import models.Point;
import models.Result;
import models.things.Item;

public abstract class Tool extends Item {
    public void Item(int ItemID , int value){
        super.Item(ItemID, value);
    }
    public Result useTool(Tool tool, Point point){
        return null;
    }
}
