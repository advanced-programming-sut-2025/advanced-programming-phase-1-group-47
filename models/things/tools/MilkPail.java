package models.things.tools;

import models.App;
import models.Player;
import models.Point;
import models.Result;
import models.things.Item;

public class MilkPail extends Item {
    public MilkPail() {
        super("-milk pail", 55, 1000, 0, 1);
    }

    public int energyCost() {
        return (int) (4 * App.getCurrentGame().getWeather().getIntensity());
    }

    public Result<String> useTool(){
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        return null;
    }
}
