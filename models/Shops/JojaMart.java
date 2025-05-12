package models.Shops;

import java.util.ArrayList;
import models.Shop;
import models.enums.ShopType;
import models.things.Item;

public class JojaMart {


    public Shop jojaBuilder() {
        ArrayList<Item> permaStock = permaStockBuilder();
        ArrayList<Item> springStock = springStockBuilder();
        ArrayList<Item> summerStock = summerStockBuilder();
        ArrayList<Item> fallStock = fallStockBuilder();
        ArrayList<Item> winterStock = winterStockBuilder();

        Shop blacksmith = new Shop(ShopType.JojaMart, permaStock, springStock, 
        summerStock, fallStock, 9, 23, winterStock);
        return blacksmith;
    }
    private ArrayList<Item> permaStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Joja Cola",24 , 75 , 0 , 1000000));
        returnvalue.add(new Item("Ancient Seed",442 ,500 , 401 , 1));
        returnvalue.add(new Item("Grass Starter",25 , 125 , 0 , 1000000));
        returnvalue.add(new Item("Suger",27 , 125 , 0 , 1000000));
        returnvalue.add(new Item("White Flour", 28 , 125 , 0 , 1000000));
        returnvalue.add(new Item("Rice",48 , 250 , 0 , 1000000));   
        return returnvalue;
    }

    private ArrayList<Item> springStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Parsnip Seeds", 409 , 25 , 401 , 5));
        returnvalue.add(new Item("Bean Starter", 407 , 75 , 401 , 5));
        returnvalue.add(new Item("Cauliflower Seeds", 404 , 100 , 401 , 5));
        returnvalue.add(new Item("Potato Seeds", 410 , 62 , 401 , 5));
        returnvalue.add(new Item("Strawberry Seeds", 412 , 100 , 401 , 5));
        returnvalue.add(new Item("Tulip Bulb", 413 , 25 , 401 , 5));
        returnvalue.add(new Item("Kale Seeds", 408 , 87 , 401 , 5));
        returnvalue.add(new Item("Coffee Beans", 405 , 200 , 401 , 1));
        returnvalue.add(new Item("Carrot Seeds", 403 , 5 , 401 , 10));
        returnvalue.add(new Item("Rhubarb Seeds", 411 , 100 , 401 , 5));
        returnvalue.add(new Item("Jazz Seeds", 402 , 37 , 401 , 5));
        return returnvalue;
    }
    private ArrayList<Item> summerStockBuilder() {
        ArrayList<Item> returnvalue = new ArrayList<>();
        returnvalue.add(new Item("Parsnip Seeds", 409 , 25 , 401 , 5));
        returnvalue.add(new Item("Bean Starter", 407 , 75 , 401 , 5));
        returnvalue.add(new Item("Cauliflower Seeds", 404 , 100 , 401 , 5));
        returnvalue.add(new Item("Potato Seeds", 410 , 62 , 401 , 5));
        returnvalue.add(new Item("Strawberry Seeds", 412 , 100 , 401 , 5));
        returnvalue.add(new Item("Tulip Bulb", 413 , 25 , 401 , 5));
        returnvalue.add(new Item("Kale Seeds", 408 , 87 , 401 , 5));
        returnvalue.add(new Item("Coffee Beans", 405 , 200 , 401 , 1));
        returnvalue.add(new Item("Carrot Seeds", 403 , 5 , 401 , 10));
        returnvalue.add(new Item("Rhubarb Seeds", 411 , 100 , 401 , 5));
        returnvalue.add(new Item("Jazz Seeds", 402 , 37 , 401 , 5));
        return returnvalue;
    }
    private ArrayList<Item> fallStockBuilder() {
        return null;
    }
    private ArrayList<Item> winterStockBuilder() {
        return null;
    }
}
