package models.things.machines;

import models.things.products.Product;

public class BeeHouse extends Machine {
    public BeeHouse(String name, int itemID, int value, int parentItemID, int amount, Product returnProduct) {
        super(name, itemID, value, parentItemID,amount , returnProduct);
    }
}
