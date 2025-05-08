package models;

import models.Farms.*;
import models.buildings.Building;
import models.buildings.Cottage;
import models.buildings.Lake;
import models.buildings.greenHouse;
import models.enums.TileType;
import models.things.products.Fish;

import java.util.ArrayList;

public class Map {
    private ArrayList<Building> buildings;
    public Tile[][] tiles = new Tile[160][120];
    public Farm[] farms = new Farm[4];
    public static final int farmWidth = 50 , farmHeight = 40;
    public Map(String[] types) {
        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 120; j++) {
                this.tiles[i][j] = new Tile(new Point(i,j), TileType.EMPTY);
            }
        }
        for (int i = 0; i < 4; i++) {
            Farm farm;
            Tile[][] temp;
            switch (types[i]) {
                case "1":
                    FishingFarm fishingFarm = new FishingFarm();
                    temp = fishingFarm.getTemp();
                    farm = fishingFarm.getFarm();
                    break;
                case "2":
                    farmingFarm farmingFarm = new farmingFarm();
                    temp = farmingFarm.getTemp();
                    farm = farmingFarm.getFarm();
                    break;
                case "3":
                    QuarryFarm quarryFarm = new QuarryFarm();
                    temp = quarryFarm.getTemp();
                    farm = quarryFarm.getFarm();
                    break;
                case "4":
                    CraftingFarm craftingFarm = new CraftingFarm();
                    temp = craftingFarm.getTemp();
                    farm = craftingFarm.getFarm();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown farm type: " + types[i]);
            }
            farms[i] = farm;
            if (App.currentGame.currentPlayer.getId() == i ){
                if(i==0)
                    setTiles(new Point(0, 0), new Point(50, 40), temp);
                else if(i==1)
                    setTiles(new Point(110, 0), new Point(160, 40), temp);
                else if(i==2)
                    setTiles(new Point(0, 80), new Point(50, 120), temp);
                else if(i==3)
                    setTiles(new Point(110, 80), new Point(160, 120), temp);
            }
        }
    }
    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }
    public Tile[][] getTiles() {
        return tiles;
    }

    public Farm[] getFarms() {
        return farms;
    }

    public void setTiles(Point start, Point end , Tile[][] tiles) {
        for (int i = start.x; i < end.x; i++) {
            for (int j = start.y; j < end.y; j++) {
                this.tiles[i][j] = tiles[i-start.x][j-start.y];
            }
        }
    }
}
