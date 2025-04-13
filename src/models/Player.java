package models;

import models.enums.Gender;
import models.enums.SkillType;

public class Player extends User {
    private Point Coordinates;
    private int Energy=0;
    private Invetory invetory;
    private Skill[] skills;

    public Player(String username, String password, String email, String nickname, Gender gender) {
        super(username, password, email, nickname, gender);  
        this.Coordinates = new Point(0, 0);
        this.invetory = new Invetory();
        this.skills = new Skill[0];
    }
    public void gainXP(SkillType type , int xp) {
    }
}
