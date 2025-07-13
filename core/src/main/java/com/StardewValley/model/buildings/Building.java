package com.StardewValley.model.buildings;

import com.StardewValley.model.*;


public abstract class Building {
    private Ground ground;
    public Building(Ground ground) {
        this.ground = ground;
    }
    public Ground getGround()
    {
        return ground;
    }
    public int getWidth(){
        return Math.abs(this.ground.getEndPoint().x - this.ground.getStartPoint().x);
    }
    public int getHeight(){
        return Math.abs(this.ground.getEndPoint().y - this.ground.getStartPoint().y);
    }
}