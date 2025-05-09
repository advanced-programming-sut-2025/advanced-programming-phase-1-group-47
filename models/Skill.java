package models;

import models.enums.*;

public class Skill {
    private final SkillType type;
    private int xp;
    private int level;

    public Skill(SkillType type){
       this.type = type;
       this.xp = 0;
       this.level = 0;
    }

    public void progress(int progress) {
        xp += progress;
        if(xp > ((level + 1) * 100 + 50) && level < 4)
            level ++;
    }

}
