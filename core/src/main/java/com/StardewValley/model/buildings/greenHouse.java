package com.StardewValley.model.buildings;

import java.util.ArrayList;
import com.StardewValley.model.Ground;
import com.StardewValley.model.Point;
import com.StardewValley.model.Tree;
import com.StardewValley.model.enums.TileType;

public  class greenHouse  extends Building  {
    ArrayList<Tree> trees = new ArrayList<>();
    public boolean hasRepeare = false;
    public Point Door;
    public greenHouse(Ground ground, Point Door) {
        super(ground);
        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.GREENHOUSE);
    }
}
