package com.StardewValley.model.things.tools;

public enum Type {
    REGULAR("Regular", 0, 5, 0),
    COPPER("Copper", 1, 4, 2000),
    SILVER("Silver", 2, 3, 5000),
    GOLDEN("Golden", 3, 2, 10000),
    IRIDIUM("Iridium", 4, 1, 25000);

    private final String name;
    private final int level;
    private final int energyCost;
    private final int price;

    Type(String name, int level, int energyCost, int price) {
        this.name = name;
        this.level = level;
        this.energyCost = energyCost;
        this.price = price;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public int getPrice() {
        return price;
    }

    public int getUpgradeCost() {
        Type[] values = Type.values();
        return (this.ordinal() < values.length - 1) ? values[this.ordinal() + 1].getPrice() : 0;
    }


}
