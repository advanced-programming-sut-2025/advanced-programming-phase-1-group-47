package com.StardewValley.model;


import com.StardewValley.model.things.Item;

public class Trade {
    int id;
    Item item;
    Item targetItem;
    int price;
    int targetPrice;
    public Trade(int id,Item item , Item targetItem , int price , int targetPrice) {
        this.id = id;
        this.item = item;
        this.targetItem = targetItem;
        this.price = price;
        this.targetPrice = targetPrice;
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }



    public Item getTargetItem() {
        return targetItem;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTargetPrice() {
        return targetPrice;
    }
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("item id : ")
              .append(id)
              .append("\nItem : ")
              .append(item.getName())
              .append("\nTarget Item : ")
              .append(targetItem.getName())
              .append("\nprice")
              .append(price)
              .append("\nTarget price")
              .append(targetPrice);
        return output.toString();
    }
}
