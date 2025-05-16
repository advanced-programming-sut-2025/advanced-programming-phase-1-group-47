package models.things.tools;

import controllers.GameMenuController;
import models.*;
import models.enums.Season;
import models.enums.TileType;
import models.things.Item;

import java.util.HashMap;
import java.util.Random;

public class Scythe extends Item {

    public Scythe() {
        super("-scythe", 57, 0, 0, 1);
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
    public String useTool(Point point) {
        StringBuilder builder = new StringBuilder();
        TileType tileType = App.currentGame.map.tiles[point.getX()][point.getY()].type;
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        Random rand = new Random();
        if (tileType.equals(TileType.FORAGING)) {

            int[] range = getRangeBySeason();
            int randomId = rand.nextInt(Math.abs(range[1] - range[0])) + range[0];

            Item item = AllTheItemsInTheGame.getItemById(randomId);
            builder.append("You got a foraging ").append(item.getName())
                    .append(" at ").append(point.x).append(", ").append(point.y);

            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.EMPTY;
            App.currentGame.currentPlayer.getInvetory().addItem(item);
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
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.EMPTY;
            builder.append("You got a hay at ").append(point.getX()).append(", ").append(point.getY());
        }
        else {
            builder.append("The point you selected is a ")
                    .append(tileType.toString().toLowerCase());
        }

        return builder.toString();
    }

}
