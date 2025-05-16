package models.things.tools;

import models.*;
import models.enums.Season;
import models.enums.TileType;
import models.things.Item;

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
        if ((App.currentGame.map.tiles[point.getX()][point.getY()].type.equals(TileType.FORAGING))){
            Random rand = new Random();
            int randomId = rand.nextInt(getRangeBySeason()[0]) + getRangeBySeason()[1];
            Item item = AllTheItemsInTheGame.getItemById(randomId);
            builder.append("You got a Forgaging " + item.getName() + " at " + point.x + ", " + point.y);
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.EMPTY;
            App.currentGame.currentPlayer.getInvetory().addItem(item);
        }
        else
            builder.append("the point You want it its a " + String.valueOf(App.currentGame.map.tiles[point.getX()][point.getY()].type.toString()).toLowerCase());
        return builder.toString();
    }
}
