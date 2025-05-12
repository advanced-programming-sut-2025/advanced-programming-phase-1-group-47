package models.Shops;

import java.util.ArrayList;
import models.Shop;
import models.enums.ShopType;
import models.things.Item;

public class MarniesRanch {
    public Shop MarnieRanchBuilder() {
        ArrayList<Item> permaStock = permaStockBuilder();
        ArrayList<Item> springStock = springStockBuilder();
        ArrayList<Item> summerStock = summerStockBuilder();
        ArrayList<Item> fallStock = fallStockBuilder();
        ArrayList<Item> winterStock = winterStockBuilder();

        Shop marnieRanch = new Shop(ShopType.Marnies, permaStock, springStock,
        summerStock, fallStock, 9, 16, winterStock);
        return marnieRanch;
    }
    private ArrayList<Item> permaStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Hay",30 , 50 , 0 , 1000000));
        returnvalue.add(new Item("Milk Pail",55 ,1000 , 0 , 1));
        returnvalue.add(new Item("Shears",58 , 1000 , 0 , 1));
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
