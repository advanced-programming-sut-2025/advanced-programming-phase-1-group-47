package com.StardewValley.model;

import com.StardewValley.model.enums.Season;

public class foragingCrop extends Plant {
    public foragingCrop(Point point ,int plantId , String name ,Season season , int baseValue , int energy , int health){
        super(plantId, point, name, baseValue, true, energy, health, "GOD", 1, 1, 1,season, new int[]{1}, false, false, false, 0);
    }
}
