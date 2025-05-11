package models.buildings;


import models.Ground;
import models.Map;
import models.Point;
import models.enums.TileType;

public  class Quarry extends Building  {
    public Point Door;
    public Quarry(Ground ground,Point Door) {
        super(ground);
        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.QUARRY);
//        this.Door = Door;
//        this.getGround().tiles[Door.x][Door.y].type = TileType.Door;
    }
}
