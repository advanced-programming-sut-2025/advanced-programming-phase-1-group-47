package models;
import models.enums.Season;
import models.things.Item;

public class Tree extends Plant {
    private final int foodCycle;
    public Tree(int plantID, Point point, String name, int baseValue, boolean isEdible, int energy, int health,
                Item source, int currentStage, int currentStageCount, int totalHarvestTime, Season seasonOfGrowth,
                int[] growStages, boolean isReUsable, boolean canBecomeGiant, boolean isFruit,
                int foodCycle) {
        super(plantID, point, name, baseValue, isEdible, energy, health, source, currentStage,
              currentStageCount, totalHarvestTime, seasonOfGrowth, growStages, isReUsable, canBecomeGiant, isFruit);
        this.foodCycle = foodCycle;
    }
    public int getFoodCycle() {
        return foodCycle;
    }
}
