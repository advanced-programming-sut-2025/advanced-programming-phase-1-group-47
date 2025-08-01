package com.StardewValley.model.things.machines;

import com.StardewValley.model.things.Item;

import java.util.ArrayList;

public class Kiln extends Machine{
    
    private static final ArrayList<Operation> kilnOperations = new ArrayList<>();
    static {
        kilnOperations.add(new Operation(1,1, new Item("Wood",36,2,0,10),
          new Item("Coal",396,2,0,1), false));
    }

    public Kiln() {
        super("Charcoal Kiln", 12, 20, 0, 1,kilnOperations);
    }
}
