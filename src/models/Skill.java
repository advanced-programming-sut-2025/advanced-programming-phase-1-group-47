package models;

import models.enums.*;

public class Skill {
    private final SkillType type;
    private int XP;
    private int level;

    public Skill(SkillType type){
       this.type = type;
       this.XP = 0;
       this.level = 1;
    }

}
