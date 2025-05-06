package models.things.products;

import models.enums.FishType;

public abstract class Fish extends Product {
    private FishType type;

    // Constructor
    public Fish(String name, int itemID, int value, int parentItemID, int amount, FishType type) {
        super(name, itemID, value, parentItemID, amount, true, 0, 0);  // فرض کرده‌ام که Fish یک محصول خوراکی است
        this.type = type;
    }

    // Getter and Setter for type
    public FishType getType() {
        return type;
    }

    public void setType(FishType type) {
        this.type = type;
    }
}
