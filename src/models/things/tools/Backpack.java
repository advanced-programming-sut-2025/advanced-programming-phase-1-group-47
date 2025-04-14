package models.things.tools;

import models.Point;
import models.Result;
import models.enums.backpacks;
import models.backpackable;
import java.util.ArrayList;

public class Backpack extends Tool{
    private backpacks backpackType;
    private ArrayList<backpackable> backpackables;
    public Result useTool(Tool tool, Point point) {
        super.useTool(tool, point);
        return null;
    }
    public void setBackpackables(ArrayList<backpackable> backpackables) {
        this.backpackables = backpackables;
    }

    @Override
    public void setItemID(int ItemID) {
        super.setItemID(ItemID);
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
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
    public int getItemID() {
        return super.getItemID();
    }

    @Override
    public int getValue() {
        return super.getValue();
    }
}
