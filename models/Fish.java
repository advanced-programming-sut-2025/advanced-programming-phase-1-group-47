package models;

import models.enums.AnimalType;
import models.enums.FishType;

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
