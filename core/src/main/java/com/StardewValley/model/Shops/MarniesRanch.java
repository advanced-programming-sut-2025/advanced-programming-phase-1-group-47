package com.StardewValley.model.Shops;

import java.util.ArrayList;

import com.StardewValley.model.Animal;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.Shop;
import com.StardewValley.model.enums.AnimalType;
import com.StardewValley.model.enums.ShopType;
import com.StardewValley.model.things.Item;

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
        returnvalue.add(new Item("Hay",30 , 50 , 0 , 1000000, GameAssetManager.HAY));
        returnvalue.add(new Item("Milk Pail",55 ,1000 , 0 , 1, GameAssetManager.MILKPILL));

        returnvalue.add(new Animal(AnimalType.DINOSAUR));
        returnvalue.add(new Animal(AnimalType.HEN));
        returnvalue.add(new Animal(AnimalType.DUCK));
        returnvalue.add(new Animal(AnimalType.PIG));
        returnvalue.add(new Animal(AnimalType.GOAT));
        returnvalue.add(new Animal(AnimalType.COW));
        returnvalue.add(new Animal(AnimalType.RABBIT));
        returnvalue.add(new Animal(AnimalType.SHEEP));
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
