package com.StardewValley.model.things.machines;

import java.util.ArrayList;

import com.StardewValley.model.things.Item;

public abstract class Refrigerator extends Item {
    private int capacity;
    private ArrayList<Item> items;

    public Refrigerator(String name, int itemID, int value, int parentItemID, int amount, int capacity) {
        super(name, itemID, value, parentItemID, amount);
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
