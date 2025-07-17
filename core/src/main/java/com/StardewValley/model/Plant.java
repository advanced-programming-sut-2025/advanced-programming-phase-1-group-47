package com.StardewValley.model;

import com.StardewValley.model.enums.ProductQuality;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.things.Item;
import com.StardewValley.model.things.products.Product;

import java.util.Random;


public class Plant {
    private final int plantID;
    private Point point;
    private final String name;
    private final int baseValue;
    private final boolean isEdible;
    private final int energy;
    private final int health;
    private final String source;
    private int currentStage;
    private int currentStageCount;
    private final int totalHarvestTime;
    private final int regrowthTime;
    private final Season seasonOfGrowth;
    private final int[] growStages;
    private final boolean isReUsable;
    private final boolean canBecomeGiant;
    private final boolean isFruit;
    private boolean hasBeenWatered;
    private boolean hasBeenFertilized;
    private int fertilizerId;

    public Plant(int plantID, Point point, String name, int baseValue, boolean isEdible, int energy, int health,
                 String source, int currentStage, int currentStageCount, int totalHarvestTime, Season seasonOfGrowth,
                 int[] growStages, boolean isReUsable, boolean canBecomeGiant, boolean isFruit , int regrowthTime) {
        this.plantID = plantID;
        this.point = point;
        this.name = name;
        this.baseValue = baseValue;
        this.isEdible = isEdible;
        this.energy = energy;
        this.health = health;
        this.source = source;
        this.currentStage = currentStage;
        this.currentStageCount = currentStageCount;
        this.totalHarvestTime = totalHarvestTime;
        this.seasonOfGrowth = seasonOfGrowth;
        this.growStages = growStages;
        this.isReUsable = isReUsable;
        this.canBecomeGiant = canBecomeGiant;
        this.isFruit = isFruit;
        this.hasBeenWatered = false;
        this.hasBeenFertilized = false;
        this.regrowthTime = regrowthTime;
    }
    public Plant(Plant plant , Point point) {
        this.plantID = plant.getPlantID();
        this.point = point;
        this.name = plant.getName();
        this.baseValue = plant.getBaseValue();
        this.isEdible = plant.isIsEdible();
        this.energy = plant.getEnergy();
        this.health = plant.getHealth();
        this.source = plant.getSource();
        this.currentStage = plant.getCurrentStage();
        this.currentStageCount = plant.getCurrentStageCount();
        this.totalHarvestTime = plant.getTotalHarvestTime();
        this.seasonOfGrowth = plant.getSeasonOfGrowth();
        this.growStages = plant.getGrowStages();
        this.isReUsable = plant.isIsReUsable();
        this.canBecomeGiant = plant.isCanBecomeGiant();
        this.isFruit = plant.isIsFruit();
        this.hasBeenWatered = false;
        this.hasBeenFertilized = false;
        this.regrowthTime = plant.getRegrowthTime();
    }

    public Product harvestPlant() {
        ProductQuality quality = ProductQuality.NORMAL;
        int amount = 1;
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        if (randomNumber < 11 )
            quality = ProductQuality.IRIDIUM;
        else if (randomNumber < 31)
            quality = ProductQuality.GOLD;
        else if (randomNumber < 61)
            quality = ProductQuality.SILVER;
        return new Product(name , plantID ,baseValue , 301 ,amount ,isEdible , energy ,health , quality ,isFruit , !isFruit );
    }
    public Item getSeed() {
        return new Item(source,plantID + 100 ,2 , 401 ,1);
    }

    public void grow() {
        if(currentStage == -1)
            return;
        if (currentStage == -2) {
            if (currentStageCount == regrowthTime - 1)
                currentStage = -1;
            else
                currentStageCount++;
            return;
        }
        int currentCap = growStages[currentStage];
        if ( growStages.length - 1 == currentStage && growStages[growStages.length - 1] - 1 ==currentStageCount){
            currentStage = -1;
            return;
        }
        if (currentStageCount + 1 == currentCap) {
            currentStageCount=0;
            currentStage++;
        }
        else
            currentStageCount++;

    }
 
    public int getPlantID() {
        return plantID;
    }

    public String getName() {
        return name;
    }

    public Point getPoint() {
        return point;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public boolean isIsEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public int getHealth() {
        return health;
    }

    public String getSource() {
        return source;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public int getCurrentStageCount() {
        return currentStageCount;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public Season getSeasonOfGrowth() {
        return seasonOfGrowth;
    }

    public int[] getGrowStages() {
        return growStages;
    }

    public boolean isIsReUsable() {
        return isReUsable;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public boolean isIsFruit() {
        return isFruit;
    }

    public boolean isHasBeenWatered() {
        return hasBeenWatered;
    }

    public boolean isHasBeenFertilized() {
        return hasBeenFertilized;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public void setHasBeenWatered(boolean hasBeenWatered) {
        this.hasBeenWatered = hasBeenWatered;
    }

    public void setHasBeenFertilized(boolean hasBeenFertilized) {
        this.hasBeenFertilized = hasBeenFertilized;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public void setCurrentStageCount(int currentStageCount) {
        this.currentStageCount = currentStageCount;
    }

    public int getFertilizerId() {
        return fertilizerId;
    }

    public void setFertilizerId(int fertilizerId) {
        this.fertilizerId = fertilizerId;
    }
}
