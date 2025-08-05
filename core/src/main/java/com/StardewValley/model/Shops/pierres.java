package com.StardewValley.model.Shops;

import java.util.ArrayList;
import com.StardewValley.model.Shop;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.enums.ShopType;
import com.StardewValley.model.things.Item;

public class pierres {
    public Shop pierresBuilder() {
        ArrayList<Item> permaStock = permaStockBuilder();
        ArrayList<Item> springStock = springStockBuilder();
        ArrayList<Item> summerStock = summerStockBuilder();
        ArrayList<Item> fallStock = fallStockBuilder();
        ArrayList<Item> winterStock = winterStockBuilder();

        return new Shop(ShopType.Pierres, permaStock, springStock,
                summerStock, fallStock, 9, 23, winterStock);
    }

    private ArrayList<Item> permaStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Ancient Seed", 442, 500, 401, 1, GameAssetManager.ANCIENT_SEED));
        returnvalue.add(new Item("Grass Starter", 25, 125, 0, 1000000, GameAssetManager.GRASS_STARTER));
        returnvalue.add(new Item("Sugar", 27, 125, 0, 1000000, GameAssetManager.SUGAR));
        returnvalue.add(new Item("White Flour", 28, 125, 0, 1000000, GameAssetManager.WHITE_FLOUR));
        returnvalue.add(new Item("Rice", 48, 250, 0, 1000000, GameAssetManager.RICE));
        return returnvalue;
    }

    private ArrayList<Item> springStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Parsnip Seeds", 409, 25, 401, 5, GameAssetManager.PARSNIP_SEEDS));
        returnvalue.add(new Item("Bean Starter", 407, 75, 401, 5, GameAssetManager.BEAN_STARTER));
        returnvalue.add(new Item("Cauliflower Seeds", 404, 100, 401, 5, GameAssetManager.CAULIFLOWER_SEEDS));
        returnvalue.add(new Item("Potato Seeds", 410, 62, 401, 5, GameAssetManager.POTATO_SEEDS));
        returnvalue.add(new Item("Strawberry Seeds", 412, 100, 401, 5, GameAssetManager.STRAWBERRY_SEEDS));
        returnvalue.add(new Item("Tulip Bulb", 413, 25, 401, 5, GameAssetManager.TULIP_BULB));
        returnvalue.add(new Item("Kale Seeds", 408, 87, 401, 5, GameAssetManager.KALE_SEEDS));
        returnvalue.add(new Item("Coffee Beans", 405, 200, 401, 1, GameAssetManager.COFFEE_BEANS));
        returnvalue.add(new Item("Carrot Seeds", 403, 5, 401, 10, GameAssetManager.CARROT_SEEDS));
        returnvalue.add(new Item("Rhubarb Seeds", 411, 100, 401, 5, GameAssetManager.RHUBARB_SEEDS));
        returnvalue.add(new Item("Jazz Seeds", 402, 37, 401, 5, GameAssetManager.JAZZ_SEEDS));
        return returnvalue;
    }

    private ArrayList<Item> summerStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Tomato Seeds", 427, 62, 401, 5, GameAssetManager.TOMATO_SEEDS));
        returnvalue.add(new Item("Pepper Seeds", 418, 50, 401, 5, GameAssetManager.PEPPER_SEEDS));
        returnvalue.add(new Item("Wheat Seeds", 428, 12, 401, 10, GameAssetManager.WHEAT_SEEDS));
        returnvalue.add(new Item("Summer Squash Seeds", 425, 10, 401, 10, GameAssetManager.SUMMER_SQUASH_SEEDS));
        returnvalue.add(new Item("Radish Seeds", 421, 50, 401, 5, GameAssetManager.RADISH_SEEDS));
        returnvalue.add(new Item("Melon Seeds", 419, 100, 401, 5, GameAssetManager.MELON_SEEDS));
        returnvalue.add(new Item("Hops Starter", 417, 75, 401, 5, GameAssetManager.HOPS_STARTER));
        returnvalue.add(new Item("Poppy Seeds", 420, 125, 401, 5, GameAssetManager.POPPY_SEEDS));
        returnvalue.add(new Item("Spangle Seeds", 424, 62, 401, 5, GameAssetManager.SPANGLE_SEEDS));
        returnvalue.add(new Item("StarFruit Seeds", 423, 400, 401, 5, GameAssetManager.STARFRUIT_SEEDS));
        returnvalue.add(new Item("Coffee Beans", 405, 200, 401, 1, GameAssetManager.COFFEE_BEANS));
        returnvalue.add(new Item("SunFlower Seeds", 426, 125, 401, 5, GameAssetManager.SUNFLOWER_SEEDS));
        return returnvalue;
    }

    private ArrayList<Item> fallStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Corn Seeds", 416, 187, 401, 5, GameAssetManager.CORN_SEEDS));
        returnvalue.add(new Item("Eggplant Seeds", 435, 25, 401, 5, GameAssetManager.EGGPLANT_SEEDS));
        returnvalue.add(new Item("Pumpkin Seeds", 438, 125, 401, 5, GameAssetManager.PUMPKIN_SEEDS));
        returnvalue.add(new Item("Broccoli Seeds", 433, 15, 401, 5, GameAssetManager.BROCCOLI_SEEDS));
        returnvalue.add(new Item("Amaranth Seeds", 429, 87, 401, 5, GameAssetManager.AMARANTH_SEEDS));
        returnvalue.add(new Item("Grape Starter", 437, 75, 401, 5, GameAssetManager.GRAPE_STARTER));
        returnvalue.add(new Item("Beet Seeds", 431, 20, 401, 5, GameAssetManager.BEET_SEEDS));
        returnvalue.add(new Item("Yam Seeds", 439, 75, 401, 5, GameAssetManager.YAM_SEEDS));
        returnvalue.add(new Item("Bok Choy Seeds", 432, 62, 401, 5, GameAssetManager.BOK_CHOY_SEEDS));
        returnvalue.add(new Item("Cranberry Seeds", 434, 300, 401, 5, GameAssetManager.CRANBERRY_SEEDS));
        returnvalue.add(new Item("SunFlower Seeds", 426, 250, 401, 5, GameAssetManager.SUNFLOWER_SEEDS));
        returnvalue.add(new Item("Fairy Seeds", 436, 250, 401, 5, GameAssetManager.FAIRY_SEEDS));
        returnvalue.add(new Item("Rare Seed", 440, 1000, 401, 1, GameAssetManager.RARE_SEED));
        returnvalue.add(new Item("Wheat Seeds", 428, 12, 401, 10, GameAssetManager.WHEAT_SEEDS));
        return returnvalue;
    }

    private ArrayList<Item> winterStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("PowderMelon Seeds", 441, 20, 401, 10, GameAssetManager.POWDERMELON_SEEDS));
        return returnvalue;
    }
}