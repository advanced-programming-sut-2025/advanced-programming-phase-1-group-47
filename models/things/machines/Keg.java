package models.things.machines;

import java.util.ArrayList;
import models.AllTheItemsInTheGame;
import models.things.Item;

public class Keg extends Machine {
    private static final ArrayList<Operation> kegOperations = new ArrayList<>(); 
    static {
        kegOperations.add(new Operation(1,24, new Item(AllTheItemsInTheGame.getItemById(328) , 1),
        new Item(AllTheItemsInTheGame.getItemById(26) , 1), false));
        kegOperations.add(new Operation(2,10, new Item(AllTheItemsInTheGame.getItemById(48) , 1),
        new Item(AllTheItemsInTheGame.getItemById(31) , 1), false));
        kegOperations.add(new Operation(3,2, new Item(AllTheItemsInTheGame.getItemById(305) , 5),
        new Item(AllTheItemsInTheGame.getItemById(6) , 1), false));
        kegOperations.add(new Operation(4,96, new Item(AllTheItemsInTheGame.getItemById(299) , 1),
        new Item(AllTheItemsInTheGame.getItemById(0) , 1), false));
        kegOperations.add(new Operation(5,10, new Item(AllTheItemsInTheGame.getItemById(22) , 1),
        new Item(AllTheItemsInTheGame.getItemById(23) , 1), false));
        kegOperations.add(new Operation(6,72, new Item(AllTheItemsInTheGame.getItemById(317) , 1),
        new Item(AllTheItemsInTheGame.getItemById(32) , 1), false));
        kegOperations.add(new Operation(7,168, new Item(AllTheItemsInTheGame.getItemById(300) , 1),
        new Item(AllTheItemsInTheGame.getItemById(0) , 1), false));
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
