package models.buildings;

import models.Ground;
import models.Map;


public abstract class Building {
    private Ground ground;
    private Map map;
 
    public void Building(Ground ground , Map map) {
        this.ground = ground;
        this.map = map;
    }
    public Ground getGround() {
        return ground;
    }
    public Map getMap() {
        return map;
    }
}
