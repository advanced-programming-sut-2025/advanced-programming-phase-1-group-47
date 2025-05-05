package models.things.relations;

import java.util.HashMap;
import java.util.Map;
import models.App;
import models.Player;
import models.things.Item;

public class Quest {
    private boolean isDone;
    private Map <Player, Boolean> isActive;
    private final int questID;
    private Item requiermentItems;
    private Item rewards;
    private int rewardMoney;



    public Quest (Item requiermentItems ,Item rewards , int rewardMoney , int questID) {
        this.requiermentItems = requiermentItems;
        this.rewards = rewards;
        this.rewardMoney = rewardMoney;
        this.questID = questID;
        isDone = false;
        isActive = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers())
            isActive.put(player, false);
           
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

    public boolean isIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
    public void ActivateQuest(Player player) {
        isActive.put(player, true);
    }

    public int getQuestID() {
        return questID;
    }

    public Map<Player, Boolean> getIsActive() {
        return isActive;
    }
}
