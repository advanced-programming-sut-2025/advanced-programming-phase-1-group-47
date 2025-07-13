package com.StardewValley.model.buildings;


import com.StardewValley.model.Ground;
import com.StardewValley.model.enums.TileType;

public  class Lake  extends Building {
    public Lake(Ground ground) {
        super(ground);
        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.LAKE);
    }
}
