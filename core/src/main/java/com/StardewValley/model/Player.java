package com.StardewValley.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.StardewValley.model.enums.*;
import com.StardewValley.model.things.Item;
import com.StardewValley.model.things.relations.Gift;
import com.StardewValley.model.things.tools.*;
import com.badlogic.gdx.graphics.Texture;

public class Player extends User {
    private Point Coordinates;
    private Invetory invetory;
    public Item currentToll;
    public HashMap<String,Animal> animalHashMap = new HashMap<>();
    TrashCanType trashCanType = TrashCanType.REGULARTRASHCAN;
    private Buff buff = null;
    private final Skill[] skills = new Skill[]{
            new Skill(SkillType.FARMING),
            new Skill(SkillType.FISHING),
            new Skill(SkillType.MINING),
            new Skill(SkillType.FORAGING)
    };

    public int trashCanLevel = 0;
    public ArrayList<Item> playerShipping_bin = new ArrayList<>();
    public Energy EnergyObject = new Energy(200,200);
    private int money;
    private int id;
    //friendships & trade
    private Map <Player, Integer> friendshipXP;
    private Map <Player, Integer> friendshipLevel;
    private Map <Player, ArrayList<String>> talkHistory;
    private Map <Player, ArrayList<Gift>> giftHistory;
    private Map <Player, ArrayList<Gift>> pendingGifts;
    private Map <Player, Boolean> hasBeenTalkedTo;
    private Map <Player, Boolean> hasBeenGiftedTo;
    private Map <Player, Boolean> hasbeenHugged;
    private Map <Player, ArrayList<Trade>> pendingTrades;
    private Map <Player, ArrayList<String>> tradeHistory;

    private Player partner;
    private Player pendingPartner;
    private final ArrayList<Recipe> recipes = new ArrayList<>(List.of(
            Recipe.FRIED_EGG_RECIPE,
            Recipe.BAKED_FISH_RECIPE,
            Recipe.SALAD_RECIPE
    ));

    //friendships
    private ArrayList<String> notifications;
    //friendships & trade

    private Refrigerator refrigerator = new Refrigerator();

    public Player(String username, String password, String email, String nickname, Gender gender, String securityQuestion, String securityAnswer) {

        super(username, password, email, nickname, gender, securityQuestion, securityAnswer);
        this.Coordinates = new Point(0, 0);
        this.invetory = new Invetory(20);
        money = 5000;
        friendshipXP = new HashMap<>();
        friendshipLevel = new HashMap<>();
        talkHistory = new HashMap<>();
        giftHistory = new HashMap<>();
        pendingGifts = new HashMap<>();
        hasBeenTalkedTo = new HashMap<>();
        hasBeenGiftedTo = new HashMap<>();
        hasbeenHugged = new HashMap<>();
        pendingTrades = new HashMap<>();
        notifications = new ArrayList<>();
        partner = null;
        pendingPartner = null;
        invetory.addItem(new Axe(Type.GOLDEN));
        invetory.addItem(new Hoe(Type.GOLDEN));
        invetory.addItem(new Pickaxe(Type.GOLDEN));
        invetory.addItem(new WateringCan(Type.SILVER));
        invetory.addItem(new FishingPole(RodType.IRIDIUMROD));
        invetory.addItem(new Scythe());
    }
    public void addToShippingBin(Item item) {
        for(Item item2 : playerShipping_bin){
            if(item2.getItemID() == item.getItemID()) {
                item2.addAmount(item.getAmount());
                return;
            }
        }
        if(item.getItemID() != 0)
            playerShipping_bin.add(item);
    }

    public void setupRelations() {
        for (Player player : App.getCurrentGame().getPlayers()) {
            friendshipXP.put(player, 0);
            friendshipLevel.put(player, 0);
            talkHistory.put(player, new ArrayList<>());
            giftHistory.put(player, new ArrayList<>());
            pendingGifts.put(player, new ArrayList<>());
            pendingTrades.put(player, new ArrayList<>());
            hasBeenGiftedTo.put(player, false);
            hasBeenTalkedTo.put(player, false);
            hasbeenHugged.put(player, false);
        }
    }
    public void addTradeToTradeHistory(Player player ,String tradeMessege) {
        ArrayList<String> oldTradeHistory = tradeHistory.get(player);
        oldTradeHistory.add(tradeMessege);
        tradeHistory.put(player,oldTradeHistory);
    }
    public void addNotifToNotifications(String messege) {
        notifications.add(messege);
    }
    public String printNotifications() {
        StringBuilder output = new StringBuilder();
        for(String messege : notifications)
            output.append(messege).append("\n");
        return output.toString();
    }
    public void resetNotifications() {
        notifications.clear();
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
    public void setHasBeenGiftedTo (Player player , boolean state) {
        hasBeenGiftedTo.put(player, state);
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
    public void addTradeToPendingTrades(Player player , Trade trade) {
        ArrayList<Trade> oldPendingTrades = pendingTrades.get(player);
        oldPendingTrades.add(trade);
        pendingTrades.put(player, oldPendingTrades);
    }

    public void setInvetory(Item item, int amount) {
        Item i = this.invetory.findItemFromName(item.getName());
        item.setAmount(amount + (i == null ? amount : i.getAmount() + amount));
        this.invetory.setItems(item);
    }

    public void addGiftToPendingGifts(Player player, Gift gift) {
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
    public void reduceFriendshipXP(int amount , Player player) {
        int xp = friendshipXP.get(player) - amount;
        int level = friendshipLevel.get(player);
        if (xp < 0) {
            if(level > 2 || level == 0)
                xp=0;
            else {
                level--;
                xp = 100 * (level+1) + xp;
            }
        }

        friendshipLevel.put(player, level);
        friendshipXP.put(player, xp);

    }

    public void gainXP(SkillType type , int xp) {

    }
    public void reduceMoney(int amount) {
        money-=amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Energy getEnergy() {
        return EnergyObject;
    }

    public void setEnergy(Energy Energy) {
        this.EnergyObject = Energy;
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


    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }

    public void addRecipe(Recipe recipe) {
        if (!recipes.contains(recipe)) {
            recipes.add(recipe);
        }
    }


    public Map<Player, ArrayList<String>> getTalkHistory() {
        return talkHistory;
    }

    public Player getPartner() {
        return partner;
    }

    public void setPartner(Player partner) {
        this.partner = partner;
    }

    public Map<Player, ArrayList<Trade>> getPendingTrades() {
        return pendingTrades;
    }

    public Map<Player, Boolean> getHasBeenGiftedTo() {
        return hasBeenGiftedTo;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public Player getPendingPartner() {
        return pendingPartner;
    }

    public void setPendingPartner(Player pendingPartner) {
        this.pendingPartner = pendingPartner;
    }

    public Map<Player, ArrayList<String>> getTradeHistory() {
        return tradeHistory;
    }

    public void setTradeHistory(Map<Player, ArrayList<String>> tradeHistory) {
        this.tradeHistory = tradeHistory;
    }
    public boolean hasRecipe(String recipeName) {
        for(Recipe recipe : this.getRecipes()) {
            if(recipe.getDisplayName().equals(recipeName))
                return  true;
        }

        return false;
    }

    public ArrayList<Item> getPlayerShipping_bin() {
        return playerShipping_bin;
    }
}