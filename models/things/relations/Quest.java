package models.things.relations;

import models.things.Item;

public class Quest {
    private boolean isActive;
    private Item requiermentItems;
    private Item rewards;
    private int rewardMoney;



    public Quest (Item requiermentItems ,Item rewards , int rewardMoney) {
        this.requiermentItems = requiermentItems;
        this.rewards = rewards;
        this.rewardMoney = rewardMoney;
        this.isActive = false;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(int rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Item getRewards() {
        return rewards;
    }

    public void setRewards(Item rewards) {
        this.rewards = rewards;
    }

    public Item getRequiermentItems() {
        return requiermentItems;
    }

    public void setRequiermentItems(Item requiermentItems) {
        this.requiermentItems = requiermentItems;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
