package com.StardewValley.model.things.machines;

import com.StardewValley.model.things.Item;

import java.util.ArrayList;

public class BeeHouse extends Machine {
    
    private static final ArrayList<Operation> beehouseOperations = new ArrayList<>();
    static {
        beehouseOperations.add(new Operation(1,96, new Item("null",0,0,0, 1),
           new Item("Honey",22,350,0,1), false));
    }

    public BeeHouse() {
        super("Bee House", 43, 20, 0, 1,beehouseOperations);
    }
}
