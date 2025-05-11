package models;

import java.util.ArrayList;
import models.enums.ShopType;
import models.things.Item;

public class Shop {
    private ShopType type;
    private ArrayList<Item> permaStock;
    private ArrayList<Item> springStock;
    private ArrayList<Item> summerStock;
    private ArrayList<Item> fallStock;

    public ArrayList<Item> getFallStock() {
        return fallStock;
    }

    public ArrayList<Item> getPermaStock() {
        return permaStock;
    }

    public ArrayList<Item> getSpringStock() {
        return springStock;
    }

    public ArrayList<Item> getSummerStock() {
        return summerStock;
    }

    public ShopType getType() {
        return type;
    }

    public void setFallStock(ArrayList<Item> fallStock) {
        this.fallStock = fallStock;
    }

    public void setPermaStock(ArrayList<Item> permaStock) {
        this.permaStock = permaStock;
    }

    public void setSpringStock(ArrayList<Item> springStock) {
        this.springStock = springStock;
    }

    public void setSummerStock(ArrayList<Item> summerStock) {
        this.summerStock = summerStock;
    }

    public void setType(ShopType type) {
        this.type = type;
    }
}
