package models;

import java.util.ArrayList;
import models.enums.Season;
import models.enums.ShopType;
import models.things.Item;

public class Shop {
    private ShopType type;
    private  int startingHour;
    private  int stoppingHour;
    private ArrayList<Item> permaStock;
    private ArrayList<Item> springStock;
    private ArrayList<Item> summerStock;
    private ArrayList<Item> fallStock;
    private ArrayList<Item> winterStock;

    public Shop(ShopType type , ArrayList<Item> permastock , ArrayList<Item> springStock , ArrayList<Item> summerStock 
    , ArrayList<Item> fallStock , int startingHour , int stoppingHour , ArrayList<Item> winterStock) {
        this.type = type;
        this.startingHour = startingHour;
        this.stoppingHour = stoppingHour;
        this.permaStock = permastock;
        this.springStock = springStock;
        this.summerStock = summerStock;
        this.fallStock = fallStock;
        this.winterStock = winterStock;
    }

    public ArrayList<Item> getStock() {
    ArrayList<Item> combined = new ArrayList<>(permaStock);
        if(Time.getSeason() == Season.SPRING)
            combined.addAll(springStock);
        if(Time.getSeason() == Season.SUMMER)
            combined.addAll(summerStock);
        if(Time.getSeason() == Season.FALL)
            combined.addAll(fallStock);
        if(Time.getSeason() == Season.WINTER)
            combined.addAll(winterStock);
        return combined;
    }

}
