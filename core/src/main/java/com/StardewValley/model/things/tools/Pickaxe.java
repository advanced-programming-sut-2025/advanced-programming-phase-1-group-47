package com.StardewValley.model.things.tools;
import com.StardewValley.model.*;
import com.StardewValley.model.enums.SkillType;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.StardewValley.model.things.tools.Type;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class Pickaxe extends Item {

    private Type type;

    public Pickaxe(Type type) {
        super(type.getName() + "-pickaxe", 56, type.getPrice(), 0, 1);
        this.type = type;
        image = new Texture("Tools/Pickaxe/"+ type.getName() +"_Pickaxe.png");
    }
    @Override
    public Type getType(){
        return type;
    }

    public int energyCost() {
        int fraction = 0;
//        if(App.getCurrentGame().getCurrentPlayer().getSkills()[2].getLevel() == 4) {
//            fraction++;
//        }
//
//        if(App.currentGame.currentPlayer.getBuff().getType().equals(SkillType.MINING)) {
//            fraction++;
//        }

        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }

    public String useTool(Tile tile) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        currentPlayer.setEnergy(new Energy(currentPlayer.getEnergy().getEnergyCap(),currentPlayer.getEnergy().getCurrentEnergy() - energyCost() * 100));
        Point point = tile.point;
        StringBuilder builder = new StringBuilder();
        TileType tileType = tile.type;
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return ("Not enough energy!");
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        if (tileType.equals(TileType.TILLED)){
            tile.type = TileType.EMPTY;
            builder.append("the ground " + point.x + ", " + point.y + " got UnTilled");
        }
        else if (tileType.equals(TileType.MACHINE)){

        }
        else if (tileType.equals(TileType.STONE)){
                Random rand = new Random();
                int randomId = rand.nextInt(16) + 380;
                Item item = AllTheItemsInTheGame.getItemById(randomId).returnSimiliar();
                builder.append("You got a Mineral: ").append(item.getName())
                        .append(" at ").append(point.x).append(", ").append(point.y);
                tile.type = TileType.EMPTY;
                App.currentGame.currentPlayer.getInvetory().addItem(item);
        }
        else {
            builder.append("The point you selected is a ")
                    .append(tileType.toString().toLowerCase());
        }

        return builder.toString();
    }


}
