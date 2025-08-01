package com.StardewValley.model.things.products;

import com.StardewValley.model.things.Item;

import java.util.ArrayList;

public abstract class Recipe extends Item {
    private boolean isCooking;
    private ArrayList<Item> items;

    // Constructor for Recipe
    public Recipe(String name, int itemID, int value, int parentItemID, int amount, boolean isCooking, ArrayList<Item> items) {
        super(name, itemID, value, parentItemID, amount);
        this.isCooking = isCooking;
        this.items = items;
    }

    // Getters and Setters
    public boolean isCooking() {
        return isCooking;
    }

    public void setCooking(boolean isCooking) {
        this.isCooking = isCooking;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
