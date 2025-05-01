package models;

import models.enums.AnimalProductType;
import models.enums.Animals;
import models.enums.FishType;

import java.util.Map;

public class Fish {
    FishType fishType;
//    public void Animal(AnimalProductType productType, Animals animalType, Point point, String name) {
//
//            super(Animals.COW);
//    }

    public void setFishType(FishType fishType) {
        this.fishType = fishType;
    }

    public FishType getFishType() {
        return fishType;
    }
}
