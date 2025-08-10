package com.StardewValley.model;

import java.util.ArrayList;

import com.StardewValley.model.things.Item;

public class Invetory {
    //    private BackpackType backpackType;
    private ArrayList<eatble> foods = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Tool> tools = new ArrayList<>();
    private  int capacity = 100;
    private ArrayList<Item> bufferInvetory = new ArrayList<>();
    public Item findItemFromName(String name){
        for(Item i : items)
            if (i.getName().equals(name))
                return i;
        return null;
    }
    public boolean isFull(){
        return (items.size() >= capacity);
    }
    public Invetory(int i) {
        this.capacity = 100;
    }
    public void removeItem(Item item) {
        items.removeIf(item1 -> item1.getItemID() == item.getItemID());
    }
    public boolean addItem(Item item) {
        // First try to stack with existing items
        for (Item existing : items) {
            if (existing.getName().equals(item.getName())) {
                existing.setAmount(existing.getAmount() + item.getAmount());
                return true;
            }
        }

        if (items.size() < capacity) {
            items.add(item);
            return true;
        }

        return false;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFoods(ArrayList<eatble> foods) {
        this.foods = foods;
    }

    public void setItems(Item item) {
        this.items.add(item);
    }

    public void setTools(ArrayList<Tool> tools) {
        this.tools = tools;
    }

    public ArrayList<eatble> getFoods() {
        return foods;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Tool> getTools() {
        return tools;
    }

    public int getCapacity() {
        return capacity;
    }

    public void showInventory() {
        for(Item item : items) {
            System.out.println(item.getName() + " " + item.getAmount());
        }
    }

    public void reduceAmount(Item item, int amount) {
        for (Item invItem : items) {
            if (invItem.getItemID() == item.getItemID()) {
                invItem.setAmount(invItem.getAmount() - amount);

                // If amount reaches zero or below, remove the item from inventory
                if (invItem.getAmount() <= 0) {
                    items.remove(invItem);
                }
                return;
            }
        }
    }

    public int remainingSpace() {
        int amount = 0;
        for(Item item : items) {
            amount += item.getAmount();
        }

        return capacity - amount;
    }

    public void setBufferInvetory(ArrayList<Item> bufferInvetory) {
        this.bufferInvetory = bufferInvetory;
    }

    public ArrayList<Item> getBufferInvetory() {
        return bufferInvetory;
    }

}