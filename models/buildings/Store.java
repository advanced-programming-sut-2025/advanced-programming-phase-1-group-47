package models.buildings;

import models.Ground;
import models.Map;
import models.enums.ShopType;
import models.enums.TileType;

public  class Store  extends Building  {
    ShopType type;
    public Store(Ground ground, TileType type) {
        super(ground);
        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.STORE);
    }
}