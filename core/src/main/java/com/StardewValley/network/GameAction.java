package com.StardewValley.network;

public class GameAction {
    private ActionType actionType;
    private String targetId;
    private String data;
    private float x, y;

    public enum ActionType {
        PLANT_CROP,
        HARVEST_CROP,
        WATER_CROP,
        FERTILIZE_CROP,
        MINE_ROCK,
        CHOP_TREE,
        FISH,
        FEED_ANIMAL,
        MILK_ANIMAL,
        COLLECT_EGG,
        BUILD_STRUCTURE,
        UPGRADE_TOOL,
        BUY_ITEM,
        SELL_ITEM,
        CRAFT_ITEM,
        COOK_ITEM,
        SOCIALIZE_NPC,
        GIVE_GIFT,
        ATTEND_EVENT,
        SLEEP,
        SAVE_GAME
    }

    public GameAction() {}

    public GameAction(ActionType actionType, String targetId, String data) {
        this.actionType = actionType;
        this.targetId = targetId;
        this.data = data;
    }

    public GameAction(ActionType actionType, float x, float y, String data) {
        this.actionType = actionType;
        this.x = x;
        this.y = y;
        this.data = data;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
} 