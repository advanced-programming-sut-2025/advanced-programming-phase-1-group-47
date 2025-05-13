package models;

import java.util.HashMap;
import java.util.Map;

import models.enums.AnimalProductType;
import models.enums.AnimalType;

public abstract class Animal {
    private String name;
    private final AnimalType animalType;
    private Map<Player, Integer> friendship = new HashMap<>();
    private AnimalProductType productType;
    public Animal(AnimalType animalType) {
        this.animalType = animalType;
    }

    public void setProductType(AnimalProductType productType) {
        this.productType = productType;
    }
    public Animal(AnimalProductType productType,AnimalType animalType, Map<Player, Integer> friendship, AnimalType animal, Point point, String name) {
        this.productType = productType;
        this.animalType = animalType;
        this.friendship = friendship;
        this.name = name;
    }
    public AnimalType getAnimalType() {
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
