package models;

public class Buff {
    private Skill skill;
    private int hour;
    private boolean increaseMaximumEnergy;

    public Buff(Skill skill, int hour, boolean increaseMaximumEnergy) {
        this.skill = skill;
        this.hour = hour;
        this.increaseMaximumEnergy = increaseMaximumEnergy;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getHour() {
        return hour;
    }

    public boolean isIncreaseMaximumEnergy() {
        return increaseMaximumEnergy;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }


}
