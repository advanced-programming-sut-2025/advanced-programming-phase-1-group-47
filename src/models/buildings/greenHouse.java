package models.buildings;

import java.util.ArrayList;
import models.Ground;
import models.Map;
import models.Tree;

public  class greenHouse  extends Building  {
    ArrayList<Tree> trees = new ArrayList<>();
    @Override
    public void Building(Ground ground, Map map) {
        super.Building(ground, map);
    }
}
