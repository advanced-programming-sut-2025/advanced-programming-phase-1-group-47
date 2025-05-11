package models;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import models.things.Item;
public class AllTheItemsInTheGame {
    public static final Map<Integer , Item> allItems;
    public static final Map<Integer , Plant> allPlants;

    static {
        Map<Integer, Item> temp = new HashMap<>();
        temp.put(302, )
        allItems = Collections.unmodifiableMap(temp);
    }
    static {
        Map<Integer , Item> temp = new HashMap<>();
        temp.put(key, value);
        allPlants = Collections.unmodifiableMap(temp);
    }




}
