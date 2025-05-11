package models.things.tools;

import models.App;
import models.Point;
import models.Result;
import models.enums.SkillType;
import models.things.Item;

public class WateringCan extends Item {
    private Type type;
    private int capacity;

    public WateringCan(Type type) {
        super(type.getName() + "watering can", 60, type.getPrice(), 0, 1);

        if(type.getName().equals("Regular")) {
            capacity = 40;
        } if(type.getName().equals("Copper")) {
            capacity = 55;
        } if (type.getName().equals("Silver")) {
            capacity = 70;
        } else if(type.getName().equals("Golden")) {
            capacity = 85;
        } else {
            capacity = 100;
        }
    }

    public int getEnergyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[0].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff().getSkill().getType().equals(SkillType.FARMING)) {
            fraction++;
        }

        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }

    public Result<String> useTool(Point point) {
        return new Result<>(true, "Watering can used at point " + point);
    }
}
