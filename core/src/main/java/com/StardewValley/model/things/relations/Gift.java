package com.StardewValley.model.things.relations;


import com.StardewValley.model.things.Item;

public class Gift {
    private Item item;
    private int ID;

    public Gift(Item item , int ID) {
        this.item = item;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
