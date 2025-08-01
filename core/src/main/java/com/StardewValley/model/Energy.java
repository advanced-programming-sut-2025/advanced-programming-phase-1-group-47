package com.StardewValley.model;

public class Energy {
    private int energyCap;
    private int currentEnergy;

    public int getEnergyCap() {
        return energyCap;
    }
    public Energy(int energyCap, int currentEnergy) {
        this.energyCap = energyCap;
        this.currentEnergy = currentEnergy;
    }
    public void setEnergyCap(int energyCap) {
        this.energyCap = energyCap;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = Math.min(energyCap, currentEnergy);
    }




}
