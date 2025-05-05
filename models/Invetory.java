package models;

import java.util.ArrayList;
import models.things.Item;
import models.things.tools.Backpack;

public class Invetory {
    private Backpack backpack;
    private ArrayList<eatble> foods;
    private ArrayList<Item> items;
    private ArrayList<Tool> tools;

    public void removeItem(Item item) {
        items.remove(item);
    }
    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
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

    public Backpack getBackpack() {
        return backpack;
    }
}
