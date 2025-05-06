package models.things.machines;

import models.things.Item;

public abstract class Machine extends Item {

    public Machine(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }
}