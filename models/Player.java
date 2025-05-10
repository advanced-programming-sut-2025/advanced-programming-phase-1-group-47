package models;

import models.enums.Gender;
import models.enums.SkillType;

public class Player extends User {
    private Point coordinates;
    private Invetory invetory;
    private Skill[] skills = new Skill[]{
            new Skill(SkillType.FARMING),
            new Skill(SkillType.FISHING),
            new Skill(SkillType.MINING),
            new Skill(SkillType.FORAGING)
    };

    private Energy energy = new Energy(200, 200);
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
        return energy.getCurrentEnergy();
    }

    public void setEnergy(int energy) {
         this.energy.setCurrentEnergy(energy);
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

    public Skill getFarmingSkill() {
        return skills[0];
    }

    public void setFarmingSkill(Skill farming) {
        skills[0] = farming;
    }

    public Skill getFishingSkill() {
        return skills[1];
    }

    public void setFishingSkill(Skill fishing) {
        skills[1] = fishing;
    }

    public Skill getMiningSkill() {
        return skills[2];
    }

    public void setMiningSkill(Skill mining) {
        skills[2] = mining;
    }

    public Skill getForagingSkill() {
        return skills[3];
    }

    public void setForagingSkill(Skill foraging) {
        skills[3] = foraging;
    }
}
