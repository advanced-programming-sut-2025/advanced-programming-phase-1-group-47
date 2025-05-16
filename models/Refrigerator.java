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
}
