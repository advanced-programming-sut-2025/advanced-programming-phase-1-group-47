package com.StardewValley.model.things.tools;

import com.StardewValley.model.*;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.enums.SkillType;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.StardewValley.model.things.tools.Type;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.Random;

public class Axe extends Item {
    private Type type;
    public Axe(Type type) {
        super(type.getName() + "-axe", 52, type.getPrice(), 0, 1);
        this.type = type;
        image = new Texture("Tools/Axe/"+ type.getName() +"_Axe.png");
    }
    @Override
    public Type getType(){
        return this.type;
    }

    @Override
    public Texture getImage() {
        return image;
    }

    public void setImage() {
        image = new Texture("Tools/Axe/"+ type.getName() +"_Axe.png");
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
    public String useTool(Tile tile) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        currentPlayer.setEnergy(new Energy(currentPlayer.getEnergy().getEnergyCap(),currentPlayer.getEnergy().getCurrentEnergy() - energyCost() * 100));
        Point point = tile.point;
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return ("Not enough energy!");
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        StringBuilder builder = new StringBuilder();
        TileType tileType = tile.type;
        Random rand = new Random();
        if (tileType.equals(TileType.TREE)){
            tile.type = TileType.EMPTY;
            builder.append("Derakht ro Ghat kardi dar  (" + point.x + ", " + point.y + ")");
            int randomId = rand.nextInt(4) + 351;
            Item item = AllTheItemsInTheGame.getItemById(randomId).returnSimiliar();;
            builder.append("You got a Tree Seed ").append(item.getName())
                    .append(" at ").append(point.x).append(", ").append(point.y);
            tile.type = TileType.EMPTY;
            Item x = AllTheItemsInTheGame.getItemById(36).returnSimiliar();
            player.getInvetory().addItem(x);
            player.getInvetory().addItem(item);
        }
        else if (tileType.equals(TileType.MACHINE)){
            player.getInvetory().addItem(AllTheItemsInTheGame.getItemById(30));
            tile.type = TileType.EMPTY;
            builder.append("You got a hay at ").append(point.getX()).append(", ").append(point.getY());
        }
        else {
            builder.append("The point you selected is a ")
                    .append(tileType.toString().toLowerCase());
        }

        return builder.toString();
    }

}
