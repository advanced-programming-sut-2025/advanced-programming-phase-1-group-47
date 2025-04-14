package models;

import java.util.ArrayList;
import models.buildings.Building;

public class Map {
    public Ground ground;
    private ArrayList<Building> buildings;

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }
    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public Ground getGround() {
        return ground;
    }

    public Map(Ground ground) {
    }

    

    

}
