package models;

import java.util.HashMap;
import java.util.Map;

import models.enums.AnimalProductType;
import models.enums.Animals;

public abstract class Animal {
    private String name;
    private final Animals animalType;
    private Map<Player, Integer> friendship = new HashMap<>();
    public Animal(Animals animalType) {
        this.animalType = animalType;
    }
    private AnimalProductType productType;

    public void setProductType(AnimalProductType productType) {
        this.productType = productType;
    }
    Animal(AnimalProductType productType, Animals animalType, Map<Player, Integer> friendship, Animals animal, Point point, String name) {
        this.productType = productType;
        this.animalType = animalType;
        this.friendship = friendship;
        this.name = name;
    }
    public Animals getAnimalType() {
        return animalType;
    }

    public void setFriendship(Map<Player, Integer> friendship) {
        this.friendship = friendship;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public AnimalProductType getProductType() {
        return productType;
    }

    public Map<Player, Integer> getFriendship() {
        return friendship;
    }

    public void setFriendship(Player player, int level) {
    }

    public void increaseFriendship(Player player, int amount) {
    }

    public void getFriendship(Player player) {
    }
    
}
