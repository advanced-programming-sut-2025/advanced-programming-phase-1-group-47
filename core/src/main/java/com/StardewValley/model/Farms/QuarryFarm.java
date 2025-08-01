package com.StardewValley.model.Farms;


import com.StardewValley.model.*;
import com.StardewValley.model.buildings.*;
import com.StardewValley.model.enums.TileType;

import java.util.ArrayList;
import java.util.Random;

public class QuarryFarm {
    Tile[][] temp = new Tile[50][40];
    Farm farm;

    public QuarryFarm() {
        for (int i = 0; i < Map.farmWidth; i++) {
            for (int j = 0; j < Map.farmHeight; j++) {
                this.temp[i][j] = new Tile(new Point(i, j), TileType.EMPTY);
            }
        }

        Cottage cottage = new Cottage(new Ground(new Point(4, 4), new Point(8, 8)),new Point(8,6));
        greenHouse greenHouses ;
        ArrayList<Quarry> quarries = new ArrayList<>();
        ArrayList<Lake> lakes = new ArrayList<>();

        // Quarry – دو تا، با اندازه متفاوت
        quarries.add(new Quarry(new Ground(new Point(10, 10), new Point(18, 16)), new Point(18,13)));
        quarries.add(new Quarry(new Ground(new Point(25, 5), new Point(32, 10)), new Point(32,7)));
        changeTiles(new Point(10, 10), new Point(18, 16), TileType.QUARRY);
        changeTiles(new Point(10, 13), new Point(10,13), TileType.DOOR);
        changeTiles(new Point(25, 5), new Point(32, 10), TileType.QUARRY);
        changeTiles(new Point(32,7), new Point(32,7), TileType.DOOR);
        // Greenhouse – یکی، کوچک
        greenHouses = (new greenHouse(new Ground(new Point(35, 30), new Point(39,35)), new Point(37,30)));
        changeTiles(new Point(35, 30), new Point(39,35), TileType.GREENHOUSE);
        changeTiles(new Point(37,30), new Point(37,30), TileType.DOOR);
        // Cottage
        changeTiles(new Point(4, 4), new Point(8,8), TileType.COTTAGE);
        // Lake – یکی، کوچک و جدا
        lakes.add(new Lake(new Ground(new Point(40, 10), new Point(44, 14))));
        changeTiles(new Point(40, 10), new Point(44, 14), TileType.LAKE);

        this.farm = new Farm(
                1,
                new Ground(new Point(0, 0), new Point(50, 40)),
                cottage,
                greenHouses,
                quarries,
                lakes,
                new Point(8,7)
        );

        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(400,500); i++) {
            int x = rand.nextInt(Map.farmWidth);
            int y = rand.nextInt(Map.farmHeight);
            int t = rand.nextInt(Map.farmHeight);
            if (this.temp[x][y].type == TileType.EMPTY) {
                int mod = t % 8;
                if (mod == 0) {
                    this.temp[x][y].type = TileType.FORAGING;
                } else if (mod == 1 || mod == 2) {
                    this.temp[x][y].type = TileType.TREE;
                } else if (mod == 3 || mod == 4) {
                    this.temp[x][y].type = TileType.GRASS;
                } else {
                    this.temp[x][y].type = TileType.STONE;
                }
            }
        }
    }

    public void changeTiles(Point start, Point end, TileType type) {
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                this.temp[i][j].type = type;
            }
        }
    }

    public Farm getFarm() {
        return this.farm;
    }

    public Tile[][] getTemp() {
        return temp;
    }
}