package com.StardewValley.model.things.tools;

import com.StardewValley.model.*;
import com.StardewValley.model.enums.SkillType;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Hoe extends Item {
    private Type type;

    public Hoe(Type type) {
        super(type.getName() + "-hoe", 61, type.getPrice(), 0, 1);
        this.type = Type.COPPER;
        image = new Texture("Tools/Hoe/" + type.getName() + ".png");
    }

    @Override
    public Type getType() {
        return type;
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
    public String useTool(Tile tile) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        currentPlayer.setEnergy(new Energy(currentPlayer.getEnergy().getEnergyCap(),currentPlayer.getEnergy().getCurrentEnergy() - energyCost() * 100));
        Point point = tile.point;
        StringBuilder builder = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return ("Not enough energy!");
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        if ((tile.type.equals(TileType.EMPTY))){
            builder.append("the ground got tilled at " + point.y + ", " + point.x);
            tile.type = TileType.TILLED;
        }
        else
            builder.append("the point You want it its a " + String.valueOf(tile.type.toString()).toLowerCase());
        Gdx.app.log("Hoe", builder.toString());
        return builder.toString();
    }
}
