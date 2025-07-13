package com.StardewValley.model.enums;

public enum TrashCanType {
    REGULARTRASHCAN("Regular Trash Can", 0.0),
    COPPERTRASHCAN("Copper Trash Can", 0.15),
    SILVERTRASHCAN("Silver Trash Can", 0.30),
    GOLDTRASHCAN("Gold Trash Can", 0.45),
    IRIDIUMTRASHCAN("Iridium Trash Can", 0.60);

    private final String name;
    private final double refundPercentage;

    TrashCanType(String name, double refundPercentage) {
        this.name = name;
        this.refundPercentage = refundPercentage;
    }

    public String getName() {
        return name;
    }

    public double getRefundPercentage() {
        return refundPercentage;
    }
}
