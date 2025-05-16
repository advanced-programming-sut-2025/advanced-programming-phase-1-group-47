package models.things.machines;

import models.things.products.Product;

public class Kiln extends Machine {
    public Kiln(String name, int itemID, int value, int parentItemID, int amount,Product returnProduct) {
        super(name, itemID, value, parentItemID, amount,returnProduct);
    }
}
