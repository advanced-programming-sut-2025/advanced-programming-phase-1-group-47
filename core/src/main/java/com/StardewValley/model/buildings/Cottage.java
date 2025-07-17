package com.StardewValley.model.buildings;

import com.StardewValley.model.Ground;
import com.StardewValley.model.Point;
import com.StardewValley.model.Refrigerator;
import com.StardewValley.model.enums.TileType;


public  class Cottage extends Building {
    public Refrigerator fridge;
    public Point Door;
    public Cottage(Ground ground, Point Door) {
        super(ground);
        this.getGround().set_tile_default(TileType.COTTAGE);
    }
}
