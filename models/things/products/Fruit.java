package models.things.products;
import models.enums.Season;
public abstract class Fruit extends Product {
    private final Season fruitSeason;

    public Fruit(Season fruitSeason) {
        this.fruitSeason = fruitSeason;
    }

    public Season getFruitSeason() {
        return fruitSeason;
    }

    
}
