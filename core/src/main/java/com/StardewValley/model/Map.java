package com.StardewValley.model;

import java.util.ArrayList;

import com.StardewValley.model.Farms.*;
import com.StardewValley.model.enums.TileType;

public class Map {
    private ArrayList<Building> buildings;
    public Tile[][] tiles = new Tile[160][120];
    public Farm[] farms = new Farm[4];
    public static final int farmWidth = 50, farmHeight = 40;

    public Map(String[] types) {
        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 120; j++) {
                tiles[i][j] = new Tile(new Point(i, j), TileType.EMPTY);
            }
        }
        for (int i = 0; i < types.length; i++) {
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
                    ForgagingFarm forgagingFarm = new ForgagingFarm();
                    temp = forgagingFarm.getTemp();
                    farm = forgagingFarm.getFarm();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown farm type: " + types[i]);
            }
            farms[i] = farm;

            Point start = App.farmStart[i];
            Point end = new Point(start.x + farmWidth, start.y + farmHeight);

            setTiles(start, end, temp);

            Point personPoint = new Point(start.x + farm.personPoint.x, start.y + farm.personPoint.y);
            tiles[personPoint.x][personPoint.y].type = TileType.PERSON;
        }
        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 120; j++) {
                if ((i == 50 || i == 110 || j == 40 || j == 80) && !(((i < 110 && i > 50) || (j < 80 && j > 40)))) {
                    tiles[i][j].type = TileType.WALL;
                }
            }
        }
        setTiles(new Point(60, 110), new Point(67, 117), setTiles(8, 8, TileType.BLACKSMITH));
        setTiles(new Point(70, 90), new Point(77, 97), setTiles(8, 8, TileType.MARNIESRANCH));
        setTiles(new Point(80, 30), new Point(87, 37), setTiles(8, 8, TileType.CARPENTER));
        setTiles(new Point(60, 20), new Point(67, 27), setTiles(8, 8, TileType.FISHSHOP));
        setTiles(new Point(120, 60), new Point(127, 67), setTiles(8, 8, TileType.JOJAMART));
        setTiles(new Point(25, 70), new Point(32, 77), setTiles(8, 8, TileType.STARDROPSALOON));
        setTiles(new Point(15, 50), new Point(22, 57), setTiles(8, 8, TileType.PIERRESSTORE));

        setTiles(new Point(52, 56), new Point(58,62), setTiles(6,6, TileType.NPCHOUSE));
        tiles[58][59].type = TileType.ABIGEL;
        setTiles(new Point(54,70), new Point(60,76), setTiles(6,6, TileType.NPCHOUSE));
        tiles[60][73].type = TileType.LEAH;
        setTiles(new Point(100, 55), new Point(106,61), setTiles(6,6, TileType.NPCHOUSE));
        tiles[106][58].type = TileType.ROBIN;
        setTiles(new Point(70,45), new Point(76,51), setTiles(6,6, TileType.NPCHOUSE));
        tiles[76][48].type = TileType.SEBASTIAN;
        setTiles(new Point(80, 57), new Point(86,63), setTiles(6,6, TileType.NPCHOUSE));
        tiles[86][60].type = TileType.HARVEY;

        tiles[50][10].type = TileType.FARMWALL;
        tiles[50][30].type = TileType.FARMWALL;
        tiles[110][10].type = TileType.FARMWALL;
        tiles[110][30].type = TileType.FARMWALL;

        tiles[50][90].type = TileType.FARMWALL;
        tiles[50][110].type = TileType.FARMWALL;
        tiles[110][90].type = TileType.FARMWALL;
        tiles[110][110].type = TileType.FARMWALL;

        tiles[20][40].type = TileType.FARMWALL;
        tiles[40][40].type = TileType.FARMWALL;
        tiles[20][80].type = TileType.FARMWALL;
        tiles[40][80].type = TileType.FARMWALL;

        tiles[120][40].type = TileType.FARMWALL;
        tiles[140][40].type = TileType.FARMWALL;
        tiles[120][80].type = TileType.FARMWALL;
        tiles[140][80].type = TileType.FARMWALL;

    }

    public TileType[][] setTiles(int width, int height, TileType type) {
        TileType[][] temp = new TileType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                temp[i][j] = type;
            }
        }
        return temp;
    }

    public void setTiles(Point start, Point end, Tile[][] tiles) {
        for (int i = start.x; i < end.x; i++) {
            for (int j = start.y; j < end.y; j++) {
                this.tiles[i][j] = tiles[i - start.x][j - start.y];
            }
        }
    }

    public void setTiles(Point start, Point end, TileType[][] types) {
        for (int i = start.x; i < end.x; i++) {
            for (int j = start.y; j < end.y; j++) {
                this.tiles[i][j].type = types[i - start.x][j - start.y];
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
}
