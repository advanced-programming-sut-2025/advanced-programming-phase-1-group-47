package models.things.tools;

import models.App;
import models.Player;
import models.Point;
import models.Result;
import models.things.Item;

public class Shear extends Item {

    public Shear() {
        super("-shear", 58, 1000, 0, 1);
    }

    public int energyCost() {
        return (int) (4 * App.getCurrentGame().getWeather().getIntensity());
    }

    public Result<String> useTool(){
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return new Result<>(false, "Not enough energy!");
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        return null;
    }
}
