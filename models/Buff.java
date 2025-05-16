package models;

import models.enums.SkillType;

public class Buff {
    private SkillType skillType;
    private int hour;
    private int increaseMaximumEnergy;

    public Buff(SkillType skillType, int hour, int increaseMaximumEnergy) {
        this.skillType = skillType;
        this.hour = hour;
        this.increaseMaximumEnergy = increaseMaximumEnergy;
    }

    public SkillType getType() {
        return skillType;
    }

    public int getHour() {
        return hour;
    }

    public int isIncreaseMaximumEnergy() {
        return increaseMaximumEnergy;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }


}
