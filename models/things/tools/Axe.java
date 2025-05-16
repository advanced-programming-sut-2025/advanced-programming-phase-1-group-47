package models.things.tools;

import models.App;
import models.Point;
import models.Result;
import models.Skill;
import models.enums.SkillType;
import models.things.Item;

public class Axe extends Item {
    private Type type;

    public Axe(Type type) {
        super(type.getName() + "-axe", 52, type.getPrice(), 0, 1);
        this.type = type;
    }
    public Type getType(){
        return type;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[3].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff().getType().equals(SkillType.FORAGING)) {
            fraction++;
        }
        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }
    @Override
    public String useTool(Point point) {
        return "Axe with rod: " + type.getName() + " at point " + point;
    }

}
