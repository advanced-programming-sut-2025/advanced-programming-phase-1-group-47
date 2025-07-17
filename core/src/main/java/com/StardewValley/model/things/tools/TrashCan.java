package com.StardewValley.model.things.tools;


import com.StardewValley.model.App;
import com.StardewValley.model.Result;
import com.StardewValley.model.things.Item;

import java.util.HashMap;
public class TrashCan extends Item {
    public int trashLevel = 0;
    public TrashCan() {
        super("-TrashCan", 51, 1000, 0, 1);
    }

    public int energyCost() {

        return (int) (4 * App.getCurrentGame().getWeather().getIntensity());
    }

    public Result<String> useTool(String itemName) {
        return new Result<>(true,"Tool used");
    }
}
