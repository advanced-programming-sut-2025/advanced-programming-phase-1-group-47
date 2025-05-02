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
    private String securityQuestion;
    private String securityAnswer;
    public void set_game(Game game){
        this.currentGame = game;
    }
    public User(String username, String password, String email, String nickname, Gender gender, String securityQuestion, String securityAnswer){
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGame_played(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    public void setGoldMax(int maxGold) {
        this.maxGold = maxGold;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    // 
// ----------------
    // 
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public String getNickname(){
        return this.nickname;
    }
    public Gender getGender(){
        return this.gender;
    }
    public int getGamePlayed(){
        return this.gamePlayed;
    }
    public int getGoldMax(){
        return this.maxGold;
    }
    public Game getGame(){
        return this.currentGame;
    }
    public String getSecurityQuestion() {
        return this.securityQuestion;
    }
    public String getSecurityAnswer() {
        return this.securityAnswer;
    }
}