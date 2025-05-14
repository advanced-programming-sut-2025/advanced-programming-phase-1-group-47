package models.buildings;

import java.util.ArrayList;
import models.Ground;
import models.Map;
import models.Point;
import models.Tree;
import models.enums.TileType;

public  class greenHouse  extends Building  {
    ArrayList<Tree> trees = new ArrayList<>();
    public boolean hasRepeare = false;
    public Point Door;
    public greenHouse(Ground ground, Point Door) {
        super(ground);
        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.GREENHOUSE);
//        this.Door = Door;
//        this.getGround().tiles[Door.x][Door.y].type = TileType.Door;
    }
}
