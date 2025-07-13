package com.StardewValley.model.things.tools;


import com.StardewValley.model.App;
import com.StardewValley.model.Player;
import com.StardewValley.model.Result;
import com.StardewValley.model.things.Item;

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
