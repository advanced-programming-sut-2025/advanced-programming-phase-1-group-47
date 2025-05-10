package models;

import models.things.Item;

public class Trade {
    int id;
    Item item;
    Item targetItem;
    int amount;
    int targetAmount;
    int price;
    Player player1;
    Player player2;
    public Trade(int id,Item item , Item targetItem , int amount, int targetAmount , int price , Player player1, Player player2) {
        this.id = id;
        this.item = item;
        this.targetItem = targetItem;
        this.amount = amount;
        this.targetAmount = targetAmount;
        this.player1 = player1;
        this.player2 = player2;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Item getTargetItem() {
        return targetItem;
    }

    public Player getPlayer2() {
        return player2;
    }
}
