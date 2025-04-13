package models;

import models.enums.TileType;

public class Ground {
    private final int height;
    private final int width;
    private final Point startPoint;
    TileType[][] tiles;
    public Ground( int width, int height, int startWidth, int startHeight) {
        this.width = width;
        this.height = height;
        this.startPoint = new Point(startWidth, startHeight);
        this.tiles = new TileType[height][width];
    }

    public int get_height() {
        return height;
    }

    public int get_width() {
        return width;
    }
    public Point get_start_point() {
        return this.startPoint;
    }

    public TileType get_tile(int x, int y) {
        return tiles[x][y];
    }

    public void set_tile(int x, int y, TileType type) {

    }
}
