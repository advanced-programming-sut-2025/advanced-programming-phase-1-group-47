package controllers;

import models.*;
import models.enums.Recipe;
import models.things.Food.Food;

import static models.enums.Recipe.canMakeTheFood;
import static models.enums.Recipe.recipeByName;

public class HouseMenuController {
    public Result<String> CookFood(String recipeName) {
        Recipe recipe = recipeByName(recipeName);
        if(recipe == null)
            return new Result<>(false, "Recipe name is incorrect");
        if(canMakeTheFood(App.getCurrentGame().getCurrentPlayer(), recipe)) {
            // have to remove ingredients from inventory
            App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(new Food(recipe.getFoodType()));
        }

        return new Result<>(false, "Can't make this");
    }

}
