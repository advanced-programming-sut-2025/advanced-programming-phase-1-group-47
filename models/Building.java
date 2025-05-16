package models;

import models.enums.AnimalProductType;
import models.enums.AnimalType;
import models.things.Item;

import java.util.HashMap;

public class Building extends Item {
    private final FarmBuilding buildingType;

    public Building(FarmBuilding buildingType) {
        super(String.valueOf(buildingType), buildingType.getId(), buildingType.getPrice(), 1000,300);
        this.buildingType = buildingType;
    }
}
