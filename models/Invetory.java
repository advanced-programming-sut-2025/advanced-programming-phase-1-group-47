package models;

import java.util.ArrayList;
import models.things.Item;

public class Invetory {
    //    private BackpackType backpackType;
    private ArrayList<eatble> foods = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Tool> tools = new ArrayList<>();
    private  int capacity = 20;
    public Item findItemFromName(String name){
        for(Item i : items)
            if (i.getName().equals(name))
                return i;
        return null;
    }
    public Invetory(int i) {
        this.capacity = 20;
    }
    public void removeItem(Item item) {
            items.removeIf(item1 -> item1.getItemID() == item.getItemID());
    }
    public void addItem(Item item) {
        for(Item item2 : items){
            if(item2.getItemID() == item.getItemID()) {
                item2.addAmount(item.getAmount());
                return;
            }
        }
        if(item.getItemID() != 0)
            items.add(item);
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

}