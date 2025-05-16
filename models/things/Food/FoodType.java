package models.things.Food;

import models.Buff;
import models.enums.SkillType;

public enum FoodType {
    FRIED_EGG("Fried-Egg",250,  50, null, 35),
    BAKED_FISH("Baked-Fish", 251, 75, null, 100),
    SALAD("Salad", 252,  113, null, 110),
    OMELET("Omelet", 253, 100, null, 125),
    PUMPKIN_PIE("Pumpkin", 254, 225, null, 385),
    SPAGHETTI("Spaghetti", 255, 75, null, 120),
    PIZZA("Pizza", 256, 150, null, 300),
    TORTILLA("Tortilla", 257, 50, null, 50),
    MAKI_ROLL("Maki", 258, 100, null, 220),
    TRIPLE_SHOT_ESPRESSO("Triple-Shot-Espresso", 259, 200, new Buff(null, 0, 100), 450),
    COOKIE("Cookie", 260, 90, null, 140),
    HASH_BROWNS("Hash-Browns", 261, 90, new Buff(SkillType.FARMING, 5, 0), 120),
    PANCAKES("Pancakes", 262, 90, new Buff(SkillType.FORAGING, 11, 0), 80),
    FRUIT_SALAD("Fruit-Salad", 263, 263, null, 450),
    RED_PLATE("Red-Plate", 264, 240, new Buff(null, 0, 50), 400),
    BREAD("Bread", 265, 50, null, 60),
    SALMON_DINNER("Salmon-Dinner", 266, 125, null, 300),
    VEGETABLE_MEDLEY("Vegetable-Medley", 267, 165, null, 120),
    FARMER_LUNCH("Farmer's-Lunch", 268, 200, new Buff(SkillType.FARMING, 5, 0), 150),
    SURVIVAL_BURGER("Survival-Burger", 269, 125, new Buff(SkillType.FORAGING, 5, 0), 180),
    DISH_O_THE_SEA("Dish-o-the-Sea", 270, 50, new Buff(SkillType.FISHING, 5, 0), 220),
    SEAFOAM_PUDDING("Seafoam-Pudding", 271, 175, new Buff(SkillType.FISHING, 10, 0), 300),
    MINER_TREAT("Miner-s-Treat", 272, 125, new Buff(SkillType.MINING, 5, 0), 200);

    private final String name;
    private final int id;
    private final int energy;
    private final Buff buff;
    private final int price;

    FoodType(String name, int id , int energy, Buff buff, int price) {
        this.name = name;
        this.id = id;
        this.energy = energy;
        this.buff = buff;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getEnergy() {
        return energy;
    }

    public Buff getBuff() {
        return buff;
    }

    public int getPrice() {
        return price;
    }
}
