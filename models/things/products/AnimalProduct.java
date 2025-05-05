package models.things.products;

public abstract class AnimalProduct extends Product {
    // Constructor for AnimalProduct
    public AnimalProduct(String name, int itemID, int value, int parentItemID, int amount, boolean isEdible, int energy, int health) {
        super(name, itemID, value, parentItemID, amount, isEdible, energy, health);
    }
}
