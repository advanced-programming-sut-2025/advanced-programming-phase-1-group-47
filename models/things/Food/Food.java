package models.things.Food;

import models.App;
import models.Energy;
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
        App.getCurrentGame().getCurrentPlayer().setBuff(foodType.getBuff());
        int nEnergy = Math.min(App.getCurrentGame().getCurrentPlayer().getEnergy().getEnergyCap() + foodType.getBuff().isIncreaseMaximumEnergy(), App.getCurrentGame().getCurrentPlayer().getEnergy().getCurrentEnergy() + foodType.getEnergy());
        Energy newEnergy = new Energy(App.getCurrentGame().getCurrentPlayer().getEnergy().getEnergyCap() + foodType.getBuff().isIncreaseMaximumEnergy(), nEnergy);
        App.getCurrentGame().getCurrentPlayer().setEnergy(newEnergy);

        return new Result<>(true, "You ate " + foodType.getName());

    }


}
