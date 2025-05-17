package models.things.products;

import models.enums.ProductQuality;
import models.things.Item;

public  class Product extends Item {
    private boolean isEdible;
    private int energy;
    private int health;
    private ProductQuality quality;
    private boolean isFruit;
    private boolean isVegetable;

    // Constructor
    public Product(String name, int itemID, int value, int parentItemID, int amount,
                   boolean isEdible, int energy, int health , ProductQuality quality , boolean isFruit , boolean isVegetable) {
        super(name, itemID, value, parentItemID, amount);
        this.isEdible = isEdible;
        this.energy = energy;
        this.health = health;
        this.quality = quality;
        this.isFruit = isFruit;
        this.isVegetable = isVegetable;
    }

    // Getters and Setters
    public boolean isEdible() {
        return isEdible;
    }

    public void setEdible(boolean edible) {
        isEdible = edible;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isIsFruit() {
        return isFruit;
    }

    public void setIsFruit(boolean isFruit) {
        this.isFruit = isFruit;
    }

    public boolean isIsVegetable() {
        return isVegetable;
    }

    public void setIsVegetable(boolean isVegetable) {
        this.isVegetable = isVegetable;
    }

    public ProductQuality getQuality() {
        return quality;
    }
}
