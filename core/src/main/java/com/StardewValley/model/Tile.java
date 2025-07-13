package com.StardewValley.model;

import com.StardewValley.model.enums.TileType;

public class Tile {
    public Point point;
    public TileType type;
    public Tile(Point point, TileType type) {
        this.point = point;
        this.type = type;
    }
    public void ChangeTile (TileType type) {
        this.type = type;
    }
}
