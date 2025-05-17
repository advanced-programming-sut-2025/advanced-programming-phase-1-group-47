package models.things.machines;

import java.util.ArrayList;
import models.enums.ProductQuality;
import models.things.Item;
import models.things.products.Product;

public class Dehydrator extends Machine{
    private static final ArrayList<Operation> dehydratorOperations = new ArrayList<>();
    static {
        dehydratorOperations.add(new Operation(1,24, new Item("Common Mushroom", 357, 2, 0, 5 ),
       new Product("Dried Mushrooms", 557, 325, 0, 1, true, 50, 20, ProductQuality.NORMAL, false, false), true));
        dehydratorOperations.add(new Operation(2,24, new Item("Grape", 337, 2, 0, 5),
         new Item("Raisins", 13, 600, 0, 1), true));
        dehydratorOperations.add(new Operation(3,24, new Item("FRUIT", 300, 0, 0,10),
        new Item("null", 0, 0, 0, 1), true));
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
