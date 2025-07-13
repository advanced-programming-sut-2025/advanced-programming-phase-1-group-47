package com.StardewValley.model.things.machines;

import com.StardewValley.model.things.Item;

import java.util.ArrayList;
public class Keg extends Machine {
    private static final ArrayList<Operation> kegOperations = new ArrayList<>(); 
    static {
        kegOperations.add(new Operation(1,24, new Item("Wheat",328,2,0 ,1),
        new Item("Beer",26,200,0,1), false));
        kegOperations.add(new Operation(2,10,  new Item("Rice",48,2,0,1),
        new Item("Vinegar", 31, 100, 0, 1), false));
        kegOperations.add(new Operation(3,2, new Item("Coffee Bean",305,2,0, 5),
         new Item("Coffee",6,150,0,1), false));
        kegOperations.add(new Operation(4,96, new Item("Vegatable",299,0,0, 1),
        new Item("null",0,0,0, 1), false));
        kegOperations.add(new Operation(5,10, new Item("Honey",22,350,0,1),
        new Item("Mead", 23, 300, 0, 1), false));
        kegOperations.add(new Operation(6,72, new Item("Hops",317,2,0, 1),
        new Item("Pale ale", 32, 300, 0, 1), false));
        kegOperations.add(new Operation(7,168, new Item("Fruit",300,1,0,1),
        new Item("null",0,0,0, 1), false));
    }

    public Keg() {
        super("Keg", 20, 20, 0, 1,kegOperations);
    }
    @Override
    public Item getOutput() {
        switch (currentOperation.getId()) {
            case 4:
                return currentOperation.getInput().getJuice();
            case 7:
                return currentOperation.getInput().getWine();
            default:
                return currentOperation.getOutput();
        }
    }
}
