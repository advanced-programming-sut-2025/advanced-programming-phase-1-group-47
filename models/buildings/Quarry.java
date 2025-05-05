package models.buildings;


import models.Ground;
import models.Map;
import models.enums.TileType;

public  class Quarry extends Building  {
    public Quarry(Ground ground) {
        super(ground);
        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.QUARRY);
    }
}
