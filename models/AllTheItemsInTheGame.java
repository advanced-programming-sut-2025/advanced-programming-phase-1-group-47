package models;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import models.enums.Season;
import models.things.Item;
public class AllTheItemsInTheGame {
    public static final Map<Integer , Item> allItems;
    public static final Map<Integer , Plant> allPlants;

    static {
        Map<Integer, Item> temp = new HashMap<>();
        for (int i = 302; i < 343;i++)
            temp.put(i, getPlantById(i).harvestPlant());
        allItems = Collections.unmodifiableMap(temp);
    }
    static {
        Map<Integer , Plant> temp2 = new HashMap<>();
        temp2.put(302, new Plant(302, new Point(0, 0), "Blue Jazz", 50, true, 45, 20, "Jazz Seeds", 0, 0, 7, Season.SPRING, new int[]{1, 2, 2, 2}, false, false, false,0));
        temp2.put(303, new Plant(303, new Point(0, 0), "Carrot", 35, true, 75, 33, "Carrot Seeds", 0, 0, 3, Season.SPRING, new int[]{1, 1, 1}, false, false, false,0));
        temp2.put(304, new Plant(304, new Point(0, 0), "Cauliflower", 175, true, 75, 33, "Cauliflower Seeds", 0, 0, 12, Season.SPRING, new int[]{1, 2, 4, 4, 1}, false, true, false,0));
        temp2.put(305, new Plant(305, new Point(0, 0), "Coffee Bean", 15, false, -1, -1, "Coffee Bean", 2, 0, 10, Season.SPRING_SUMMER, new int[]{1, 2, 2, 3, 2}, true, false, false,2));
        temp2.put(306, new Plant(306, new Point(0, 0), "Garlic", 60, true, 20, 9, "Garlic Seeds", 0, 0, 4, Season.SPRING, new int[]{1, 1, 1, 1}, false, false, false,0));
        temp2.put(307, new Plant(307, new Point(0, 0), "Green Bean", 40, true, 25, 11, "Bean Starter", 3, 0, 10, Season.SPRING, new int[]{1, 1, 1, 3, 4}, true, false, false,3));
        temp2.put(308, new Plant(308, new Point(0, 0), "Kale", 110, true, 50, 22, "Kale Seeds", 0, 0, 6, Season.SPRING, new int[]{1, 2, 2, 1}, false, false, false,0));
        temp2.put(309, new Plant(309, new Point(0, 0), "Parsnip", 35, true, 25, 11, "Parsnip Seeds", 0, 0, 4, Season.SPRING, new int[]{1, 1, 1, 1}, false, false, false,0));
        temp2.put(310, new Plant(310, new Point(0, 0), "Potato", 80, true, 25, 11, "Potato Seeds", 0, 0, 6, Season.SPRING, new int[]{1, 1, 1, 2, 1}, false, false, false,0));
        temp2.put(311, new Plant(311, new Point(0, 0), "Rhubarb", 220, false, -1, -1, "Rhubarb Seeds", 0, 0, 13, Season.SPRING, new int[]{2, 2, 2, 3, 4}, false, false, false,0));
        temp2.put(312, new Plant(312, new Point(0, 0), "Strawberry", 120, true, 50, 22, "Strawberry Seeds", 0, 0, 8, Season.SPRING, new int[]{1, 1, 2, 2, 2}, true, false, false,4));
        temp2.put(313, new Plant(313, new Point(0, 0), "Tulip", 30, true, 45, 20, "Tulip Bulb", 0, 0, 6, Season.SPRING, new int[]{1, 1, 2, 2}, false, false, false,0));
        temp2.put(314, new Plant(314, new Point(0, 0), "Unmilled Rice", 30, true, 3, 1, "Rice Shoot", 0, 0, 8, Season.SPRING, new int[]{1, 2, 2, 3}, false, false, false,0));
        temp2.put(315, new Plant(315, new Point(0, 0), "Blueberry", 50, true, 25, 11, "Blueberry Seeds", 0, 0, 13, Season.SUMMER, new int[]{1, 3, 3, 4, 2}, true, false, false,4));
        temp2.put(316, new Plant(316, new Point(0, 0), "Corn", 50, true, 25, 11, "Corn Seeds", 0, 0, 14, Season.SUMMER_FALL, new int[]{2, 3, 3, 3, 3}, true, false, false,4));
        temp2.put(317, new Plant(317, new Point(0, 0), "Hops", 25, true, 45, 20, "Hops Starter", 0, 0, 11, Season.SUMMER, new int[]{1, 1, 2, 3, 4}, true, false, false,1));
        temp2.put(318, new Plant(318, new Point(0, 0), "Hot Pepper", 40, true, 13, 5, "Pepper Seeds", 0, 0, 5, Season.SUMMER, new int[]{1, 1, 1, 1, 1}, true, false, false,3));
        temp2.put(319, new Plant(319, new Point(0, 0), "Melon", 250, true, 113, 50, "Melon Seeds", 0, 0, 12, Season.SUMMER, new int[]{1, 2, 3, 3, 3}, false, false, false,0));
        temp2.put(320, new Plant(320, new Point(0, 0), "Poppy", 140, true, 45, 20, "Poppy Seeds", 0, 0, 7, Season.SUMMER, new int[]{1, 2, 2, 2}, false, false, false,0));
        temp2.put(321, new Plant(321, new Point(0, 0), "Radish", 90, true, 45, 20, "Radish Seeds", 0, 0, 6, Season.SUMMER, new int[]{2, 1, 2, 1}, false, false, false,0));
        temp2.put(322, new Plant(322, new Point(0, 0), "Red Cabbage", 260, true, 75, 33, "Red Cabbage Seeds", 0, 0, 9, Season.SUMMER, new int[]{2, 1, 2, 2, 2}, false, false, false,0));
        temp2.put(323, new Plant(323, new Point(0, 0), "Starfruit", 750, true, 125, 56, "Starfruit Seeds", 0, 0, 13, Season.SUMMER, new int[]{2, 3, 2, 3, 3}, false, false, false,0));
        temp2.put(324, new Plant(324, new Point(0, 0), "Summer Spangle", 90, true, 45, 20, "Spangle Seeds", 0, 0, 8, Season.SUMMER, new int[]{1, 2, 3, 1}, false, false, false,0));
        temp2.put(325, new Plant(325, new Point(0, 0), "Summer Squash", 45, true, 63, 28, "Summer Squash Seeds", 0, 0, 6, Season.SUMMER, new int[]{1, 1, 1, 2, 1}, true, false, false,3));
        temp2.put(326, new Plant(326, new Point(0, 0), "Sunflower", 80, true, 45, 20, "Sunflower Seeds", 0, 0, 8, Season.SUMMER_FALL, new int[]{1, 2, 3, 2}, false, false, false,0));
        temp2.put(327, new Plant(327, new Point(0, 0), "Tomato", 60, true, 20, 9, "Tomato Seeds", 0, 0, 11, Season.SUMMER, new int[]{2, 2, 2, 2, 3}, true, false, false,4));
        temp2.put(328, new Plant(328, new Point(0, 0), "Wheat", 25, false, -1, -1, "Wheat Seeds", 0, 0, 4, Season.SUMMER_FALL, new int[]{1, 1, 1, 1}, false, false, false,0));
        temp2.put(329, new Plant(329, new Point(0, 0), "Amaranth", 150, true, 50, 22, "Amaranth Seeds", 0, 0, 7, Season.FALL, new int[]{1, 2, 2, 2}, false, false, false,0));
        temp2.put(330, new Plant(330, new Point(0, 0), "Artichoke", 160, true, 30, 13, "Artichoke Seeds", 0, 0, 8, Season.FALL, new int[]{2, 2, 1, 2, 1}, false, false, false,0));
        temp2.put(331, new Plant(331, new Point(0, 0), "Beet", 100, true, 30, 13, "Beet Seeds", 0, 0, 6, Season.FALL, new int[]{1, 1, 2, 2}, false, false, false,0));
        temp2.put(332, new Plant(332, new Point(0, 0), "Bok Choy", 80, true, 25, 11, "Bok Choy Seeds", 0, 0, 4, Season.FALL, new int[]{1, 1, 1, 1}, false, false, false,0));
        temp2.put(333, new Plant(333, new Point(0, 0), "Broccoli", 70, true, 63, 28, "Broccoli Seeds", 0, 0, 8, Season.FALL, new int[]{2, 2, 2, 2}, true, false, false,4));
        temp2.put(334, new Plant(334, new Point(0, 0), "Cranberries", 75, true, 38, 17, "Cranberry Seeds", 0, 0, 7, Season.FALL, new int[]{1, 2, 1, 1, 2}, true, false, false,5));
        temp2.put(335, new Plant(335, new Point(0, 0), "Eggplant", 60, true, 20, 9, "Eggplant Seeds", 0, 0, 5, Season.FALL, new int[]{1, 1, 1, 1}, true, false, false,5));
        temp2.put(336, new Plant(336, new Point(0, 0), "Fairy Rose", 290, true, 45, 20, "Fairy Seeds", 0, 0, 12, Season.FALL, new int[]{1, 4, 4, 3}, false, false, false,0));
        temp2.put(337, new Plant(337, new Point(0, 0), "Grape", 80, true, 38, 17, "Grape Starter", 0, 0, 10, Season.FALL, new int[]{1, 1, 2, 3, 3}, true, false, false,3));
        temp2.put(338, new Plant(338, new Point(0, 0), "Pumpkin", 320, false, -1, -1, "Pumpkin Seeds", 0, 0, 13, Season.FALL, new int[]{1, 2, 3, 4, 3}, false, false, false,0));
        temp2.put(339, new Plant(339, new Point(0, 0), "Yam", 160, true, 45, 20, "Yam Seeds", 0, 0, 10, Season.FALL, new int[]{1, 3, 3, 3}, false, false, false,0));
        temp2.put(340, new Plant(340, new Point(0, 0), "Sweet Gem Berry", 3000, false, -1, -1, "Rare Seed", 0, 0, 24, Season.FALL, new int[]{2, 4, 6, 6, 6}, false, false, false,0));
        temp2.put(341, new Plant(341, new Point(0, 0), "Powdermelon", 60, true, 63, 28, "Powdermelon Seeds", 0, 0, 7, Season.WINTER, new int[]{1, 2, 1, 2, 1}, true, false, false,0));
        temp2.put(342, new Plant(342, new Point(0, 0), "Ancient Fruit", 550, false, -1, -1, "Ancient Seeds", 0, 0, 28, Season.OTHER_THAN_WINER, new int[]{2, 7, 7, 7, 5}, true, false, false,7));
        allPlants = Collections.unmodifiableMap(temp2);
    }
    public static Plant getPlantById(int id) {
        return allPlants.get(id);
    }
    public static Item getItemById(int id) {
        return allItems.get(id);
    }

//

}
