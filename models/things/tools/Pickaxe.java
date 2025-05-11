package models.things.tools;

import models.App;
import models.Point;
import models.Result;
import models.enums.SkillType;
import models.things.Item;

public class Pickaxe extends Item {

    private Type type;

    public Pickaxe(Type type) {
        super("pickaxe" + type.getName(), 56, type.getPrice(), 0, 1);
        this.type = type;
    }

    public Type getType(){
        return type;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[2].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff().getSkill().getType().equals(SkillType.MINING)) {
            fraction++;
        }

        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }

    public Result<String> useTool(Point point) {
        return new Result<>(true, "Pickaxe used at point " + point);
    }


}
