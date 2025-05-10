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

    @Override
    public int getEnergyCost() {
        return 0;
    }

    @Override
    public Result<String> useTool(Point point) {
        return null;
    }
}
