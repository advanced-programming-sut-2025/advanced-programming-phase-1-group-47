package com.StardewValley.model;

import com.StardewValley.model.enums.Season;

public class Tree extends Plant {
    private final int foodCycle;
    public Tree(int plantID, Point point, String name, int baseValue, boolean isEdible, int energy, int health,
                String source, int currentStage, int currentStageCount, int totalHarvestTime, Season seasonOfGrowth,
                int[] growStages, boolean isReUsable, boolean canBecomeGiant, boolean isFruit,
                int foodCycle , int regrowthTime) {
        super(plantID, point, name, baseValue, isEdible, energy, health, source, currentStage,
              currentStageCount, totalHarvestTime, seasonOfGrowth, growStages, isReUsable, canBecomeGiant, isFruit , regrowthTime);
        this.foodCycle = foodCycle;
    }
    public int getFoodCycle() {
        return foodCycle;
    }
}
