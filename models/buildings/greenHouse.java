package models.buildings;

import java.util.ArrayList;
import models.Ground;
import models.Map;
import models.Tree;
import models.enums.TileType;

public  class greenHouse  extends Building  {
    ArrayList<Tree> trees = new ArrayList<>();
    boolean hasRepeare = false;
    public greenHouse(Ground ground) {
        super(ground);
        ground.set_tile(ground.get_start_point(),ground.getEndPoint(), TileType.GREENHOUSE);
    }
}
