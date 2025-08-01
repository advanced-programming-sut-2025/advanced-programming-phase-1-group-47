package com.StardewValley.model.things.tools;


import com.StardewValley.controllers.GameMenuController;
import com.StardewValley.model.*;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Random;

public class Scythe extends Item {
    public Scythe() {
        super("-scythe", 57, 0, 0, 1);
        image = new Texture("Tools/Scythe/Regual_Scythe.png");
    }

    public int energyCost() {
        return (int) (2 * App.getCurrentGame().getWeather().getIntensity());
    }

    public Result<String> useTool(){
        return null;
    }

    public int[] getRangeBySeason(){
        Time time = App.getCurrentGame().getTime();
        if (time.getSeason().equals(Season.SUMMER)){
            return new int[]{365,269};
        }
        else if (time.getSeason().equals(Season.SPRING)){
            return new int[]{357,264};
        }
        else if (time.getSeason().equals(Season.FALL)){
            return new int[]{370,374};
        }
        else if (time.getSeason().equals(Season.WINTER)){
            return new int[]{375,379};
        }
        else
            return new int[]{357,379};
    }

    @Override
    public String useTool(Tile tile) {
        Point point = tile.point;
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        currentPlayer.setEnergy(new Energy(currentPlayer.getEnergy().getEnergyCap(),currentPlayer.getEnergy().getCurrentEnergy() - energyCost() * 100));
        StringBuilder builder = new StringBuilder();
        TileType tileType = tile.type;
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return ("Not enough energy!");
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        if (tileType.equals(TileType.FORAGING)) {
            Gdx.app.debug("id", String.valueOf(tile.id));
            Plant item = AllTheItemsInTheGame.getPlantById(tile.id);
            builder.append("You got a foraging ").append(item.getName())
                    .append(" at ").append(point.x).append(", ").append(point.y);

            tile.type = TileType.EMPTY;
            App.currentGame.currentPlayer.getInvetory().addItem(new Item(item.getName(),item.getPlantID(),item.getBaseValue(),-22,1,item.getImage()));
        }
        else if (tileType.equals(TileType.PLANT)){
            GameMenuController controller = new GameMenuController();
            for (HashMap.Entry<Point, Plant> entry : App.currentGame.map.farms[App.getCurrentGame().turn].plantMap.entrySet()) {
                Point plantpoint = entry.getKey();
                Plant plant = entry.getValue();
                if (plantpoint.x == point.x && plantpoint.y == point.y) {
                    builder.append(controller.harvestPlant(plant).getData());
                }
            }
        }
        else if (tileType.equals(TileType.GRASS)){
            App.currentGame.currentPlayer.getInvetory().addItem(AllTheItemsInTheGame.getItemById(30));
            tile.type = TileType.EMPTY;
            builder.append("You got a hay at ").append(point.getX()).append(", ").append(point.getY());
        }
        else {
            builder.append("The point you selected is a ")
                    .append(tileType.toString().toLowerCase());
        }
        Gdx.app.log("Skythe", builder.toString());
        return builder.toString();
    }

}
