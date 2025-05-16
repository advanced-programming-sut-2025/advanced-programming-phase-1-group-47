package models.things.machines;

import models.Point;
import models.things.Item;
import models.things.products.Product;

public class Machine extends Item {
    Point point;
    Product returnProduct;
    int readyTime;
    int currentTime;

    public Machine(String name, int itemID, int value, int parentItemID, int amount , Product returnProduct) {
        super(name, itemID, value, parentItemID, amount);
        this.returnProduct = returnProduct;
    }
}