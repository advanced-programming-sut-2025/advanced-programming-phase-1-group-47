package models;

import models.things.Item;

import java.util.ArrayList;

public class Refrigerator {
    private ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        for (Item existing : items) {
            if (existing.getItemID() == item.getItemID()) {
                existing.setAmount(existing.getAmount() + item.getAmount());
                return;
            }
        }
        items.add(item);
    }


    public void removeItem(Item item) {
        items.removeIf(item1 -> item1.getItemID() == item.getItemID());
    }

}
