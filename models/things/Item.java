package models.things;

import models.Result;
import models.Point;
import models.things.tools.Type;

public class Item {
    private String name;
    private int ItemID;
    private int ParentItemID; //for wine/pickled vegtables/fruit jam /fish /plant /i hate NPCs
    private int value;
    private int amount = 0;
    public String useTool(Point point) {
        return "";
    }
    public Item(String name ,int ItemID , int value , int ParentItemID , int amount) {
        this.name = name;
        this.ItemID = ItemID;
        this.value = value;
        this.ParentItemID = ParentItemID;
        this.amount = amount;
    }
    public Type getType(){
        return null;
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
