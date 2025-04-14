package models.enums;

import models.Seed;

public enum SeedType {
    ;
    public static SeedType fromString(String s){
        return SeedType.valueOf(s.toUpperCase());
    }
    Seed seed;

    public void setSeed(Seed seed) {
        this.seed = seed;
    }
}
