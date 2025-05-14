package models.things.tools;

import models.App;
import models.Point;
import models.Result;
import models.things.Item;

public class Scythe extends Item {

    public Scythe() {
        super("-scythe", 57, 0, 0, 1);
    }

    public int energyCost() {
        return (int) (2 * App.getCurrentGame().getWeather().getIntensity());
    }

    public Result<String> useTool(){
        return null;
    }
}
