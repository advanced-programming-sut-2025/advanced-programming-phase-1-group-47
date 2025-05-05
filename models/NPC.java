package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.things.Item;
import models.things.relations.Quest;

public class NPC {
    private Map <Player, Integer> friendship;
    private String name;
    private String job;
    private Quest quest1;
    private Quest quest2;
    private Quest quest3;
    private ArrayList<Item> favorites;
    private ArrayList<String> responses;
    private ArrayList<Item> possibleGifts;
    private int TimeUntilQuest3;

    public String getName() {
        return name;
    }

    public NPC(String name , String job , Quest quest1 , Quest quest2 , Quest quest3 ,ArrayList<Item> favorites
    ,ArrayList<String> responses , ArrayList<Item> possibleGifts , int TimeUntilQuest3){
        this.name = name;
        this.job = job;
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;
        this.favorites = favorites;
        this.responses = responses;
        this.possibleGifts = possibleGifts;
        this.TimeUntilQuest3 = TimeUntilQuest3;
        friendship = new HashMap<>();
    }

    public void addFriendship(int amount) {

    }
    
    public void ActivateQuest(Quest quest) {

    }
}
