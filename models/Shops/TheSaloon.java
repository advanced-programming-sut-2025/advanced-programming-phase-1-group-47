package models.Shops;

import java.util.ArrayList;

import models.Shop;
import models.enums.ShopType;
import models.things.Item;

public class TheSaloon {
  public Shop theSaloonBuilder() {
        ArrayList<Item> permaStock = permaStockBuilder();
        ArrayList<Item> springStock = springStockBuilder();
        ArrayList<Item> summerStock = summerStockBuilder();
        ArrayList<Item> fallStock = fallStockBuilder();
        ArrayList<Item> winterStock = winterStockBuilder();

        Shop saloon = new Shop(ShopType.TheSaloon, permaStock, springStock, 
        summerStock, fallStock, 9, 16, winterStock);
        return saloon;
    }
    private ArrayList<Item> permaStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();//TODO
        returnvalue.add(new Item("Beer",29 , 250 , 0 , 10000));
        returnvalue.add(new Item("Trout Soup",29 , 250 , 0 , 10000));
        returnvalue.add(new Item("Trout Soup",29 , 250 , 0 , 10000));
        returnvalue.add(new Item("Trout Soup",29 , 250 , 0 , 10000));
        returnvalue.add(new Item("Trout Soup",29 , 250 , 0 , 10000));
        returnvalue.add(new Item("Trout Soup",29 , 250 , 0 , 10000));
        return returnvalue;
    }

    private ArrayList<Item> springStockBuilder() {
        return null;
    }
    private ArrayList<Item> summerStockBuilder() {
        return null;
    }
    private ArrayList<Item> fallStockBuilder() {
        return null;
    }
    private ArrayList<Item> winterStockBuilder() {
        return null;
    }
}
