package models.things.Food;

import models.Buff;
import models.enums.SkillType;

public enum FoodType {
    FRIED_EGG("Fried Egg", 50, null),
    BAKED_FISH("Baked Fish", 75, null),
    SALAD("Salad", 113, null),
    OMELET("Omelet", 100, null),
    PUMPKIN_PIE("Pumpkin", 225, null),
    SPAGHETTI("Spaghetti", 75, null),
    PIZZA("Pizza", 150, null),
    TORTILLA("Tortilla", 50, null),
    MAKI_ROLL("Maki", 100, null),
    TRIPLE_SHOT_ESPRESSO("Triple", 200, new Buff(null, 0, 100)),
    COOKIE("Cookie", 90, null),
    HASH_BROWNS("Hash Browns", 90, new Buff(SkillType.FARMING, 5, 0)),
    PANCAKES("Pancakes", 90, new Buff(SkillType.FORAGING, 11, 0)),
    FRUIT_SALAD("Fruit Salad", 263, null),
    RED_PLATE("Red Plate", 240, new Buff(null, 0, 50)),
    BREAD("Bread", 50, null),
    SALMON_DINNER("Salmon Dinner", 125, null),
    VEGETABLE_MEDLEY("Vegetable Medley", 165, null),
    FARMER_LUNCH("Farmer's Lunch", 200, new Buff(SkillType.FARMING, 5, 0)),
    SURVIVAL_BURGER("Survival Burger", 125, new Buff(SkillType.FORAGING, 5, 0)),
    DISH_O_THE_SEA("Dish o' the Sea", 50, new Buff(SkillType.FISHING, 5, 0)),
    SEAFOAM_PUDDING("Seafoam Pudding", 175, new Buff(SkillType.FISHING, 10, 0)),
    MINER_TREAT("Miner's Treat", 125, new Buff(SkillType.MINING, 5, 0));

    private final String name;
    private final int energy;
    private final Buff buff;

    FoodType(String name, int energy, Buff buff) {
        this.name = name;
        this.energy = energy;
        this.buff = buff;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public Buff getBuff() {
        return buff;
    }


}
