package models;

import models.enums.Gender;
import models.enums.SkillType;

public class Player extends User {
    private Point Coordinates;
    private int Energy;
    private Invetory invetory;
    private Skill[] skills = new Skill[]{
            new Skill(SkillType.FARMING),
            new Skill(SkillType.FISHING),
            new Skill(SkillType.MINING),
            new Skill(SkillType.FORAGING)
    };
    
    private Energy energy;
    private int money;
    private int id;

    public Player(String username, String password, String email, String nickname, Gender gender, String securityQuestion, String securityAnswer) {

        super(username, password, email, nickname, gender, securityQuestion, securityAnswer);

    }

    public void gainXP(SkillType type , int xp) {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public void skillProgress(int skillNumber, int progress) {
        skills[skillNumber].progress(progress);
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
    public void addMoney(int moneyToAdd) {
        money+=moneyToAdd;
    }
}
