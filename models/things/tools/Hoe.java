package models.things.tools;

import models.App;
import models.Point;
import models.Result;
import models.enums.SkillType;
import models.things.Item;

public class Hoe extends Item {
    private Type type;

    public Hoe(Type type) {
        super("hoe" + type.getName(), 61, type.getPrice(), 0, 1);
        this.type = type;
    }


    public int energyCost() {
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
        return new Result<>(true, "Hoe used at point " + point);
    }
}
