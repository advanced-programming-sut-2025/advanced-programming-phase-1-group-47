package models;

import java.lang.reflect.Array;
import java.util.HashMap;
import models.things.relations.Quest;
import java.util.ArrayList;
import java.util.Map;
import models.Player;
import models.things.Item;

public class NPC {
    private Map <Player, Integer> friendship = new HashMap<>();
    private String name;
    private Quest quest1;
    private Quest quest2;
    private Quest quest3;
    private ArrayList<Item> favorites;

    public String getName() {
        return name;
    }

    public void NPC(String name ,Quest quest1 , Quest quest2 , Quest quest3 ,ArrayList<Item> favorites){

    }

    public void addFriendship() {

    }
    
    public void ActivateQuest(Quest quest) {

    }
}
