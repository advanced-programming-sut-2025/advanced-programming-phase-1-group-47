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

        if(String.valueOf(type).toUpperCase().equals("Regular".toUpperCase())) {
            capacity = 40;
        } if(String.valueOf(type).toUpperCase().equals("Copper".toUpperCase())) {
            capacity = 55;
        } if (String.valueOf(type).toUpperCase().equals("Silver".toUpperCase())) {
            capacity = 70;
        } else if(String.valueOf(type).toUpperCase().equals("Golden".toUpperCase())) {
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
    @Override
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[0].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff() != null && App.getCurrentGame().getCurrentPlayer().getBuff().getType().equals(SkillType.FARMING)) {
            fraction++;
        }

        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }
    @Override
    public String useTool(Point point) {
        if(App.getCurrentGame().getCurrentPlayer().EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return ("Not enough energy!");
        App.getCurrentGame().getCurrentPlayer().getEnergy().setCurrentEnergy(App.getCurrentGame().getCurrentPlayer().EnergyObject.getCurrentEnergy() - energyCost());
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
