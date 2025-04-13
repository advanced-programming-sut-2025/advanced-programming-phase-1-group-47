package models;

import java.util.HashMap;
import java.util.Map;
import models.enums.Animals;

public class Animal {
    private final Animals animalType;
    private Map<Player, Integer> friendship = new HashMap<>();
    public Animal(Animals animalType) {
        this.animalType = animalType;
    }

    public void setFriendship(Player player, int level) {
    }

    public void increaseFriendship(Player player, int amount) {
    }

    public void getFriendship(Player player) {
    }
    
}
