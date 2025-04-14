package models.things.relations;

import java.util.ArrayList;
import models.things.Item;

public class Quest {
    private int index;
    private ArrayList<Item> requiermentItems;
    private ArrayList<Item> rewards;
    private int rewardMoney;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(int rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public ArrayList<Item> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<Item> rewards) {
        this.rewards = rewards;
    }

    public ArrayList<Item> getRequiermentItems() {
        return requiermentItems;
    }

    public void setRequiermentItems(ArrayList<Item> requiermentItems) {
        this.requiermentItems = requiermentItems;
    }
}
