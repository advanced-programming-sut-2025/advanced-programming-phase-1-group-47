package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.enums.Gender;
import models.enums.RodType;
import models.enums.SkillType;
import models.enums.TrashCanType;
import models.things.relations.Gift;
import models.things.tools.*;

public class Player extends User {
    private Point Coordinates;
    private int Energy;
    private Invetory invetory;

    ArrayList<Animal> animals = new ArrayList<>();
    TrashCanType trashCanType = TrashCanType.REGULARTRASHCAN;
    private Buff buff = null;
    private final Skill[] skills = new Skill[]{
            new Skill(SkillType.FARMING),
            new Skill(SkillType.FISHING),
            new Skill(SkillType.MINING),
            new Skill(SkillType.FORAGING)
    };
    
    private Energy energy;
    private int money;
    private int id;
    //friendships
    private Map <Player, Integer> friendshipXP;
    private Map <Player, Integer> friendshipLevel;
    private Map <Player, ArrayList<String>> talkHistory;
    private Map <Player, ArrayList<Gift>> giftHistory;
    private Map <Player, ArrayList<Gift>> pendingGifts;
    private Map <Player, Boolean> hasBeenTalkedTo;
    private Map <Player, Boolean> hasBeenGiftedTo;
    private Map <Player, Boolean> hasbeenHugged;
    private Player partner;
    //friendships
    public Player(String username, String password, String email, String nickname, Gender gender, String securityQuestion, String securityAnswer) {

        super(username, password, email, nickname, gender, securityQuestion, securityAnswer);
        this.Coordinates = new Point(0, 0);
        this.invetory = new Invetory(20);
        money = 0;
        friendshipXP = new HashMap<>();
        friendshipLevel = new HashMap<>();
        talkHistory = new HashMap<>();
        giftHistory = new HashMap<>();
        pendingGifts = new HashMap<>();
        hasBeenTalkedTo = new HashMap<>();
        hasBeenGiftedTo = new HashMap<>();
        hasbeenHugged = new HashMap<>();
        partner = null;
        invetory.addItem(new Axe(Type.REGULAR));
        invetory.addItem(new Hoe(Type.REGULAR));
        invetory.addItem(new Pickaxe(Type.REGULAR));
        invetory.addItem(new WateringCan(Type.REGULAR));
        invetory.addItem(new FishingPole(RodType.TRAININGROD));
        invetory.addItem(new Scythe());
    }

    public void setupRelations() {
        for (Player player : App.getCurrentGame().getPlayers()) {
            friendshipXP.put(player, 0);
            friendshipLevel.put(player, 0);
            talkHistory.put(player, new ArrayList<>());
            giftHistory.put(player, new ArrayList<>());
            pendingGifts.put(player, new ArrayList<>());
            hasBeenGiftedTo.put(player, false);
            hasBeenTalkedTo.put(player, false);
            hasbeenHugged.put(player, false);
        }
    }
    public boolean GetHasTalkedToPlayer(Player player) {
        return hasBeenTalkedTo.get(player);
    }
    public boolean GetHasHuggedPlayer(Player player) {
        return hasbeenHugged.get(player);
    }
    public void setHasBeenTalkedTo(Player player , boolean state) {
        hasBeenTalkedTo.put(player, state);
    }
    public void setHasHuggedPlayer(Player player , boolean state) {
        hasbeenHugged.put(player, state);
    }
    public void setFriendshipLevel(Player player , int value) {
        friendshipLevel.put(player, value);
    }
    public void setFriendshipXP(Player player , int value) {
        friendshipXP.put(player, value);
    }

    public void addMessegeToTalkHistory(Player player , String messege) {
        ArrayList<String> oldTalkhistory = talkHistory.get(player);
        oldTalkhistory.add(messege);
        talkHistory.put(player, oldTalkhistory);
    }
    public void addGiftToPendingGifts(Player player,Gift gift) {
        ArrayList<Gift> oldPendingGifts = pendingGifts.get(player);
        oldPendingGifts.add(gift);
        pendingGifts.put(player, oldPendingGifts);
    }
    public void addGiftToGiftHistory(Player player , Gift gift) {
        ArrayList<Gift> oldGiftHistory = giftHistory.get(player);
        oldGiftHistory.add(gift);
        pendingGifts.put(player, oldGiftHistory);
    }
    public void addFriendshipXP(int amount, Player player) {
        int xp = friendshipXP.getOrDefault(player, 0) + amount;
        int level = friendshipLevel.getOrDefault(player, 0);

        while (level < 2 && xp >= (level + 1) * 100) {
            xp -= (level + 1) * 100;
            level++;
        }

        friendshipLevel.put(player, level);
        friendshipXP.put(player, xp);
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

    public void setCoordinates(Point Coordinates) {
        this.Coordinates = Coordinates;
    }

    public Point getCoordinates() {
        return Coordinates;
    }

    public Map<Player, Integer> getFriendshipLevel() {
        return friendshipLevel;
    }

    public void setFriendshipLevel(Map<Player, Integer> friendshipLevel) {
        this.friendshipLevel = friendshipLevel;
    }

    public Map<Player, Integer> getFriendshipXP() {
        return friendshipXP;
    }

    public void setFriendshipXP(Map<Player, Integer> friendshipXP) {
        this.friendshipXP = friendshipXP;
    }

    public Map<Player, Boolean> getHasbeenHugged() {
        return hasbeenHugged;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }


    public Map<Player, ArrayList<Gift>> getPendingGifts() {
        return pendingGifts;
    }

    public void setPendingGifts(Map<Player, ArrayList<Gift>> pendingGifts) {
        this.pendingGifts = pendingGifts;
    }

    public Map<Player, ArrayList<Gift>> getGiftHistory() {
        return giftHistory;
    }
}
