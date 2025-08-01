package com.StardewValley.model.buildings;


import com.StardewValley.model.Ground;
import com.StardewValley.model.Point;
import com.StardewValley.model.enums.TileType;

public  class Quarry extends Building  {
    public Point Door;
    public Quarry(Ground ground,Point Door) {
        super(ground);
        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.QUARRY);
    }
}
