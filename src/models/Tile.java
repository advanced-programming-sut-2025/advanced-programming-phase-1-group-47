package models;

import models.enums.TileType;

public class Tile {
    private Point point;
    private int tileID;
    TileType type;

    public void ChangeTile (TileType type) {
        
    }
    Tile(Point point, int tileID, TileType type) {
        this.point = point;
        this.tileID = tileID;
        this.type = type;
    }
    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public int getTileID() {
        return tileID;
    }
}
