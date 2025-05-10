package models.enums;

public enum Rods {

    TRAINING_ROD(25, 8),

    BAMBOO_POLE(500, 8),

    FIBERGLASS_ROD(1800, 6),

    IRIDIUM_ROD(7500, 4);

    private final int cost;
    private final int energy;

    Rods(int cost, int energy) {
        this.cost = cost;
        this.energy = energy;
    }

    public int getCost() {
        return cost;
    }

    public int getEnergy() {
        return energy;
    }
}
