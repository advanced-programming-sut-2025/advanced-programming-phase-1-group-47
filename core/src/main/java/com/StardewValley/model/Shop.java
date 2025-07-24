package com.StardewValley.model;

import java.util.ArrayList;

import com.StardewValley.model.enums.Season;
import com.StardewValley.model.enums.ShopType;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Shop {
    private ShopType type;
    private int startingHour;
    private int stoppingHour;
    private ArrayList<Item> permaStock;
    private ArrayList<Item> springStock;
    private ArrayList<Item> summerStock;
    private ArrayList<Item> fallStock;
    private ArrayList<Item> winterStock;
    private boolean isPlayerInside;
    public void render(SpriteBatch batch) {
        Texture tex = isPlayerInside ? type.getInTexture() : type.getOutTexture();
        batch.draw(tex, type.getPosition().x, type.getPosition().y, type.getWidth(),type.getHeight());
    }
    // 🔹 کانستراکتور اصلی با همه پارامترها
    public Shop(ShopType type, ArrayList<Item> permastock, ArrayList<Item> springStock,
                ArrayList<Item> summerStock, ArrayList<Item> fallStock,
                int startingHour, int stoppingHour, ArrayList<Item> winterStock) {
        this.type = type;
        this.permaStock = permastock;
        this.springStock = springStock;
        this.summerStock = summerStock;
        this.fallStock = fallStock;
        this.winterStock = winterStock;
        this.startingHour = startingHour;
        this.stoppingHour = stoppingHour;
    }
    public void update(Vector2 playerPos) {
        float dist = playerPos.dst(type.getPosition());
        isPlayerInside = dist < 100;
    }

    public Shop() {
        this.permaStock = new ArrayList<>();
        this.springStock = new ArrayList<>();
        this.summerStock = new ArrayList<>();
        this.fallStock = new ArrayList<>();
        this.winterStock = new ArrayList<>();
        this.startingHour = 0;
        this.stoppingHour = 24;
        this.type = null;
    }
    public ArrayList<Item> getStock() {
        ArrayList<Item> combined = new ArrayList<>(permaStock);
        Season season = App.currentGame.time.getSeason();
        switch (season) {
            case SPRING -> combined.addAll(springStock);
            case SUMMER -> combined.addAll(summerStock);
            case FALL -> combined.addAll(fallStock);
            case WINTER -> combined.addAll(winterStock);
        }
        return combined;
    }

    // 🔹 متدهای افزودن محصول به فصل‌های مختلف
    public void addSpringStock(Item springStock) {
        this.springStock.add(springStock);
    }

    public void addPermaStock(Item permaStock) {
        this.permaStock.add(permaStock);
    }

    public void addFallStock(Item fallStock) {
        this.fallStock.add(fallStock);
    }

    public void addWinterStock(Item winterStock) {
        this.winterStock.add(winterStock);
    }

    public void addSummerStock(Item summerStock) {
        this.summerStock.add(summerStock);
    }

    // 🔹 getterها
    public ArrayList<Item> getSummerStock() {
        return summerStock;
    }

    public ArrayList<Item> getSpringStock() {
        return springStock;
    }

    public ArrayList<Item> getPermaStock() {
        return permaStock;
    }

    public ArrayList<Item> getFallStock() {
        return fallStock;
    }

    public ArrayList<Item> getWinterStock() {
        return winterStock;
    }

    public ShopType getType() {
        return type;
    }

    public void setType(ShopType type) {
        this.type = type;
    }

    public int getStartingHour() {
        return startingHour;
    }

    public int getStoppingHour() {
        return stoppingHour;
    }

    public void setStartingHour(int startingHour) {
        this.startingHour = startingHour;
    }

    public void setStoppingHour(int stoppingHour) {
        this.stoppingHour = stoppingHour;
    }
}
