package models.things.products;

public abstract class ArtisanFood extends Product {

    // Constructor for ArtisanFood
    public ArtisanFood(String name, int itemID, int value, int parentItemID, int amount, boolean isEdible, int energy, int health) {
        super(name, itemID, value, parentItemID, amount, isEdible, energy, health);
    }
}
