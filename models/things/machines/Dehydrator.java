package models.things.machines;

import java.util.ArrayList;
import models.AllTheItemsInTheGame;
import models.things.Item;

public class Dehydrator extends Machine{
    private static final ArrayList<Operation> dehydratorOperations = new ArrayList<>();
    static {
        dehydratorOperations.add(new Operation(24, new Item(AllTheItemsInTheGame.getItemById(357) , 5),
        new Item(AllTheItemsInTheGame.getItemById(557) , 1), true));
        dehydratorOperations.add(new Operation(24, new Item(AllTheItemsInTheGame.getItemById(337) , 5),
        new Item(AllTheItemsInTheGame.getItemById(13) , 1), true));
        dehydratorOperations.add(new Operation(24, new Item(AllTheItemsInTheGame.getItemById(300) , 10),
        new Item(AllTheItemsInTheGame.getItemById(300) , 1), true));
    }

    public Dehydrator() {
        super("Charcoal Kiln", 12, 20, 0, 1,dehydratorOperations);
    }
}
