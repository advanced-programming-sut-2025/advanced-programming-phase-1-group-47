package models.enums;

public enum Quality {
    REGULAR(5),

    COPPER(4),

    SILVER(3),

    GOLD(2),

    IRIDIUM(1);

    private final int energy;

    Quality(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }
}
