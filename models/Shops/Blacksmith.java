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
        returnvalue.add(new Item("Copper Ore", 392, 75, 0, 1000000));
        returnvalue.add(new Item("Iron Ore", 393, 150, 0, 1000000));
        returnvalue.add(new Item("Gold Ore", 394, 400, 0, 1000000));
        returnvalue.add(new Item("Coal", 396, 150, 0, 1000000));
        returnvalue.add(new Item("Copper Pickaxe" ,1000,2000,1000,1));
        returnvalue.add(new Item("Steel Pickaxe" ,1001,5000,1000,1));
        returnvalue.add(new Item("Gold Pickaxe" ,1002,10000,1000,1));
        returnvalue.add(new Item("Iridium Pickaxe" ,1003,25000,1000,1));
        returnvalue.add(new Item("Copper Axe" ,1004,2000,1000,1));
        returnvalue.add(new Item("Steel Axe" ,1005,5000,1000,1));
        returnvalue.add(new Item("Gold Axe" ,1006,10000,1000,1));
        returnvalue.add(new Item("Iridium Axe" ,1007,25000,1000,1));
        returnvalue.add(new Item("Copper WateringCan" ,1008,2000,1000,1));
        returnvalue.add(new Item("Steel WateringCan" ,1009,5000,1000,1));
        returnvalue.add(new Item("Gold WateringCan" ,1010,10000,1000,1));
        returnvalue.add(new Item("Iridium WateringCan" ,1011,25000,1000,1));
        returnvalue.add(new Item("Copper Hoe" ,1012,2000,1000,1));
        returnvalue.add(new Item("Steel Hoe" ,1013,5000,1000,1));
        returnvalue.add(new Item("Gold Hoe" ,1014,10000,1000,1));
        returnvalue.add(new Item("Iridium Hoe" ,1015,25000,1000,1));
        returnvalue.add(new Item("Copper Trash Can" ,1016,2000,1000,1));
        returnvalue.add(new Item("Steel Trash Can" ,1017,5000,1000,1));
        returnvalue.add(new Item("Gold Trash Can" ,1018,10000,1000,1));
        returnvalue.add(new Item("Iridium Trash Can" ,1019,25000,1000,1));
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
