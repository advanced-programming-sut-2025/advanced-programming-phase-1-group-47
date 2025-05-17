package controllers;

import models.*;
import models.enums.Recipe;
import models.things.Food.Food;
import models.things.Item;
import models.things.products.Product;

import static models.enums.Recipe.recipeByName;

public class HouseMenuController {
    public Result<String> makeRecipe(String recipeName) {
        return Recipe.makeRecipe(App.getCurrentGame().getCurrentPlayer(), recipeName);
    }

    public Result<String> showRecipes() {
        for(Recipe recipe : App.getCurrentGame().getCurrentPlayer().getRecipes()) {
            recipe.getDisplayName();
        }

        return new Result<>(false, "");
    }

    public Result<String> putToFridge(String itemName) {
        for(Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getName().equals(itemName)) {
                if(item instanceof Food){
                    Food food = (Food) item;
                    App.getCurrentGame().getCurrentPlayer().getRefrigerator().addItem(food);
                    App.getCurrentGame().getCurrentPlayer().getInvetory().removeItem(item);
                    return new Result<>(true, item.getName() + " is now in your inventory!");
                }

                if(item instanceof Product) {
                    Product product = (Product) item;
                    if(!product.isEdible())
                        return new Result<>(false, product.getName() + " is not edible!");
                    App.getCurrentGame().getCurrentPlayer().getRefrigerator().addItem(product);
                    App.getCurrentGame().getCurrentPlayer().getInvetory().removeItem(item);
                    return new Result<>(true, item.getName() + " is now in your inventory!");
                }

                return new Result<>(false, itemName + " is not edible!");
            }


        }

        return new Result<>(false, "You do not have " + itemName);
    }

    public Result<String> pickFromFridge(String itemName) {
        for(Item item : App.getCurrentGame().getCurrentPlayer().getRefrigerator().getItems()) {
            if(item.getName().equals(itemName)) {
                if(item.getAmount() > App.getCurrentGame().getCurrentPlayer().getInvetory().remainingSpace()) {
                    return new Result<>(false, "Not enough space in inventory!");
                }

                App.getCurrentGame().getCurrentPlayer().getRefrigerator().removeItem(item);
                App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(item);
                return new Result<>(true, itemName +  " added to your inventory successfully!");

            }
        }

        return new Result<>(false, "You do not have " + itemName + " in your fridge!");
    }





}
