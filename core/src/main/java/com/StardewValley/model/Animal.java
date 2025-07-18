package com.StardewValley.model;
import com.StardewValley.model.enums.AnimalProductType;
import com.StardewValley.model.enums.AnimalType;
import com.StardewValley.model.things.Item;

import java.util.HashMap;

public class Animal extends Item {
    private final AnimalType animalType;
    public HashMap<Player, Integer> friendship = new HashMap<>();
    private AnimalProductType productType;
    public Animal(AnimalType animalType) {
        super(animalType.getName(), animalType.getItemId(), animalType.getPrice(), 700, 200000);
        this.animalType = animalType;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setProductType(AnimalProductType productType) {
        this.productType = productType;
    }

    public AnimalProductType getProductType() {
        return productType;
    }

    public void setFriendship(Player player, int level) {
        this.friendship.put(player, level);
    }


    public HashMap<Player, Integer> getFriendshipMap() {
        return friendship;
    }

    public void setFriendshipMap(HashMap<Player, Integer> friendship) {
        this.friendship = friendship;
    }
    //
//    public void increaseFriendship(Player player, int amount) {
//        int current = this.friendship.getOrDefault(player, 0);
//        this.friendship.put(player, current + amount);
//    }
//
//    public int getFriendship(Player player) {
//        return this.friendship.getOrDefault(player, 0);
//    }
}
