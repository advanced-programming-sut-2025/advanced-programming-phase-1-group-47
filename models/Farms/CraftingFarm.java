package models.Farms;

import models.*;
import models.buildings.Cottage;
import models.buildings.Lake;
import models.buildings.Quarry;
import models.buildings.greenHouse;
import models.enums.TileType;

import java.util.ArrayList;
import java.util.Random;

public class CraftingFarm {
    Tile[][] temp = new Tile[50][40];
    Farm farm;

    public CraftingFarm() {
        for (int i = 0; i < Map.farmWidth; i++) {
            for (int j = 0; j < Map.farmHeight; j++) {
                this.temp[i][j] = new Tile(new Point(i, j), TileType.EMPTY);
            }
        }

        Cottage cottage = new Cottage(new Ground(new Point(5, 5), new Point(10, 9)));
        ArrayList<greenHouse> greenHouses = new ArrayList<>();
        ArrayList<Quarry> quarries = new ArrayList<>();
        ArrayList<Lake> lakes = new ArrayList<>();

        // For crafting, let's assume more stones and trees, fewer buildings

        // Greenhouse – یکی، کوچک‌تر، پایین چپ
        greenHouses.add(new greenHouse(new Ground(new Point(2, 30), new Point(6, 35))));
        changeTiles(new Point(2, 30), new Point(6, 35), TileType.GREENHOUSE);

        // Quarry – یکی، بالا راست
        quarries.add(new Quarry(new Ground(new Point(40, 2), new Point(45, 7))));
        changeTiles(new Point(40, 2), new Point(45, 7), TileType.QUARRY);

        // Cottage – وسط نقشه
        changeTiles(new Point(5, 5), new Point(10, 9), TileType.COTTAGE);

        // Lake – یکی، وسط پایین
        lakes.add(new Lake(new Ground(new Point(20, 30), new Point(25, 34))));
        changeTiles(new Point(20, 30), new Point(25, 34), TileType.LAKE);

        this.farm = new Farm(
                1,
                new Ground(new Point(0, 0), new Point(50, 40)),
                cottage,
                greenHouses,
                quarries,
                lakes
        );

        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(1600, 1700); i++) {
            int x = rand.nextInt(Map.farmWidth);
            int y = rand.nextInt(Map.farmHeight);
            int t = rand.nextInt(Map.farmHeight);
            if (this.temp[x][y].type == TileType.EMPTY) {
                if (t % 6 == 0) {
                    this.temp[x][y].type = TileType.FORAGING;
                } else if ((t % 3) % 2 == 0) {
                    this.temp[x][y].type = TileType.TREE;
                } else if ((t % 3) % 2 == 1) {
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
