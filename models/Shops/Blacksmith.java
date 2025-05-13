package models.Shops;

import java.util.ArrayList;
import models.Shop;
import models.enums.ShopType;
import models.things.Item;

public class Blacksmith {
    public Shop blacksmithBulider() {
        ArrayList<Item> permaStock = permaStockBuilder();
        ArrayList<Item> springStock = springStockBuilder();
        ArrayList<Item> summerStock = summerStockBuilder();
        ArrayList<Item> fallStock = fallStockBuilder();
        ArrayList<Item> winterStock = winterStockBuilder();
        return new Shop(ShopType.BlackSmith, permaStock, springStock,
                summerStock, fallStock, 9, 16, winterStock);
    }

    private ArrayList<Item> permaStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Copper Ore", 21, 75, 0, 1000000));
        returnvalue.add(new Item("Iron Ore", 1, 150, 0, 1000000));
        returnvalue.add(new Item("Gold Ore", 21, 400, 0, 1000000));
        returnvalue.add(new Item("Coal", 21, 150, 0, 1000000));
        return returnvalue;
    }

    private ArrayList<Item> springStockBuilder() {
        return new ArrayList<>();
    }

    private ArrayList<Item> summerStockBuilder() {
        return new ArrayList<>();
    }

    private ArrayList<Item> fallStockBuilder() {
        return new ArrayList<>();
    }

    private ArrayList<Item> winterStockBuilder() {
        return new ArrayList<>();
    }
}
