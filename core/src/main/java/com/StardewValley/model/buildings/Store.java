package com.StardewValley.model.buildings;

import com.StardewValley.model.Ground;
import com.StardewValley.model.Point;
import com.StardewValley.model.enums.ShopType;
import com.StardewValley.model.enums.TileType;

public  class Store  extends Building  {
    ShopType type;
    public Point Door;
    public Store(Ground ground, TileType type) {
        super(ground);
//        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.STORE);
    }
}