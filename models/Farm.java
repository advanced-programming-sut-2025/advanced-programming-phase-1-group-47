package models;

import java.util.ArrayList;

import models.buildings.Cottage;
import models.buildings.Lake;
import models.buildings.Quarry;
import models.buildings.greenHouse;
import models.enums.TileType;

public class Farm {
    int farmId;
    Ground ground;
    ArrayList<Lake> lakes = new ArrayList<>();
    Cottage cottage;
    ArrayList<greenHouse> greenHouses = new ArrayList<>();
    ArrayList<Quarry> quarries = new ArrayList<>();
    public Farm(int farmId, Ground ground, Cottage cottage, ArrayList<greenHouse> greenHouses, ArrayList<Quarry> quarries,ArrayList<Lake> lakes) {
        this.farmId = farmId;
        this.ground = ground;
        this.cottage = cottage;
        this.greenHouses = greenHouses;
        this.quarries = quarries;
        this.lakes = lakes;
    }
//    farms[0] = new Farm(new Point(0, 0), new Point(farmWidth, farmHeight),TileType.COTTAGE);
//    farms[1] = new Farm(new Point(160 - farmWidth, 0), new Point(160, farmHeight),TileType.LAKE);
//    farms[2] = new Farm(new Point(0, 120 - farmHeight), new Point(farmWidth, 120),TileType.FORAGING);
//    farms[3] = new Farm(new Point(160 - farmWidth, 120 - farmHeight), new Point(160,120),TileType.STONE);
    public void setGround(Ground ground) {
        this.ground = ground;
    }
    public Ground getGround() {
        return ground;
    }
}