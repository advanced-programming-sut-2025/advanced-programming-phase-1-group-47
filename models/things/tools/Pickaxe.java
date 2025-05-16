package models.things.tools;

import controllers.GameMenuController;
import models.*;
import models.enums.SkillType;
import models.enums.TileType;
import models.things.Item;

import java.util.HashMap;
import java.util.Random;

public class Pickaxe extends Item {

    private Type type;

    public Pickaxe(Type type) {
        super(type.getName() + "-pickaxe", 56, type.getPrice(), 0, 1);
        this.type = type;
    }

    public Type getType(){
        return type;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[2].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff().getSkill().getType().equals(SkillType.MINING)) {
            fraction++;
        }

        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }

    public String useTool(Point point) {
        StringBuilder builder = new StringBuilder();
        TileType tileType = App.currentGame.map.tiles[point.getX()][point.getY()].type;
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        if (tileType.equals(TileType.TILLED)){
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.EMPTY;
            builder.append("the ground " + point.x + ", " + point.y + " got UnTilled");
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
