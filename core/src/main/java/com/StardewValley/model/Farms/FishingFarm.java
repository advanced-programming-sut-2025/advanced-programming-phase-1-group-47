package com.StardewValley.model.Farms;

import com.StardewValley.model.*;
import com.StardewValley.model.buildings.*;
import com.StardewValley.model.enums.TileType;

import java.util.ArrayList;
import java.util.Random;

public class FishingFarm {
    Tile[][] temp = new Tile[50][40];
    Farm farm;

    public FishingFarm(){
        for(int i=0;i< Map.farmWidth;i++){
            for(int j=0;j< Map.farmHeight;j++){
                this.temp[i][j] = new Tile(new Point(i,j) , TileType.EMPTY);
            }
        }
        Cottage cottage = new Cottage(new Ground(new Point(1,1), new Point(5,5)),
                new Point(5,3));
        greenHouse greenHouses;
        ArrayList<Quarry> quarries = new ArrayList<>();
        ArrayList<Lake> lakes = new ArrayList<>();

        // Greenhouse – یک عدد، کوچک، بالا سمت چپ
        greenHouses = (new greenHouse(new Ground(new Point(2, 2), new Point(6, 7)), new Point(2,5)));
        changeTiles(new Point(2, 2), new Point(6,7), TileType.GREENHOUSE);
        changeTiles(new Point(2, 5), new Point(2,5), TileType.DOOR);
        // Quarry – یک عدد، پایین سمت راست
        quarries.add(new Quarry(new Ground(new Point(40, 30), new Point(44, 34)), new Point(40,32)));
        changeTiles(new Point(40, 30), new Point(44, 34), TileType.QUARRY);
        changeTiles(new Point(42, 30), new Point(42,30), TileType.DOOR);
        // Cottage – یک عدد، وسط نقشه
        changeTiles(new Point(22, 18), new Point(26, 22), TileType.COTTAGE);
        // Lake – دو عدد، کوچک‌تر و در نواحی باز
        lakes.add(new Lake(new Ground(new Point(10, 25), new Point(19, 31))));
        changeTiles(new Point(10, 25), new Point(19, 31), TileType.LAKE);

        lakes.add(new Lake(new Ground(new Point(30, 5), new Point(39, 14))));
        changeTiles(new Point(30, 5), new Point(39, 14), TileType.LAKE);

        this.farm = new Farm(
                1,
                new Ground(new Point(0, 0), new Point(50, 40)),
                cottage,
                greenHouses,
                quarries,
                lakes,
                new Point(23,20)
        );

        Random rand = new Random();
        for (int i=0;i<rand.nextInt(400,500);i++){
            int x = rand.nextInt(Map.farmWidth);
            int y = rand.nextInt(Map.farmHeight);
            int t = rand.nextInt(Map.farmHeight);
            if (this.temp[x][y].type == TileType.EMPTY){
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
    public void changeTiles (Point start, Point end, TileType type) {
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