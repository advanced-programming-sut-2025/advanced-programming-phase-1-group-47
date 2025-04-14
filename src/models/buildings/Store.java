package models.buildings;

import models.Ground;
import models.Map;
import models.enums.ShopType;

public  class Store  extends Building  {
    ShopType type;
    @Override
    public void Building(Ground ground, Map map) {
        super.Building(ground, map);
    }
}