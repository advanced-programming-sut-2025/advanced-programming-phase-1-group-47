package com.StardewValley.model.enums;

public enum RodType {
    TRAININGROD("TrainingRod", 8, 25,0.1),
    BAMBOOPOLE("BambooPole", 8, 500, 0.5),
    FIBERGLASSROD("FiberglassRod", 6, 1800, 0.9),
    IRIDIUMROD("IridiumRod", 4, 7500, 1.2);

    private final String name;
    private final int energyCost;
    private final int price;
    private final double Quality;

    RodType(String name, int energyCost, int price,double Quality) {
        this.name = name;
        this.energyCost = energyCost;
        this.price = price;
        this.Quality = Quality;
    }

    public double getQuality() {
        return Quality;
    }

    public String getName() {
        return name;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public int getPrice() {
        return price;
    }
}
