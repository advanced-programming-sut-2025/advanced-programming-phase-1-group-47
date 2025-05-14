package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.things.Item;
import models.things.relations.Quest;

public class NPC {
    private Map <Player, Integer> friendship;
    private Map <Player, Boolean> hasBeenTalkedTo;
    private Map <Player, Boolean> hasBeenGiftedTo;
    private String name;
    private String job;
    private Quest quest1;
    private Quest quest2;
    private Quest quest3;
    private ArrayList<Item> favorites;
    private ArrayList<String> responses;
    private ArrayList<Item> possibleGifts;
    private int TimeUntilQuest3;
    private Point coordinates;
    public String getName() {
        return name;
    }

    public NPC(String name , String job , Quest quest1 , Quest quest2 , Quest quest3 ,ArrayList<Item> favorites
    ,ArrayList<String> responses , ArrayList<Item> possibleGifts , int TimeUntilQuest3 ,Point coordinates){
        this.name = name;
        this.job = job;
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;
        this.favorites = favorites;
        this.responses = responses;
        this.possibleGifts = possibleGifts;
        this.TimeUntilQuest3 = TimeUntilQuest3;
        this.coordinates = coordinates;
        friendship = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers()) {
            friendship.put(player, 0);
        }
        hasBeenGiftedTo  = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers())
            hasBeenGiftedTo.put(player, false);
        hasBeenTalkedTo  = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers())
            hasBeenTalkedTo.put(player, false);
    }

    public void addFriendship(int amount , Player player) {
        if(hasBeenTalkedTo.get(player) && amount==20)
           return;
        if(hasBeenGiftedTo.get(player) && (amount == 50 || amount == 200 ))
            return;
        int friendshipamount = friendship.get(player);
        friendshipamount+=amount;
        if(friendshipamount>799)
            friendshipamount=799;
        friendship.put(player, friendshipamount);
        hasBeenTalkedTo.put(player, true);
    }
    
    public void ActivateQuest(Quest quest) {

    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public Quest getQuest1() {
        return quest1;
    }

    public void setQuest1(Quest quest1) {
        this.quest1 = quest1;
    }

    public Quest getQuest2() {
        return quest2;
    }

    public void setQuest2(Quest quest2) {
        this.quest2 = quest2;
    }

    public Quest getQuest3() {
        return quest3;
    }

    public void setQuest3(Quest quest3) {
        this.quest3 = quest3;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public Map<Player, Integer> getFriendship() {
        return friendship;
    }

    public void setHasBeenTalkedTo(Map<Player, Boolean> hasBeenTalkedTo) {
        this.hasBeenTalkedTo = hasBeenTalkedTo;
    }

    public void setHasBeenGiftedTo(Map<Player, Boolean> hasBeenGiftedTo) {
        this.hasBeenGiftedTo = hasBeenGiftedTo;
    }

    public Map<Player, Boolean> getHasBeenTalkedTo() {
        return hasBeenTalkedTo;
    }

    public Map<Player, Boolean> getHasBeenGiftedTo() {
        return hasBeenGiftedTo;
    }
}
