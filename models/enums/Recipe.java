package models.enums;

import models.Buff;
import models.things.Food.FoodType;

public enum Recipe {
    FRIED_EGG_RECIPE("Fried Egg Recipe", FoodType.FRIED_EGG, 0),
    BAKED_FISH_RECIPE("Baked Fish Recipe", FoodType.BAKED_FISH, 0),
    SALAD_RECIPE("Salad Recipe", FoodType.SALAD, 0),
    OMELET_RECIPE("Omelet Recipe", FoodType.OMELET, 100),
    PUMPKIN_PIE_RECIPE("Pumpkin Pie Recipe", FoodType.PUMPKIN_PIE, 0),
    SPAGHETTI_RECIPE("Spaghetti Recipe", FoodType.SPAGHETTI, 0),
    PIZZA_RECIPE("Pizza Recipe", FoodType.PIZZA, 150),
    TORTILLA_RECIPE("Tortilla Recipe", FoodType.TORTILLA, 100),
    MAKI_ROLL_RECIPE("Maki Roll Recipe", FoodType.MAKI_ROLL, 300),
    TRIPLE_SHOT_ESPRESSO_RECIPE("Triple Shot Espresso Recipe", FoodType.TRIPLE_SHOT_ESPRESSO, 5000),
    COOKIE_RECIPE("Cookie Recipe", FoodType.COOKIE, 300),
    HASH_BROWNS_RECIPE("Hash Browns Recipe", FoodType.HASH_BROWNS, 50),
    PANCAKES_RECIPE("Pancakes Recipe", FoodType.PANCAKES, 100),
    FRUIT_SALAD_RECIPE("Fruit Salad Recipe", FoodType.FRUIT_SALAD, 0),
    RED_PLATE_RECIPE("Red Plate Recipe", FoodType.RED_PLATE, 0),
    BREAD_RECIPE("Bread Recipe", FoodType.BREAD, 100),
    SALMON_DINNER_RECIPE("Salmon Dinner Recipe", FoodType.SALMON_DINNER, 0),
    VEGETABLE_MEDLEY_RECIPE("Vegetable Medley Recipe", FoodType.VEGETABLE_MEDLEY, 0),
    FARMER_LUNCH_RECIPE("Farmer's Lunch Recipe", FoodType.FARMER_LUNCH, 0),
    SURVIVAL_BURGER_RECIPE("Survival Burger Recipe", FoodType.SURVIVAL_BURGER, 0),
    DISH_O_THE_SEA_RECIPE("Dish o' the Sea Recipe", FoodType.DISH_O_THE_SEA, 0),
    SEAFOAM_PUDDING_RECIPE("Seafoam Pudding Recipe", FoodType.SEAFOAM_PUDDING, 0),
    MINER_TREAT_RECIPE("Miner's Treat Recipe", FoodType.MINER_TREAT, 0);

    private final String displayName;
    private final FoodType foodType;
    private final int price;

    Recipe(String displayName, FoodType foodType, int price) {
        this.displayName = displayName;
        this.foodType = foodType;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public int getPrice() {
        return price;
    }
}

