package models.buildings;

import models.Ground;
import models.Map;
import models.Point;
import models.enums.TileType;
import models.things.machines.Refrigerator;


public  class Cottage extends Building {
    public Refrigerator fridge;
    public Point Door;
    public Cottage(Ground ground, Point Door) {
        super(ground);
        this.getGround().set_tile_default(TileType.COTTAGE);
//        this.Door = Door;
//        this.getGround().tiles[Door.x][Door.y].type = TileType.Door;
    }
}
