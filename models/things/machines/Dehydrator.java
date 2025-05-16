package models.things.machines;

import java.util.ArrayList;
import models.AllTheItemsInTheGame;
import models.things.Item;

public class Dehydrator extends Machine{
    private static final ArrayList<Operation> dehydratorOperations = new ArrayList<>();
    static {
        dehydratorOperations.add(new Operation(1,24, new Item(AllTheItemsInTheGame.getItemById(357) , 5),
        new Item(AllTheItemsInTheGame.getItemById(557) , 1), true));
        dehydratorOperations.add(new Operation(2,24, new Item(AllTheItemsInTheGame.getItemById(337) , 5),
        new Item(AllTheItemsInTheGame.getItemById(13) , 1), true));
        dehydratorOperations.add(new Operation(3,24, new Item(AllTheItemsInTheGame.getItemById(300) , 10),
        new Item(AllTheItemsInTheGame.getItemById(0) , 1), true));
    }

    public Dehydrator() {
        super("Dehydrator", 14, 20, 0, 1,dehydratorOperations);
    }
    @Override
    public Item getOutput() {
        if (currentOperation.getId() == 3)
            return currentOperation.getInput().getDriedFruit();

        else
            return currentOperation.getOutput();
    }
}
