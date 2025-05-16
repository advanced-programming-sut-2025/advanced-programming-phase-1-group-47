package models.things.machines;

import java.util.ArrayList;
import models.things.Item;
import models.things.products.Product;

public abstract class Refrigerator extends Machine {
    private int capacity;
    private ArrayList<Item> items;

    public Refrigerator(String name, int itemID, int value, int parentItemID, int amount, int capacity , Product returnProduct) {
        super(name, itemID, value, parentItemID, amount,returnProduct);
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    // Getters and Setters
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public boolean addItem(Item item) {
        if (items.size() < capacity) {
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }
}
