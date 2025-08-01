package com.StardewValley.model.things.tools;

import com.StardewValley.model.*;
import com.StardewValley.model.enums.*;
import com.StardewValley.model.enums.Weather;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class FishingPole extends Item {
    private final RodType rodType;

    public FishingPole(RodType rodType) {
        super(rodType.getName() + "-fishing pole", 54, rodType.getPrice(), 0, 1);
        this.rodType = rodType;
        image = new Texture("Tools/Fishing_Pole/" + rodType.getName() + ".png");
    }

    public RodType getRodType() {
        return rodType;
    }

    public int energyCost() {
        int fraction = 0;
        if(App.getCurrentGame().getCurrentPlayer().getSkills()[1].getLevel() == 4) {
            fraction++;
        }

        if(App.getCurrentGame().getCurrentPlayer().getBuff() != null && App.getCurrentGame().getCurrentPlayer().getBuff().getType().equals(SkillType.FISHING)) {
            fraction++;
        }

        return (int) (rodType.getEnergyCost() * App.getCurrentGame().getWeather().getIntensity() - fraction);
    }
    public Double fishimgWeather(){
        Weather weather = App.getCurrentGame().getWeather();
        if (weather.equals(Weather.SUNNY))
            return 1.5;
        else if (weather.equals(Weather.RAINY))
            return 1.2;
        else if (weather.equals(Weather.STORMY))
            return 0.5;
        return 1.0;
    }
    public Fish CheepestFish(){
        Season season = App.getCurrentGame().time.getSeason();
        if (season.equals(Season.SUMMER)) {
            return new Fish(FishType.SUNFISH);
        }
        else if (season.equals(Season.FALL)) {
            return new Fish(FishType.SARDINE);
        }
        else if (season.equals(Season.WINTER)){
            return new Fish(FishType.PERCH);
        }
        else if (season.equals(Season.SPRING)){
            return new Fish(FishType.GHOSTFISH);
        }
        return new Fish(FishType.SUNFISH);
    }
    public Fish randomSeasonall(){
        Random rand = new Random();
        for(int i=0; i < 300; i++){
            int a = rand.nextInt(19) + 1050;
            if(AllTheItemsInTheGame.getItemById(a).getSeason().equals(App.currentGame.time.getSeason())){
               if (a >=1066 && a <=1069){
                   if (App.getCurrentGame().getCurrentPlayer().getSkills()[1].getLevel() >= 4) {
                        continue;
                   }
               }
                return (Fish)AllTheItemsInTheGame.getItemById(a);
            }
        }
        return null;
    }
    @Override
    public String useTool(Tile tile) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        currentPlayer.setEnergy(new Energy(currentPlayer.getEnergy().getEnergyCap(),currentPlayer.getEnergy().getCurrentEnergy() - energyCost() * 100));
        App.currentGame.currentPlayer.getSkills()[1].setLevel(4);
        Point point = tile.point;
        TileType tileType = tile.type;
        StringBuilder builder = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.EnergyObject.getCurrentEnergy() - energyCost() <= 0)
            return ("Not enough energy!");
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyCost());
        Random rand = new Random();
        int random = rand.nextInt(10);
        double fishCountRaw = ((double) random / 10.0) * fishimgWeather() * (player.getSkills()[1].getLevel() + 2);
        int fishCount = (int) fishCountRaw;
        double fishQuality = ((random) * (player.getSkills()[1].getLevel() + 2) * rodType.getEnergyCost()) / (7 - fishimgWeather());
        if ((tileType.equals(TileType.LAKE))){
            Fish fish = new Fish(FishType.SUNFISH);
            if (rodType.getName().equals("TrainingRod")){
                player.getInvetory().addItem(fish);
            }
            else if (rodType.getName().equals("BambooPole")){
                player.getInvetory().addItem(randomSeasonall());
            }
            else if (rodType.getName().equals("FiberglassRod")){
                player.getInvetory().addItem(randomSeasonall());
            }
            else if (rodType.getName().equals("IridiumRod")){
                fish = randomSeasonall();
            }
            builder.append("You got a " + fish.getName() + " that worth " + fish.getSellPrice() + "\n");
            fish.setImage(new Texture("Fish/"+fish.getName() + ".png"));
            player.getInvetory().addItem(fish);
        }
        else {
            builder.append("The point you selected is a ")
                    .append(tileType.toString().toLowerCase());
        }
        Gdx.app.log("FishingPole", builder.toString());
        return builder.toString();
    }
}
