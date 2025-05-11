package models;

import models.enums.ProductQuality;
import models.enums.Season;
import models.things.products.Product;


public class Plant {
    private int plantID;
    private Point point;
    private String name;
    private int baseValue;
    private boolean isEdible;
    private int energy;
    private int health;
    private String source;
    private int currentStage;
    private int currentStageCount;
    private int totalHarvestTime;
    private Season seasonOfGrowth;
    private int[] growStages;
    private boolean isReUsable;
    private boolean canBecomeGiant;
    private boolean isFruit;

    public Plant(int plantID, Point point, String name, int baseValue, boolean isEdible, int energy, int health,
                 String source, int currentStage, int currentStageCount, int totalHarvestTime, Season seasonOfGrowth,
                 int[] growStages, boolean isReUsable, boolean canBecomeGiant, boolean isFruit) {
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
    }

    public Product harvestPlant() {
        ProductQuality quality = null;
        int amount = 1;
        //figure out amount (might be potatoes yk)
        //figure out quality with skills and rand here
        return new Product(name , plantID ,baseValue , 301 ,amount ,isEdible , energy ,health , quality ,isFruit , !isFruit );
    }
}
