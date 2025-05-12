package models.Shops;

import java.util.ArrayList;

import models.Shop;
import models.enums.ShopType;
import models.things.Item;

public class Carpenter {
    public Shop carpenterBuilder() {
        ArrayList<Item> permaStock = permaStockBuilder();
        ArrayList<Item> springStock = springStockBuilder();
        ArrayList<Item> summerStock = summerStockBuilder();
        ArrayList<Item> fallStock = fallStockBuilder();
        ArrayList<Item> winterStock = winterStockBuilder();

        Shop carpenter = new Shop(ShopType.Carpenters, permaStock, springStock, 
        summerStock, fallStock, 9, 20, winterStock);
        return carpenter;
    }
    private ArrayList<Item> permaStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Wood",36 , 10 , 0 , 1000000));
        returnvalue.add(new Item("Stone",2 ,20 , 0 , 1000000));
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
