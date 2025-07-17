package com.StardewValley.model.enums;

public enum Weather {
    SUNNY(1),
    RAINY(1.5),
    STORMY(1.5),
    SNOWY(2);

    private final double intensity;

    Weather(double intensity) {
        this.intensity = intensity;
    }

    public double getIntensity() {
        return intensity;
    }
}
