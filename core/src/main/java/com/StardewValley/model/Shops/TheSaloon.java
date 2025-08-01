package com.StardewValley.model.Shops;

import java.util.ArrayList;

import com.StardewValley.model.Shop;
import com.StardewValley.model.enums.ShopType;
import com.StardewValley.model.things.Item;

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
        returnvalue.add(new Item("Beer",26 , 400 , 0 , 10000));
        returnvalue.add(new Item("Salad",35 , 220 , 0 , 10000));
        returnvalue.add(new Item("Bread",31 , 120 , 0 , 10000));
        returnvalue.add(new Item("Spaghetti",47 , 240 , 0 , 10000));
        returnvalue.add(new Item("Pizza",5 , 600 , 0 , 10000));
        returnvalue.add(new Item("Coffee",6 , 300 , 0 , 10000));
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
