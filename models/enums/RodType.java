package models.enums;

public enum RodType {
    TRAININGROD("TrainingRod", 8, 25),
    BAMBOOPOLE("BambooPole", 8, 500),
    FIBERGLASSROD("FiberglassRod", 6, 1800),
    IRIDIUMROD("IridiumRod", 4, 7500);

    private final String name;
    private final int energyCost;
    private final int price;

    RodType(String name, int energyCost, int price) {
        this.name = name;
        this.energyCost = energyCost;
        this.price = price;
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
