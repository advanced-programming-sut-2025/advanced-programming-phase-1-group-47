package models;

import java.util.HashMap;
import models.things.Item;

public class Invetory {
    private HashMap<eatble, Integer> foods = new HashMap<>();
    private HashMap<Item, Integer> items = new HashMap<>();
    private HashMap<Tool, Integer> tools = new HashMap<>();
    private int capacity = 20;

    public Invetory(int capacity) {
        this.capacity = capacity;
    }

    public void removeItem(Item item) {
        if (items.containsKey(item)) {
            int current = items.get(item);
            if (current <= item.getAmount()) {
                items.remove(item);
            } else {
                items.put(item, current - item.getAmount());
            }
        }
    }

    public void addItem(Item item) {
        if (item.getItemID() == 0) return;

        items.put(item, items.getOrDefault(item, 0) + item.getAmount());
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFoods(HashMap<eatble, Integer> foods) {
        this.foods = foods;
    }

    public void setItems(HashMap<Item, Integer> items) {
        this.items = items;
    }

    public void setTools(HashMap<Tool, Integer> tools) {
        this.tools = tools;
    }

    public HashMap<eatble, Integer> getFoods() {
        return foods;
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public HashMap<Tool, Integer> getTools() {
        return tools;
    }

    public int getCapacity() {
        return capacity;
    }

    public void showInventory() {
        for (Item item : items.keySet()) {
            System.out.println(item.getName() + " " + items.get(item));
        }
    }
}
