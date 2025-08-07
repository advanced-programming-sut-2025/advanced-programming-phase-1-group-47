package com.StardewValley.model;

import com.StardewValley.model.things.Item;

public class ItemSelection {
    private final Item item;
    private final int quantity;

    public ItemSelection(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
