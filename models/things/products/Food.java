package models.things.products;

public abstract class Food extends Product {
    // Constructor for Food
    public Food(String name, int itemID, int value, int parentItemID, int amount, boolean isEdible, int energy, int health) {
        super(name, itemID, value, parentItemID, amount, isEdible, energy, health);
    }
}
