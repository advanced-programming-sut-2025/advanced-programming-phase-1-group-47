package models.things.tools;

import models.App;
import models.Plant;
import models.Point;
import models.Result;
import models.enums.SkillType;
import models.enums.TileType;
import models.things.Item;

public class WateringCan extends Item {
    private Type type;
    private int capacity;

    public WateringCan(Type type) {
        super(type.getName() + "-wateringCan", 60, type.getPrice(), 0, 1);

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

    @Override
    public Type getType() {
        return type;
    }

    public void ToolUsed(Point point) {
        System.out.println(App.currentGame.map.tiles[point.getX()][point.getY()].type.getSticker());
    }
    public int getEnergyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[0].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff().getType().equals(SkillType.FARMING)) {
            fraction++;
        }

        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }
    @Override
    public String useTool(Point point) {
        StringBuilder builder = new StringBuilder();
        builder.append("\"Wathering can used at point \" + point");
        if ((App.currentGame.map.tiles[point.getX()][point.getY()].type.equals(TileType.PLANT))){
            builder.append("the plant got wather in  " + point.x + ", " + point.y);
            Plant plant =  App.currentGame.map.farms[App.currentGame.turn].plantMap.get(point);
            plant.setHasBeenWatered(true);
            App.currentGame.map.farms[App.currentGame.turn].plantMap.put(point, plant);
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.TILLED;
        }
        else
            builder.append("the point You want it its a " + String.valueOf(App.currentGame.map.tiles[point.getX()][point.getY()].type.toString()).toLowerCase());
        return builder.toString();
    }
}
