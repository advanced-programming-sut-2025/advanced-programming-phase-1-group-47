package models.things.Food;

import models.App;
import models.Result;
import models.things.Item;

public class Food extends Item {
    private FoodType foodType;
    public Food(FoodType foodType) {
        super(foodType.getName(), foodType.getId(), foodType.getPrice(), 0, 1);

        this.foodType = foodType;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    @Override
    public Result<String> eat() {

        for(Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item instanceof Food) {
                Food food =  Food(item);

                if(food.getFoodType().equals(foodType)) {
                    //want to remive item from App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()
                }
            }
        }

    }


}
