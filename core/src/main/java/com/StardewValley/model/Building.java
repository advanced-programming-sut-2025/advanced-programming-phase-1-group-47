package com.StardewValley.model;

import com.StardewValley.model.things.Item;

public class Building extends Item {
    private final FarmBuilding buildingType;

    public Building(FarmBuilding buildingType) {
        super(String.valueOf(buildingType), buildingType.getId(), buildingType.getPrice(), 1000,300);
        this.buildingType = buildingType;
        this.image = buildingType.getOutTexture();
    }
}
