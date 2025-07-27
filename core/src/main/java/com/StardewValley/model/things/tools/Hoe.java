package com.StardewValley.model.things.tools;

import com.StardewValley.model.App;
import com.StardewValley.model.Player;
import com.StardewValley.model.Point;
import com.StardewValley.model.Tile;
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
        Point point = tile.point;
        StringBuilder builder = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return ("Not enough energy!");
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        if ((App.currentGame.map.tiles[point.getX()][point.getY()].type.equals(TileType.EMPTY))){
            builder.append("the ground got tilled at " + point.y + ", " + point.x);
            App.currentGame.map.tiles[point.getX()][point.getY()].type = TileType.TILLED;
        }
        else
            builder.append("the point You want it its a " + String.valueOf(App.currentGame.map.tiles[point.getX()][point.getY()].type.toString()).toLowerCase());
        Gdx.app.log("Hoe", builder.toString());
        return builder.toString();
    }
}
