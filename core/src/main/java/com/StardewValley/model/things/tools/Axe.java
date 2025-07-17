package com.StardewValley.model.things.tools;

import com.StardewValley.model.*;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.enums.SkillType;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.StardewValley.model.things.tools.Type;

import java.util.Random;

public class Axe extends Item {
    private Type type;

    public Axe(Type type) {
        super(type.getName() + "-axe", 52, type.getPrice(), 0, 1);
        this.type = type;
    }
    @Override
    public Type getType(){
        return this.type;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[3].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff() != null && App.getCurrentGame().getCurrentPlayer().getBuff().getType().equals(SkillType.FORAGING)) {
            fraction++;
        }
        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
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
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return ("Not enough energy!");
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        StringBuilder builder = new StringBuilder();
        TileType tileType = App.currentGame.map.tiles[point.getX()][point.getY()].type;
        Random rand = new Random();
        if (tileType.equals(TileType.TREE)){
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.EMPTY;
            builder.append("Derakht ro Ghat kardi dar  (" + point.x + ", " + point.y + ")");
            int randomId = rand.nextInt(4) + 351;
            Item item = AllTheItemsInTheGame.getItemById(randomId);
            builder.append("You got a Tree Seed ").append(item.getName())
                    .append(" at ").append(point.x).append(", ").append(point.y);
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.EMPTY;
            App.currentGame.currentPlayer.getInvetory().addItem(item);

            App.currentGame.currentPlayer.getInvetory().addItem(AllTheItemsInTheGame.getItemById(36));
        }
//        else if (tileType.equals(TileType.MACHINE)){
//            App.currentGame.currentPlayer.getInvetory().addItem(AllTheItemsInTheGame.getItemById(30));
//            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.EMPTY;
//            builder.append("You got a hay at ").append(point.getX()).append(", ").append(point.getY());
//        }
        else {
            builder.append("The point you selected is a ")
                    .append(tileType.toString().toLowerCase());
        }

        return builder.toString();
    }

}
