package models.things.machines.craftingMachines;

import models.things.machines.Machine;
import models.things.products.Product;

public class Converter extends Machine {
    public Converter(String name, int itemID, int value, int parentItemID, int amount, Product returnProduct) {
        super(name, itemID, value, parentItemID,amount , returnProduct);
    }
}
