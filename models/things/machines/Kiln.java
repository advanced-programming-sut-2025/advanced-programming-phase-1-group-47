package models.things.machines;

import java.util.ArrayList;
import models.AllTheItemsInTheGame;
import models.things.Item;

public class Kiln extends Machine{
    
    private static final ArrayList<Operation> kilnOperations = new ArrayList<>();
    static {
        kilnOperations.add(new Operation(1, new Item(AllTheItemsInTheGame.getItemById(36) , 10),
          new Item(AllTheItemsInTheGame.getItemById(396) , 1), false));
    }

    public Kiln() {
        super("Charcoal Kiln", 12, 20, 0, 1,kilnOperations);
    }
}
