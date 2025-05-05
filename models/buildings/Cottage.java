package models.buildings;

import models.Ground;
import models.Map;
import models.enums.TileType;
import models.things.machines.Refrigerator;


public  class Cottage extends Building {
    public Refrigerator fridge;
    public Cottage(Ground ground) {
        super(ground);
        this.getGround().set_tile_default(TileType.COTTAGE);
    }
}
