package models;

import models.enums.Gender;

public class User {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Gender gender;
    private int gamePlayed;
    private int maxGold;
    private Game currentGame;
    public User(String username,String password,String email,String nickname,Gender gender){
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
    } 
    public void set_game(Game game){
        this.currentGame = game;
    }
    public void set_username(String username) {
        this.username = username;
    }
    public void set_password(String password) {
        this.password = password;
    }
    public void set_email(String email) {
        this.email = email;
    }
    public void set_nickname(String nickname) {
        this.nickname = nickname;
    }
    public void set_gender(Gender gender) {
        this.gender = gender;
    }
    public void set_game_played(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }
    public void set_gold_Max(int maxGold) {
        this.maxGold = maxGold;
    }
    // 
// ----------------
    // 
    public String get_username(){
        return this.username;
    }
    public String get_password(){
        return this.password;
    }
    public String get_email(){
        return this.email;
    }
    public String get_nickname(){
        return this.nickname;
    }
    public Gender get_gender(){
        return this.gender;
    }
    public int get_game_played(){
        return this.gamePlayed;
    }
    public int get_gold_max(){
        return this.maxGold;
    }
    public Game get_game(){
        return this.currentGame;
    }
}