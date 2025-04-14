package models.NPCS;

import models.NPC;
import models.things.Item;
import models.things.relations.Quest;

import java.util.ArrayList;

public class Robin extends NPC{
    @Override
    public void NPC(String name , Quest quest1 , Quest quest2 , Quest quest3 , ArrayList<Item> favorites){
        super.NPC(name , quest1 , quest2 , quest3 , favorites);
    }
}
