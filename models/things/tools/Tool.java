package models.things.tools;

import models.Point;
import models.Result;
import models.things.Item;

public abstract class Tool extends Item {

    public Tool(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }

    public abstract int getEnergyCost();

    public abstract Result<String> useTool(Point point);
}
