package models.things.tools;

import models.App;
import models.Point;
import models.Result;
import models.enums.RodType;
import models.enums.SkillType;
import models.things.Item;

public class FishingPole extends Item {
    private final RodType rodType; // Made final to ensure immutability

    public FishingPole(RodType rodType) {
        super(rodType.getName() + "-fishing pole", 54, rodType.getPrice(), 0, 1);
        this.rodType = rodType;
    }

    public RodType getRodType() {
        return rodType;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[1].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff().getSkill().getType().equals(SkillType.FISHING)) {
            fraction++;
        }

        return (int) (rodType.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }

    public Result<String> useTool(Point point) {
        return new Result<>(true, "Fishing with rod: " + rodType.getName() + " at point " + point);
    }
}
