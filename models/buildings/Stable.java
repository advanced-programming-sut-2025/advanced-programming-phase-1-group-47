package models.buildings;


import models.Ground;
import models.Map;
import models.Point;

public class Stable extends Building  {
    public Point Door;
    public Stable(Ground ground)
    {
        super(ground);
    }
}
