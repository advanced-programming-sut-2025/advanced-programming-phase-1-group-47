package models;
import java.util.HashMap;
import models.enums.AnimalProductType;
import models.enums.AnimalType;
import models.things.Item;

public class Animal extends Item {
    private final AnimalType animalType;
    private HashMap<Player, Integer> friendship = new HashMap<>();
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

//
//    public void increaseFriendship(Player player, int amount) {
//        int current = this.friendship.getOrDefault(player, 0);
//        this.friendship.put(player, current + amount);
//    }
//
//    public int getFriendship(Player player) {
//        return this.friendship.getOrDefault(player, 0);
//    }

    public HashMap<Player, Integer> getFriendshipMap() {
        return friendship;
    }

    public void setFriendshipMap(HashMap<Player, Integer> friendship) {
        this.friendship = friendship;
    }
}
