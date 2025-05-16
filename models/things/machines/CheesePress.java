package models.things.machines;

import java.util.ArrayList;
import models.AllTheItemsInTheGame;
import models.things.Item;

public class CheesePress extends Machine {
    
    private static final ArrayList<Operation> cheesePressOperations = new ArrayList<>();
    static {
        cheesePressOperations.add(new Operation(1,3, new Item(AllTheItemsInTheGame.getItemById(907) , 1),
          new Item(AllTheItemsInTheGame.getItemById(15) , 1), false));
        cheesePressOperations.add(new Operation(2,3, new Item(AllTheItemsInTheGame.getItemById(908) , 1),
          new Item(AllTheItemsInTheGame.getItemById(16) , 1), false));
        cheesePressOperations.add(new Operation(3,3, new Item(AllTheItemsInTheGame.getItemById(909) , 1),
          new Item(AllTheItemsInTheGame.getItemById(17) , 1), false));
        cheesePressOperations.add(new Operation(4,3, new Item(AllTheItemsInTheGame.getItemById(910) , 1),
          new Item(AllTheItemsInTheGame.getItemById(18) , 1), false));
    }

    public CheesePress() {
        super("Cheese Press", 43, 20, 0, 1,cheesePressOperations);
    }
}
