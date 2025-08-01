package com.StardewValley.model.things;

import com.StardewValley.model.AllTheItemsInTheGame;
import com.StardewValley.model.Point;
import com.StardewValley.model.Result;
import com.StardewValley.model.Tile;
import com.StardewValley.model.enums.ProductQuality;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.things.products.Product;
import com.StardewValley.model.things.tools.Type;
import com.badlogic.gdx.graphics.Texture;

public class Item {
    private String name;
    private int ItemID;
    private int ParentItemID; //for wine/pickled vegtables/fruit jam /fish /plant /i hate NPCs
    private int value;
    private int amount = 0;
    protected Texture image;
    public String useTool(Tile tile){
        return "";
    }
    public Item(String name ,int ItemID , int value , int ParentItemID , int amount) {
        this.name = name;
        this.ItemID = ItemID;
        this.value = value;
        this.ParentItemID = ParentItemID;
        this.amount = amount;
    }
    public Item(String name ,int ItemID , int value , int ParentItemID , int amount, Texture image) {
        this.name = name;
        this.ItemID = ItemID;
        this.value = value;
        this.ParentItemID = ParentItemID;
        this.amount = amount;
        this.image = image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public Texture getImage() {
        return image;
    }

    public int getCapacity() {
        return 25;
    }
    public Type getType(){
        return Type.REGULAR;
    }
    public Item(Item item , int amount) {
        this.name = item.getName();
        this.ItemID = item.getItemID();
        this.ParentItemID = item.getParentItemID();
        this.value = item.getValue();
        this.amount = amount;
    }
    public void addAmount(int add) {
        amount+=add;
    }
    public void reduceAmount(int minus) {
        amount-=minus;
    }
    public Product getDriedFruit() {
        return new Product("Dried " + name,  ItemID + 300, (int) Math.floor( 7.5 * value + 25), 0,1, true, 75, 50,ProductQuality.NORMAL, false, false);
    }
    public Product getJuice() {
        return new Product(name + " Juice",  ItemID + 1000, (int) Math.floor( 2.25 * value), 0,1, true, 80, 70,ProductQuality.NORMAL, false, false);
    }
    public Product getWine() {
      return new Product(name + " Wine",  ItemID + 1100, (int) Math.floor( 3 * value), 0,1, true, 70, 50,ProductQuality.NORMAL, false, false);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentItemID(int parentItemID) {
        ParentItemID = parentItemID;
    }

    public int getAmount() {
        return amount;
    }


    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }

    public Season getSeason() {
        return null;
    }
    public String getName() {
        return name;
    }

    public int getParentItemID() {
        return ParentItemID;
    }

    public boolean questEquals(Item item) {
        if (item.getItemID() != this.ItemID)
            return false;
        return item.getAmount() <= this.amount;
    }

    public Result<String> eat() { return null; };
}  
