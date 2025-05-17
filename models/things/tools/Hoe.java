package models.things.tools;

import models.App;
import models.Player;
import models.Point;
import models.Result;
import models.enums.SkillType;
import models.enums.TileType;
import models.things.Item;

public class Hoe extends Item {
    private Type type;

    public Hoe(Type type) {
        super(type.getName() + "-hoe", 61, type.getPrice(), 0, 1);
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[0].getLevel() == 4) {
            fraction++;
        }

//        if(App.getCurrentGame().getCurrentPlayer().getBuff().getType().equals(SkillType.FARMING)) {
//            fraction++;
//        }

        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }
    @Override
    public String useTool(Point point) {
        StringBuilder builder = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        if ((App.currentGame.map.tiles[point.getX()][point.getY()].type.equals(TileType.EMPTY))){
            builder.append("the ground got tilled at " + point.x + ", " + point.y);
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.TILLED;
        }
        else
            builder.append("the point You want it its a " + String.valueOf(App.currentGame.map.tiles[point.getX()][point.getY()].type.toString()).toLowerCase());
        return builder.toString();
    }
}
