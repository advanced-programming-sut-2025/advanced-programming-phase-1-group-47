package models.things.tools;

import models.Point;
import models.Result;
import models.enums.backpacks;
import models.backpackable;

import java.util.ArrayList;

public class Backpack extends Tool {
    private backpacks backpackType;
    private ArrayList<backpackable> backpackables;

    // Constructor
    public Backpack(String name, int itemID, int value, int parentItemID, int amount, backpacks backpackType) {
        super(name, itemID, value, parentItemID, amount);
        this.backpackType = backpackType;
        this.backpackables = new ArrayList<>();
    }

    @Override
    public Result useTool(Tool tool, Point point) {
        // You can process something or just return default
        return super.useTool(tool, point); // placeholder
    }

    public void setBackpackables(ArrayList<backpackable> backpackables) {
        this.backpackables = backpackables;
    }

    public void setBackpackType(backpacks backpackType) {
        this.backpackType = backpackType;
    }

    public ArrayList<backpackable> getBackpackables() {
        return backpackables;
    }

    public backpacks getBackpackType() {
        return backpackType;
    }

    public void deleteBackpack(backpackable backpackable) {
        backpackables.remove(backpackable);
    }
}
