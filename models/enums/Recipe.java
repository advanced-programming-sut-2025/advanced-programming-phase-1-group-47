package models.enums;

import models.Player;
import models.Result;
import models.things.Food.Food;
import models.things.Food.FoodType;
import models.things.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Recipe {
    FRIED_EGG_RECIPE("Fried Egg Recipe", FoodType.FRIED_EGG, 0, new HashMap<>(Map.of(901, 1))),
    BAKED_FISH_RECIPE("Baked Fish Recipe", FoodType.BAKED_FISH, 0, new HashMap<>(Map.of(28, 1, 151, 1))), // sardine 1
    SALAD_RECIPE("Salad Recipe", FoodType.SALAD, 0, new HashMap<>(Map.of(360, 1, 359, 1))),
    OMELET_RECIPE("Omelet Recipe", FoodType.OMELET, 100, new HashMap<>(Map.of(901, 1, 907, 1))),
    PUMPKIN_PIE_RECIPE("Pumpkin Pie Recipe", FoodType.PUMPKIN_PIE, 0, new HashMap<>(Map.of(338, 1, 28, 1, 907, 1 , 27, 1))),
    SPAGHETTI_RECIPE("Spaghetti Recipe", FoodType.SPAGHETTI, 0, new HashMap<>(Map.of(327, 1, 28, 1))),
    PIZZA_RECIPE("Pizza Recipe", FoodType.PIZZA, 150, new HashMap<>(Map.of(327, 1, 28, 1))), // cheese 1
    TORTILLA_RECIPE("Tortilla Recipe", FoodType.TORTILLA, 100, new HashMap<>(Map.of(316, 1))),
    MAKI_ROLL_RECIPE("Maki Roll Recipe", FoodType.MAKI_ROLL, 300, new HashMap<>(Map.of(48, 1, 151, 1))), //fiber 1
    TRIPLE_SHOT_ESPRESSO_RECIPE("Triple Shot Espresso Recipe", FoodType.TRIPLE_SHOT_ESPRESSO, 5000, new HashMap<>(Map.of(6, 3))),
    COOKIE_RECIPE("Cookie Recipe", FoodType.COOKIE, 300, new HashMap<>(Map.of(901, 1, 28, 1, 27, 1))),
    HASH_BROWNS_RECIPE("Hash Browns Recipe", FoodType.HASH_BROWNS, 50, new HashMap<>(Map.of(310, 1))), // oil
    PANCAKES_RECIPE("Pancakes Recipe", FoodType.PANCAKES, 100, new HashMap<>(Map.of(28, 1, 901, 1))),
    FRUIT_SALAD_RECIPE("Fruit Salad Recipe", FoodType.FRUIT_SALAD, 0, new HashMap<>(Map.of(315, 1, 319, 1))), //apricot 1
    RED_PLATE_RECIPE("Red Plate Recipe", FoodType.RED_PLATE, 0, new HashMap<>(Map.of(322, 1, 321, 1))),
    BREAD_RECIPE("Bread Recipe", FoodType.BREAD, 100, new HashMap<>(Map.of(28, 1))),
    SALMON_DINNER_RECIPE("Salmon Dinner Recipe", FoodType.SALMON_DINNER, 0, new HashMap<>(Map.of(151, 1, 329, 1, 308, 1))),
    VEGETABLE_MEDLEY_RECIPE("Vegetable Medley Recipe", FoodType.VEGETABLE_MEDLEY, 0, new HashMap<>(Map.of(327, 1, 331, 1))),
    FARMER_LUNCH_RECIPE("Farmer's Lunch Recipe", FoodType.FARMER_LUNCH, 0, new HashMap<>(Map.of(253, 1, 309, 1))),
    SURVIVAL_BURGER_RECIPE("Survival Burger Recipe", FoodType.SURVIVAL_BURGER, 0, new HashMap<>(Map.of(303, 1, 265, 1, 335, 1))),
    DISH_O_THE_SEA_RECIPE("Dish o' the Sea Recipe", FoodType.DISH_O_THE_SEA, 0, new HashMap<>(Map.of(261, 1))), // sardines 2
    SEAFOAM_PUDDING_RECIPE("Seafoam Pudding Recipe", FoodType.SEAFOAM_PUDDING, 0, new HashMap<>(Map.of(1, 15, 2, 18, 3, 20))), // flounder 1 midnight crap 1
    MINER_TREAT_RECIPE("Miner's Treat Recipe", FoodType.MINER_TREAT, 0, new HashMap<>(Map.of(303, 2, 27, 1, 125, 1)));

    private final String displayName;
    private final FoodType foodType;
    private final int price;
    private final Map<Integer, Integer> items;

    Recipe(String displayName, FoodType foodType, int price, Map<Integer, Integer> items) {
        this.displayName = displayName;
        this.foodType = foodType;
        this.price = price;
        this.items = items;
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

    public static Result<String> makeRecipe(Player player, String recipeName) {
        Recipe recipe = recipeByName(recipeName);
        if(recipe == null)
            return new Result<>(false, "Recipe name is incorrect");
        for (Map.Entry<Integer, Integer> entry : recipe.items.entrySet()) {
            int itemId = entry.getKey();
            int requiredAmount = entry.getValue();

            boolean hasItem = false;
            for(Item item : player.getInvetory().getItems()) {
                if(item.getItemID() == itemId) {
                    hasItem = true;
                    if(item.getAmount() >= requiredAmount) {
                        player.getInvetory().reduceAmount(item, requiredAmount);
                    } else {
                        return new Result<>(false, "Do not have enough " + item.getName());
                    }
                }
            }

            if(!hasItem) {
                return new Result<>(false, "Do not have requirements for recipe");
            }
        }

        Food food = new Food(recipe.getFoodType());
        player.getInvetory().addItem(food);
        return new Result<>(true, "You made " + food.getName() + " it is now in your inventory");
    }


    public static Recipe recipeByName(String recipeName) {
        for (Recipe recipe : Recipe.values()) {
            if (recipe.getDisplayName().equalsIgnoreCase(recipeName)) {
                return recipe;
            }
        }
        return null;
    }

}

