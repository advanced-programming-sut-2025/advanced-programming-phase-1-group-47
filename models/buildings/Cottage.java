package models.buildings;

import models.Ground;
import models.Map;
import models.things.machines.Refrigerator;


public  class Cottage extends Building {
    public Refrigerator fridge;
    @Override
    public void Building(Ground ground, Map map) {
        super.Building(ground, map);
    }
}
