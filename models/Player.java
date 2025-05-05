package models;

import models.enums.Gender;
import models.enums.SkillType;

public class Player extends User {
    private Point Coordinates;
    private int Energy;
    private Invetory invetory;
    private Skill[] skills;
    private Energy energy;
    private int money;

    public Player(String username, String password, String email, String nickname, Gender gender, String securityQuestion, String securityAnswer) {
        super(username, password, email, nickname, gender, securityQuestion, securityAnswer);
        this.Coordinates = new Point(0, 0);
        this.invetory = new Invetory();
        this.skills = new Skill[0];
    }
    public void gainXP(SkillType type , int xp) {
    }

    public int getEnergy() {
        return Energy;
    }

    public void setEnergy(int Energy) {
        this.Energy = Energy;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public void setSkills(Skill[] skills) {
        this.skills = skills;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Invetory getInvetory() {
        return invetory;
    }
}
