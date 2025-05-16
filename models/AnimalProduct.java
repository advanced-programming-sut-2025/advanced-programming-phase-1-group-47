package models;

import models.enums.AnimalProductType;
import models.enums.ProductQuality;
import models.things.Item;

public class AnimalProduct extends Item {
    private AnimalProductType animalProductType;
    private ProductQuality productQuality = ProductQuality.NORMAL;

    public AnimalProduct(AnimalProductType animalProductType) {
        super(animalProductType.getName(),animalProductType.getId(), animalProductType.getSellPrice(), 900,100000);
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