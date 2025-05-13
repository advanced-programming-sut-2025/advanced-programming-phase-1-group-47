package models;

import models.enums.AnimalProductType;
import models.enums.ProductQuality;

public class AnimalProduct {
    private AnimalProductType animalProductType;
    private ProductQuality productQuality = ProductQuality.NORMAL;

    public AnimalProduct(AnimalProductType animalProductType) {
        this.animalProductType = animalProductType;
    }
    public String getName() {
        return this.animalProductType.getName();
    }

    public int getSellPrice() {
        return (int) (animalProductType.getSellPrice() * (productQuality.getPriceCoefficient()));
    }

    public Integer getContainingEnergy() {return animalProductType.getEnergy();}
}