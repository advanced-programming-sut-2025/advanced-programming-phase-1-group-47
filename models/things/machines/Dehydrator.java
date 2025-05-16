package models.things.machines;

import models.things.products.Product;

public class Dehydrator extends Machine {
    public Dehydrator(String name, int itemID, int value, int parentItemID, int amount, Product returnProduct) {
        super(name, itemID, value, parentItemID, amount,returnProduct);
    }
}
