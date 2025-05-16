package models.things.tools;

import models.*;
import models.enums.SkillType;
import models.enums.TileType;
import models.things.Item;

public class Axe extends Item {
    private Type type;

    public Axe(Type type) {
        super(type.getName() + "-axe", 52, type.getPrice(), 0, 1);
        this.type = type;
    }
    public Type getType(){
        return type;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[3].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff().getType().equals(SkillType.FORAGING)) {
            fraction++;
        }
        return (int) (type.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }
    @Override
    public String useTool(Point point) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        StringBuilder builder = new StringBuilder();
        TileType tileType = App.currentGame.map.tiles[point.getX()][point.getY()].type;
        if (tileType.equals(TileType.TREE)){
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.EMPTY;
            builder.append("Derakht ro Ghat kardi dar  (" + point.x + ", " + point.y + ")");
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
