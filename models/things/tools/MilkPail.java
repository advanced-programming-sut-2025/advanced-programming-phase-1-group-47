package models.things.tools;

import models.App;
import models.Point;
import models.Result;
import models.things.Item;

public class MilkPail extends Item {
    public MilkPail() {
        super("milk pail", 55, 1000, 0, 1);
    }

    public int energyCost() {
        return (int) (4 * App.getCurrentGame().getWeather().getIntensity());
    }

    public Result<String> useTool(){
        return null;
    }
}
