package models;

import java.util.ArrayList;
import java.util.logging.SocketHandler;

import models.things.Item;

public class Invetory {
//    private BackpackType backpackType;
    private ArrayList<eatble> foods = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Tool> tools = new ArrayList<>();
    private  int capacity = 20;

    public Invetory(int i) {
        this.capacity = 20;
    }
    public void removeItem(Item item) {
        items.remove(item);
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

    public void setItems(ArrayList<Item> items) {
        this.items = items;
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


}
