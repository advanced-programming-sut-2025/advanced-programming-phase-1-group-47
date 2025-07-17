package com.StardewValley.model.Farms;


import com.StardewValley.model.*;
import com.StardewValley.model.buildings.*;
import com.StardewValley.model.enums.TileType;

import java.util.ArrayList;
import java.util.Random;

public class farmingFarm {
    Tile[][] temp = new Tile[50][40];
    Farm farm;
    public farmingFarm() {
        for (int i = 0; i < Map.farmWidth; i++) {
            for (int j = 0; j < Map.farmHeight; j++) {
                this.temp[i][j] = new Tile(new Point(i, j), TileType.EMPTY);
            }
        }
        Cottage cottage = new Cottage(new Ground(new Point(5, 5), new Point(9, 9)),new Point(5,7));
        greenHouse greenHouses;
        ArrayList<Quarry> quarries = new ArrayList<>();
        ArrayList<Lake> lakes = new ArrayList<>();
        // Greenhouse - افزایش و اندازه بزرگ‌تر
        greenHouses = (new greenHouse(new Ground(new Point(22, 18), new Point(26, 23)),new Point(24,18)));
        changeTiles(new Point(22, 18), new Point(26,23), TileType.GREENHOUSE);
        changeTiles(new Point(24, 18), new Point(24,18), TileType.DOOR);
        // Quarry – یک عدد کوچک
        quarries.add(new Quarry(new Ground(new Point(40, 30), new Point(44, 34)), new Point(40,32)));
        changeTiles(new Point(40, 30), new Point(44, 34), TileType.QUARRY);
        changeTiles(new Point(40,32), new Point(40,32), TileType.DOOR);
        // Cottage
        changeTiles(new Point(5, 5), new Point(9, 9), TileType.COTTAGE);
        changeTiles(new Point(9, 7), new Point(9,7), TileType.DOOR);
        // Lake – فقط یکی، کوچک
        lakes.add(new Lake(new Ground(new Point(32, 8), new Point(36, 12))));
        changeTiles(new Point(32, 8), new Point(36, 12), TileType.LAKE);
        this.farm = new Farm(
                1,
                new Ground(new Point(0, 0), new Point(50, 40)),
                cottage,
                greenHouses,
                quarries,
                lakes,
                new Point(8, 7)
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
                    this.temp[x][y].type = TileType.STONE;
                } else {
                    this.temp[x][y].type = TileType.GRASS;
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

