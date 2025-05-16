package models.things.machines;

import java.util.ArrayList;
import models.AllTheItemsInTheGame;
import models.things.Item;

public class BeeHouse extends Machine {
    
    private static final ArrayList<Operation> beehouseOperations = new ArrayList<>();
    static {
        beehouseOperations.add(new Operation(1,96, new Item(AllTheItemsInTheGame.getItemById(0) , 1),
          new Item(AllTheItemsInTheGame.getItemById(22) , 1), false));
    }

    public BeeHouse() {
        super("Bee House", 43, 20, 0, 1,beehouseOperations);
    }
}
