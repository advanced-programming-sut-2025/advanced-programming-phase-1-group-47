package com.StardewValley.model.things.machines;

import com.StardewValley.model.things.Item;

import java.util.ArrayList;

public class CheesePress extends Machine {
    
    private static final ArrayList<Operation> cheesePressOperations = new ArrayList<>();
    static {
        cheesePressOperations.add(new Operation(1,3, new Item("milk",907,2,0, 1),
          new Item("Cheese", 15, 230, 0, 1), false));
        cheesePressOperations.add(new Operation(2,3, new Item("Large milk ",908,2,0, 1),
         new Item("Large Cheese", 16, 345, 0, 1), false));
        cheesePressOperations.add(new Operation(3,3, new Item("goat milk",909,2,0, 1),
          new Item("Goat Cheese", 17, 400, 0, 1), false));
        cheesePressOperations.add(new Operation(4,3, new Item("Large goat milk",910,2,0, 1),
          new Item("Large Goat Cheese", 18, 600, 0, 1), false));
    }

    public CheesePress() {
        super("Cheese Press", 43, 20, 0, 1,cheesePressOperations);
    }
}
