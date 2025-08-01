package com.StardewValley.model;


import com.StardewValley.model.enums.TileType;

public class Ground {
    private  Point startPoint;
    private  Point endPoint;
    public Tile[][] tiles = new Tile[8][8];
    public Ground(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(new Point(startPoint.x + i, startPoint.y + j), TileType.EMPTY);
            }
        }
    }
    public Point getStartPoint() {
        return startPoint;
    }
    public Point getEndPoint() {
        return endPoint;
    }
    public Point get_start_point() {
        return this.startPoint;
    }
    public void set_tile_default(TileType type) {
        int width = endPoint.x - startPoint.x;
        int height = endPoint.y - startPoint.y;
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(new Point(startPoint.x + i, startPoint.y + j), type);
            }
        }
    }
    public void set_tile(Point start,Point end, TileType type) {
        if (end.x > endPoint.x  || end.y > endPoint.y || start.x < startPoint.x || start.y < startPoint.y) {
            return;
        }
        int width = Math.abs(end.x - start.x);
        int height = Math.abs(end.y - start.y);
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(new Point(startPoint.x + i, startPoint.y + j), TileType.EMPTY);
            }
        }
    }
}
