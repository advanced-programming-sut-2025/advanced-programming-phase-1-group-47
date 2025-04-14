package models;

import java.util.ArrayList;
import models.enums.TileType;

public class Ground {
    private final int height;
    private final int width;
    private final Point startPoint;
    private Tile[][] tiles;
    public Ground( int width, int height, int startWidth, int startHeight , ArrayList<Tile> tiles) {
        this.width = width;
        this.height = height;
        this.startPoint = new Point(startWidth, startHeight);
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
    public void set_tile(int x, int y, TileType type) {
        if (x > width || y > height || x < 0 || y < 0) {
            // error
        }
        tiles[x][y].ChangeTile(type);
    }
}
