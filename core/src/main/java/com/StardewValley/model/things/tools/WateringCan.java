package com.StardewValley.model.things.tools;


import com.StardewValley.model.App;
import com.StardewValley.model.Plant;
import com.StardewValley.model.Point;
import com.StardewValley.model.enums.SkillType;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.graphics.Texture;

public class WateringCan extends Item {
    private Type type;
    private int capacity;

    public WateringCan(Type type) {
        super(type.getName() + "-wateringCan", 60, type.getPrice(), 0, 1);
        image = new Texture("Tools/Watering_Can/" + type.getName() + ".png");
        if(String.valueOf(type).equalsIgnoreCase("Regular")) {
            capacity = 40;
        } if(String.valueOf(type).equalsIgnoreCase("Copper")) {
            capacity = 55;
        } if (String.valueOf(type).equalsIgnoreCase("Silver")) {
            capacity = 70;
        } else if(String.valueOf(type).equalsIgnoreCase("Golden")) {
            capacity = 85;
        } else {
            capacity = 100;
        }

        this.type = type;
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
