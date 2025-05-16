package models.things.machines;

import models.things.Item;
import models.things.products.Product;

public class Keg extends Machine {
    public Keg(String name, int itemID, int value, int parentItemID, int amount , Item returnProduct) {
        super(name, itemID, value, parentItemID, amount,returnProduct);
    }
}
