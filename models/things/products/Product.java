package models.things.products;

import models.things.Item;

public abstract class Product extends Item {
    private boolean isEdible;
    private int energy;
    private int health;

    // Constructor
    public Product(String name, int itemID, int value, int parentItemID, int amount,
                   boolean isEdible, int energy, int health) {
        super(name, itemID, value, parentItemID, amount);
        this.isEdible = isEdible;
        this.energy = energy;
        this.health = health;
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
}
