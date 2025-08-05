package com.StardewValley.model.Shops;

import java.util.ArrayList;

import com.StardewValley.model.Building;
import com.StardewValley.model.FarmBuilding;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.Shop;
import com.StardewValley.model.enums.ShopType;
import com.StardewValley.model.things.Item;

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
        returnvalue.add(new Item("Wood",36 , 10 , 0 , 1000000, GameAssetManager.WOOD));
        returnvalue.add(new Item("Stone",2 ,20 , 0 , 1000000, GameAssetManager.STONE));
        returnvalue.add(new Building(FarmBuilding.BARN));
        returnvalue.add(new Building(FarmBuilding.BIG_BARN));
        returnvalue.add(new Building(FarmBuilding.COOP));
        returnvalue.add(new Building(FarmBuilding.BIG_COOP));
        returnvalue.add(new Building(FarmBuilding.DELUXE_BARN));
        returnvalue.add(new Building(FarmBuilding.DELUXE_BARN));
        returnvalue.add(new Building(FarmBuilding.SHIPPING_BIN));
        returnvalue.add(new Building(FarmBuilding.SHIPPING_BIN));
        returnvalue.add(new Building(FarmBuilding.WELL));
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
