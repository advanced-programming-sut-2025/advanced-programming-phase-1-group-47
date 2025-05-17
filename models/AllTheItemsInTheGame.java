package models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import models.enums.*;
import models.things.Item;
import models.things.products.Product;
import models.things.tools.Axe;
import models.things.tools.FishingPole;
import models.things.tools.Hoe;
import models.things.tools.Pickaxe;
import models.things.tools.Scythe;
import models.things.tools.Type;
import models.things.tools.WateringCan;

public class AllTheItemsInTheGame {

    public static final Map<Integer, Plant> allPlants;
    public static final Map<Integer, Item> allItems;
    public static final Map<Integer, Animal> allAnimals;

    static {
        // اول allPlants را مقداردهی می‌کنیم
        Map<Integer, Plant> tempPlants = new HashMap<>();
        Map<Integer, Animal> tempAnimal = new HashMap<>();
        tempPlants.put(302, new Plant(302, new Point(0, 0), "Blue Jazz", 50, true, 45, 20, "Jazz Seeds", 0, 0, 7, Season.SPRING, new int[]{1, 2, 2, 2}, false, false, false,0));
        tempPlants.put(303, new Plant(303, new Point(0, 0), "Carrot", 35, true, 75, 33, "Carrot Seeds", 0, 0, 3, Season.SPRING, new int[]{1, 1, 1}, false, false, false,0));
        tempPlants.put(304, new Plant(304, new Point(0, 0), "Cauliflower", 175, true, 75, 33, "Cauliflower Seeds", 0, 0, 12, Season.SPRING, new int[]{1, 2, 4, 4, 1}, false, true, false,0));
        tempPlants.put(305, new Plant(305, new Point(0, 0), "Coffee Bean", 15, false, -1, -1, "Coffee Bean", 2, 0, 10, Season.SPRING_SUMMER, new int[]{1, 2, 2, 3, 2}, true, false, false,2));
        tempPlants.put(306, new Plant(306, new Point(0, 0), "Garlic", 60, true, 20, 9, "Garlic Seeds", 0, 0, 4, Season.SPRING, new int[]{1, 1, 1, 1}, false, false, false,0));
        tempPlants.put(307, new Plant(307, new Point(0, 0), "Green Bean", 40, true, 25, 11, "Bean Starter", 3, 0, 10, Season.SPRING, new int[]{1, 1, 1, 3, 4}, true, false, false,3));
        tempPlants.put(308, new Plant(308, new Point(0, 0), "Kale", 110, true, 50, 22, "Kale Seeds", 0, 0, 6, Season.SPRING, new int[]{1, 2, 2, 1}, false, false, false,0));
        tempPlants.put(309, new Plant(309, new Point(0, 0), "Parsnip", 35, true, 25, 11, "Parsnip Seeds", 0, 0, 4, Season.SPRING, new int[]{1, 1, 1, 1}, false, false, false,0));
        tempPlants.put(310, new Plant(310, new Point(0, 0), "Potato", 80, true, 25, 11, "Potato Seeds", 0, 0, 6, Season.SPRING, new int[]{1, 1, 1, 2, 1}, false, false, false,0));
        tempPlants.put(311, new Plant(311, new Point(0, 0), "Rhubarb", 220, false, -1, -1, "Rhubarb Seeds", 0, 0, 13, Season.SPRING, new int[]{2, 2, 2, 3, 4}, false, false, true,0));
        tempPlants.put(312, new Plant(312, new Point(0, 0), "Strawberry", 120, true, 50, 22, "Strawberry Seeds", 0, 0, 8, Season.SPRING, new int[]{1, 1, 2, 2, 2}, true, false, true,4));
        tempPlants.put(313, new Plant(313, new Point(0, 0), "Tulip", 30, true, 45, 20, "Tulip Bulb", 0, 0, 6, Season.SPRING, new int[]{1, 1, 2, 2}, false, false, false,0));
        tempPlants.put(314, new Plant(314, new Point(0, 0), "Unmilled Rice", 30, true, 3, 1, "Rice Shoot", 0, 0, 8, Season.SPRING, new int[]{1, 2, 2, 3}, false, false, false,0));
        tempPlants.put(315, new Plant(315, new Point(0, 0), "Blueberry", 50, true, 25, 11, "Blueberry Seeds", 0, 0, 13, Season.SUMMER, new int[]{1, 3, 3, 4, 2}, true, false, true,4));
        tempPlants.put(316, new Plant(316, new Point(0, 0), "Corn", 50, true, 25, 11, "Corn Seeds", 0, 0, 14, Season.SUMMER_FALL, new int[]{2, 3, 3, 3, 3}, true, false, false,4));
        tempPlants.put(317, new Plant(317, new Point(0, 0), "Hops", 25, true, 45, 20, "Hops Starter", 0, 0, 11, Season.SUMMER, new int[]{1, 1, 2, 3, 4}, true, false, false,1));
        tempPlants.put(318, new Plant(318, new Point(0, 0), "Hot Pepper", 40, true, 13, 5, "Pepper Seeds", 0, 0, 5, Season.SUMMER, new int[]{1, 1, 1, 1, 1}, true, false, true,3));
        tempPlants.put(319, new Plant(319, new Point(0, 0), "Melon", 250, true, 113, 50, "Melon Seeds", 0, 0, 12, Season.SUMMER, new int[]{1, 2, 3, 3, 3}, false, true, true,0));
        tempPlants.put(320, new Plant(320, new Point(0, 0), "Poppy", 140, true, 45, 20, "Poppy Seeds", 0, 0, 7, Season.SUMMER, new int[]{1, 2, 2, 2}, false, false, false,0));
        tempPlants.put(321, new Plant(321, new Point(0, 0), "Radish", 90, true, 45, 20, "Radish Seeds", 0, 0, 6, Season.SUMMER, new int[]{2, 1, 2, 1}, false, false, false,0));
        tempPlants.put(322, new Plant(322, new Point(0, 0), "Red Cabbage", 260, true, 75, 33, "Red Cabbage Seeds", 0, 0, 9, Season.SUMMER, new int[]{2, 1, 2, 2, 2}, false, false, false,0));
        tempPlants.put(323, new Plant(323, new Point(0, 0), "Starfruit", 750, true, 125, 56, "Starfruit Seeds", 0, 0, 13, Season.SUMMER, new int[]{2, 3, 2, 3, 3}, false, false, true,0));
        tempPlants.put(324, new Plant(324, new Point(0, 0), "Summer Spangle", 90, true, 45, 20, "Spangle Seeds", 0, 0, 8, Season.SUMMER, new int[]{1, 2, 3, 1}, false, false, false,0));
        tempPlants.put(325, new Plant(325, new Point(0, 0), "Summer Squash", 45, true, 63, 28, "Summer Squash Seeds", 0, 0, 6, Season.SUMMER, new int[]{1, 1, 1, 2, 1}, true, false, false,3));
        tempPlants.put(326, new Plant(326, new Point(0, 0), "Sunflower", 80, true, 45, 20, "Sunflower Seeds", 0, 0, 8, Season.SUMMER_FALL, new int[]{1, 2, 3, 2}, false, false, false,0));
        tempPlants.put(327, new Plant(327, new Point(0, 0), "Tomato", 60, true, 20, 9, "Tomato Seeds", 0, 0, 11, Season.SUMMER, new int[]{2, 2, 2, 2, 3}, true, false, false,4));
        tempPlants.put(328, new Plant(328, new Point(0, 0), "Wheat", 25, false, -1, -1, "Wheat Seeds", 0, 0, 4, Season.SUMMER_FALL, new int[]{1, 1, 1, 1}, false, false, false,0));
        tempPlants.put(329, new Plant(329, new Point(0, 0), "Amaranth", 150, true, 50, 22, "Amaranth Seeds", 0, 0, 7, Season.FALL, new int[]{1, 2, 2, 2}, false, false, false,0));
        tempPlants.put(330, new Plant(330, new Point(0, 0), "Artichoke", 160, true, 30, 13, "Artichoke Seeds", 0, 0, 8, Season.FALL, new int[]{2, 2, 1, 2, 1}, false, false, false,0));
        tempPlants.put(331, new Plant(331, new Point(0, 0), "Beet", 100, true, 30, 13, "Beet Seeds", 0, 0, 6, Season.FALL, new int[]{1, 1, 2, 2}, false, false, false,0));
        tempPlants.put(332, new Plant(332, new Point(0, 0), "Bok Choy", 80, true, 25, 11, "Bok Choy Seeds", 0, 0, 4, Season.FALL, new int[]{1, 1, 1, 1}, false, false, false,0));
        tempPlants.put(333, new Plant(333, new Point(0, 0), "Broccoli", 70, true, 63, 28, "Broccoli Seeds", 0, 0, 8, Season.FALL, new int[]{2, 2, 2, 2}, true, false, false,4));
        tempPlants.put(334, new Plant(334, new Point(0, 0), "Cranberries", 75, true, 38, 17, "Cranberry Seeds", 0, 0, 7, Season.FALL, new int[]{1, 2, 1, 1, 2}, true, false, true,5));
        tempPlants.put(335, new Plant(335, new Point(0, 0), "Eggplant", 60, true, 20, 9, "Eggplant Seeds", 0, 0, 5, Season.FALL, new int[]{1, 1, 1, 1}, true, false, false,5));
        tempPlants.put(336, new Plant(336, new Point(0, 0), "Fairy Rose", 290, true, 45, 20, "Fairy Seeds", 0, 0, 12, Season.FALL, new int[]{1, 4, 4, 3}, false, false, false,0));
        tempPlants.put(337, new Plant(337, new Point(0, 0), "Grape", 80, true, 38, 17, "Grape Starter", 0, 0, 10, Season.FALL, new int[]{1, 1, 2, 3, 3}, true, false, true,3));
        tempPlants.put(338, new Plant(338, new Point(0, 0), "Pumpkin", 320, false, -1, -1, "Pumpkin Seeds", 0, 0, 13, Season.FALL, new int[]{1, 2, 3, 4, 3}, false,true, false,0));
        tempPlants.put(339, new Plant(339, new Point(0, 0), "Yam", 160, true, 45, 20, "Yam Seeds", 0, 0, 10, Season.FALL, new int[]{1, 3, 3, 3}, false, false, false,0));
        tempPlants.put(340, new Plant(340, new Point(0, 0), "Sweet Gem Berry", 3000, false, -1, -1, "Rare Seed", 0, 0, 24, Season.FALL, new int[]{2, 4, 6, 6, 6}, false, false, false,0));
        tempPlants.put(341, new Plant(341, new Point(0, 0), "Powdermelon", 60, true, 63, 28, "Powdermelon Seeds", 0, 0, 7, Season.WINTER, new int[]{1, 2, 1, 2, 1}, false, true, true,0));
        tempPlants.put(342, new Plant(342, new Point(0, 0), "Ancient Fruit", 550, false, -1, -1, "Ancient Seeds", 0, 0, 28, Season.OTHER_THAN_WINER, new int[]{2, 7, 7, 7, 5}, true, false, true,7));
        tempPlants.put(343, new Tree(343, new Point(0, 0), "Apricot Tree", 59, true, 38, 17, "Apricot Sapling", 0, 0, 28, Season.SPRING, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(344, new Tree(344, new Point(0, 0), "Cherry Tree", 80, true, 38, 17, "Cherry Sapling", 0, 0, 28, Season.SPRING, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(345, new Tree(345, new Point(0, 0), "Banana Tree", 150, true, 75, 33, "Banana Sapling", 0, 0, 28, Season.SUMMER, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(346, new Tree(346, new Point(0, 0), "Mango Tree", 130, true, 100, 45, "Mango Sapling", 0, 0, 28, Season.SUMMER, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(347, new Tree(347, new Point(0, 0), "Orange Tree", 100, true, 38, 17, "Orange Sapling", 0, 0, 28, Season.SUMMER, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(348, new Tree(348, new Point(0, 0), "Peach Tree", 140, true, 38, 17, "Peach Sapling", 0, 0, 28, Season.SUMMER, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(349, new Tree(349, new Point(0, 0), "Apple Tree", 100, true, 38, 17, "Apple Sapling", 0, 0, 28, Season.FALL, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(350, new Tree(350, new Point(0, 0), "Pomegranate Tree", 140, true, 38, 17, "Pomegranate Sapling", 0, 0, 28, Season.FALL, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(351, new Tree(351, new Point(0, 0), "Oak Tree", 150, false, 0, 0, "Acorns", 0, 0, 28, Season.SPECIAL, new int[]{7, 7, 7, 7}, true, false, false, 7, 7));
        tempPlants.put(352, new Tree(352, new Point(0, 0), "Maple Tree", 200, false, 0, 0, "Maple Seeds", 0, 0, 28, Season.SPECIAL, new int[]{7, 7, 7, 7}, true, false, false, 9, 9));
        tempPlants.put(353, new Tree(353, new Point(0, 0), "Pine Tree", 100, false, 0, 0, "Pine Cones", 0, 0, 28, Season.SPECIAL, new int[]{7, 7, 7, 7}, true, false, false, 5, 5));
        tempPlants.put(354, new Tree(354, new Point(0, 0), "Mahogany Tree", 2, true, -2, 0, "Mahogany Seeds", 0, 0, 28, Season.SPECIAL, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(355, new Tree(355, new Point(0, 0), "Mushroom Tree", 40, true, 38, 17, "Mushroom Tree Seeds", 0, 0, 28, Season.SPECIAL, new int[]{7, 7, 7, 7}, true, false, true, 1, 1));
        tempPlants.put(356, new Tree(356, new Point(0, 0), "Mystic Tree", 1000, true, 500, 225, "Mystic Tree Seeds", 0, 0, 28, Season.SPECIAL, new int[]{7, 7, 7, 7}, true, false, true, 7, 7));
        tempPlants.put(357, new foragingCrop(new Point(0, 0), 357, "Common Mushroom", Season.SPECIAL, 40, 38, 30));
        tempPlants.put(358, new foragingCrop(new Point(0, 0), 358, "Daffodil", Season.SPRING, 30, 0, 30));
        tempPlants.put(359, new foragingCrop(new Point(0, 0), 359, "Dandelion", Season.SPRING, 40, 25, 30));
        tempPlants.put(360, new foragingCrop(new Point(0, 0), 360, "Leek", Season.SPRING, 60, 40, 30));
        tempPlants.put(361, new foragingCrop(new Point(0, 0), 361, "Morel", Season.SPRING, 150, 20, 30));
        tempPlants.put(362, new foragingCrop(new Point(0, 0), 362, "Salmonberry", Season.SPRING, 5, 25, 30));
        tempPlants.put(363, new foragingCrop(new Point(0, 0), 363, "Spring Onion", Season.SPRING, 8, 13, 30));
        tempPlants.put(364, new foragingCrop(new Point(0, 0), 364, "Wild Horseradish", Season.SPRING, 50, 13, 30));
        tempPlants.put(365, new foragingCrop(new Point(0, 0), 365, "Fiddlehead Fern", Season.SUMMER, 90, 25, 30));
        tempPlants.put(366, new foragingCrop(new Point(0, 0), 366, "Grape", Season.SUMMER, 80, 38, 30));
        tempPlants.put(367, new foragingCrop(new Point(0, 0), 367, "Red Mushroom", Season.SUMMER, 75, -50, 30));
        tempPlants.put(368, new foragingCrop(new Point(0, 0), 368, "Spice Berry", Season.SUMMER, 80, 25, 30));
        tempPlants.put(369, new foragingCrop(new Point(0, 0), 369, "Sweet Pea", Season.SUMMER, 50, 0, 30));
        tempPlants.put(370, new foragingCrop(new Point(0, 0), 370, "Blackberry", Season.FALL, 25, 25, 30));
        tempPlants.put(371, new foragingCrop(new Point(0, 0), 371, "Chanterelle", Season.FALL, 160, 75, 30));
        tempPlants.put(372, new foragingCrop(new Point(0, 0), 372, "Hazelnut", Season.FALL, 40, 38, 30));
        tempPlants.put(373, new foragingCrop(new Point(0, 0), 373, "Purple Mushroom", Season.FALL, 90, 30, 30));
        tempPlants.put(374, new foragingCrop(new Point(0, 0), 374, "Wild Plum", Season.FALL, 80, 25, 30));
        tempPlants.put(375, new foragingCrop(new Point(0, 0), 375, "Crocus", Season.WINTER, 60, 0, 30));
        tempPlants.put(376, new foragingCrop(new Point(0, 0), 376, "Crystal Fruit", Season.WINTER, 150, 63, 30));
        tempPlants.put(377, new foragingCrop(new Point(0, 0), 377, "Holly", Season.WINTER, 80, -37, 30));
        tempPlants.put(378, new foragingCrop(new Point(0, 0), 378, "Snow Yam", Season.WINTER, 100, 30, 30));
        tempPlants.put(379, new foragingCrop(new Point(0, 0), 379, "Winter Root", Season.WINTER, 70, 25, 30));
        allPlants = Collections.unmodifiableMap(tempPlants);

        tempAnimal.put(AnimalType.DINOSAUR.getItemId(), new Animal(AnimalType.DINOSAUR));
        tempAnimal.put(AnimalType.HEN.getItemId(), new Animal(AnimalType.HEN));
        tempAnimal.put(AnimalType.DUCK.getItemId(), new Animal(AnimalType.DUCK));
        tempAnimal.put(AnimalType.PIG.getItemId(), new Animal(AnimalType.PIG));
        tempAnimal.put(AnimalType.GOAT.getItemId(), new Animal(AnimalType.GOAT));
        tempAnimal.put(AnimalType.COW.getItemId(), new Animal(AnimalType.COW));
        tempAnimal.put(AnimalType.RABBIT.getItemId(), new Animal(AnimalType.RABBIT));
        tempAnimal.put(AnimalType.SHEEP.getItemId(), new Animal(AnimalType.SHEEP));

        allAnimals = Collections.unmodifiableMap(tempAnimal);
        // 3. ساخت مپ کلی همه آیتم‌ها (حیوان + گیاه)
        Map<Integer, Item> tempItems = new HashMap<>();

        // اضافه کردن همه حیوان‌ها به مپ آیتم‌ها
        tempItems.putAll(allAnimals);
        //اضافه کردن همه پلنت ها
        for (int i = 302; i < 380; i++) {
            Plant p = getPlantById(i);
            if (p != null) {
                tempItems.put(i, p.harvestPlant());
                tempItems.put(i + 100, p.getSeed());
                tempItems.put(i + 300, p.harvestPlant().getDriedFruit());
                tempItems.put(i + 1000,p.harvestPlant().getJuice());
                tempItems.put(i + 1100,p.harvestPlant().getJuice());
            }
        }
        tempItems.put(FarmBuilding.BARN.getId(), new Building(FarmBuilding.BARN));
        tempItems.put(FarmBuilding.BIG_BARN.getId(), new Building(FarmBuilding.BIG_BARN));
        tempItems.put(FarmBuilding.COOP.getId(), new Building(FarmBuilding.COOP));
        tempItems.put(FarmBuilding.BIG_COOP.getId(), new Building(FarmBuilding.BIG_COOP));
        tempItems.put(FarmBuilding.DELUXE_BARN.getId(), new Building(FarmBuilding.DELUXE_BARN));
        tempItems.put(FarmBuilding.DELUXE_COOP.getId(), new Building(FarmBuilding.DELUXE_BARN));
        tempItems.put(FarmBuilding.SHIPPING_BIN.getId(), new Building(FarmBuilding.SHIPPING_BIN));
        tempItems.put(FarmBuilding.SHIPPING_BIN.getId(), new Building(FarmBuilding.SHIPPING_BIN));
        tempItems.put(FarmBuilding.WELL.getId(), new Building(FarmBuilding.WELL));

        //Fishes 1050 , 1069
        tempItems.put(FishType.SALMON.getId(), new Fish(FishType.SALMON));
        tempItems.put(FishType.SARDINE.getId(), new Fish(FishType.SARDINE));
        tempItems.put(FishType.SHAD.getId(), new Fish(FishType.SHAD));
        tempItems.put(FishType.BLUE_DISCUS.getId(), new Fish(FishType.BLUE_DISCUS));
        tempItems.put(FishType.MIDNIGHT_CARP.getId(), new Fish(FishType.MIDNIGHT_CARP));
        tempItems.put(FishType.SQUID.getId(), new Fish(FishType.SQUID));
        tempItems.put(FishType.TUNA.getId(), new Fish(FishType.TUNA));
        tempItems.put(FishType.PERCH.getId(), new Fish(FishType.PERCH));
        tempItems.put(FishType.FLOUNDER.getId(), new Fish(FishType.FLOUNDER));
        tempItems.put(FishType.LIONFISH.getId(), new Fish(FishType.LIONFISH));
        tempItems.put(FishType.HERRING.getId(), new Fish(FishType.HERRING));
        tempItems.put(FishType.GHOSTFISH.getId(), new Fish(FishType.GHOSTFISH));
        tempItems.put(FishType.TILAPIA.getId(), new Fish(FishType.TILAPIA));
        tempItems.put(FishType.DORADO.getId(), new Fish(FishType.DORADO));
        tempItems.put(FishType.SUNFISH.getId(), new Fish(FishType.SUNFISH));
        tempItems.put(FishType.RAINBOW_TROUT.getId(), new Fish(FishType.RAINBOW_TROUT));
        tempItems.put(FishType.LEGEND.getId(), new Fish(FishType.LEGEND));
        tempItems.put(FishType.GLACIERFISH.getId(), new Fish(FishType.GLACIERFISH));
        tempItems.put(FishType.ANGLER.getId(), new Fish(FishType.ANGLER));
        tempItems.put(FishType.CRIMSONFISH.getId(), new Fish(FishType.CRIMSONFISH));

        tempItems.put(0,  new Item("null",0,0,0,1));
        tempItems.put(2,  new Item("Stone",2,20,0,1));
        tempItems.put(3,  new Item("Wool",3,20,0,1));
        tempItems.put(4,  new Item("BARN",4,20,0,1));
        tempItems.put(5,  new Item("COOP",5,20,0,1));
        tempItems.put(7,  new Item("BIG BARN",7,20,0,1));
        tempItems.put(8,  new Item("BIG COOP",8,20,0,1));
        tempItems.put(9,  new Item("DELUXE BARN",9,20,0,1));
        tempItems.put(10, new Item("DELUXE COOP",10,20,0,1));
        tempItems.put(11, new Item("SHIPPING BIN", 11 ,20 , 0 ,1));
        tempItems.put(13, new Item("Raisins", 13, 600, 0, 1));
        tempItems.put(15, new Item("Cheese", 15, 230, 0, 1));
        tempItems.put(16, new Item("Large Cheese", 16, 345, 0, 1));
        tempItems.put(17, new Item("Goat Cheese", 17, 400, 0, 1));
        tempItems.put(18, new Item("Large Goat Cheese", 18, 600, 0, 1));
        tempItems.put(6,  new Item("Coffee",6,150,0,1));
        tempItems.put(21, new Item("Iridium Bar",21,20,0,1));
        tempItems.put(22, new Item("Honey",22,350,0,1));
        tempItems.put(23, new Item("Mead", 23, 300, 0, 1));
        tempItems.put(24, new Item("Joja Cola",24,20,0,1));
        tempItems.put(25, new Item("Grass Starter",25,20,0,1));
        tempItems.put(26, new Item("Beer",26,200,0,1));
        tempItems.put(27, new Item("Sugar",27,2,0,1));
        tempItems.put(28, new Item("Wheat Flour",28,2,0,1));
        tempItems.put(29, new Item("Trout Soup",29,2,0,1));
        tempItems.put(30, new Item("Hay",30,20,0,1));
        tempItems.put(31, new Item("Vinegar", 31, 100, 0, 1));
        tempItems.put(32, new Item("Pale ale", 32, 300, 0, 1));
        tempItems.put(36, new Item("Wood",36,2,0,1));
        tempItems.put(37, new Item("Iron Bar",37,2,0,1));
        tempItems.put(40, new Item("Iridium Sprinkler",40,2,0,1));
        tempItems.put(41, new Item("Deluxe Scarecrow",41,2,0,1));
        tempItems.put(42, new Item("Scarecrow",42,2,0,1));
        tempItems.put(43, new Item("BeeHouse",43,2,0,1));
        tempItems.put(44, new Item("Gold Bar",44,2,0,1));
        tempItems.put(46, new Item("HardWood",46,2,0,1));
        tempItems.put(48, new Item("Rice",48,2,0,1));
        tempItems.put(52, new Axe(Type.REGULAR));
        tempItems.put(53, new Item("BackPack type" , 53 , 2 ,0 , 1));
        tempItems.put(54, new FishingPole(RodType.TRAININGROD));
        tempItems.put(55, new Item("Milk pail",55 ,-1 ,0 , 1));
        tempItems.put(56, new Pickaxe(Type.REGULAR));
        tempItems.put(57, new Scythe());
        tempItems.put(58, new Item("Shears", 58, 2, 0, 1));
       // tempItems.put(59, new  ) nemidanam
        tempItems.put(60, new WateringCan(Type.REGULAR));
        tempItems.put(61, new Hoe(Type.REGULAR));

        tempItems.put(AnimalProductType.HEN_EGG.getId(), new AnimalProduct(AnimalProductType.HEN_EGG));
        tempItems.put(AnimalProductType.HEN_BIG_EGG.getId(), new AnimalProduct(AnimalProductType.HEN_BIG_EGG));
        tempItems.put(AnimalProductType.DUCK_EGG.getId(), new AnimalProduct(AnimalProductType.DUCK_EGG));
        tempItems.put(AnimalProductType.DUCK_FEATHER.getId(), new AnimalProduct(AnimalProductType.DUCK_FEATHER));
        tempItems.put(AnimalProductType.RABBIT_WOOL.getId(), new AnimalProduct(AnimalProductType.RABBIT_WOOL));
        tempItems.put(AnimalProductType.RABBIT_LEG.getId(), new AnimalProduct(AnimalProductType.RABBIT_LEG));
        tempItems.put(AnimalProductType.DINOSAUR_EGG.getId(), new AnimalProduct(AnimalProductType.DINOSAUR_EGG));
        tempItems.put(AnimalProductType.MILK.getId(), new AnimalProduct(AnimalProductType.MILK));
        tempItems.put(AnimalProductType.BIG_MILK.getId(), new AnimalProduct(AnimalProductType.BIG_MILK));
        tempItems.put(AnimalProductType.GOAT_MILK.getId(), new AnimalProduct(AnimalProductType.GOAT_MILK));
        tempItems.put(AnimalProductType.BIG_GOAT_MILK.getId(), new AnimalProduct(AnimalProductType.BIG_GOAT_MILK));
        tempItems.put(AnimalProductType.SHEEP_WOOL.getId(), new AnimalProduct(AnimalProductType.SHEEP_WOOL));
        tempItems.put(AnimalProductType.TRUFFLE.getId(), new AnimalProduct(AnimalProductType.TRUFFLE));


        tempItems.put(151, new Item("Salmon",151,2,0,1));
        tempItems.put(176, new Item("BasicRetainingSoil",176,2,0,1));
        tempItems.put(177, new Item("improvedRetainingSoil",177,2,0,1));
        tempItems.put(178, new Item("deluxeRetainingSoil",178,2,0,1));
        tempItems.put(179, new Item("SpeedGro",179,2,0,1));
        // tempItems.put(151, new Item("Salmon",151,2,0,1));
        // tempItems.put(151, new Item("Salmon",151,2,0,1));
        // tempItems.put(151, new Item("Salmon",151,2,0,1));
        tempItems.put(201, new Item("Friendship Level",201,2,0,1));
        tempItems.put(202, new Item("Bouquet",202,2,0,1));
        tempItems.put(203, new Item("Wedding Ring",203,2,0,1));

        tempItems.put(380, new Item("Quartz",380,2,0,1));
        tempItems.put(381, new Item("Earth Crystal",381,2,0,1));    
        tempItems.put(382, new Item("Frozen Tear",382,2,0,1));    
        tempItems.put(383, new Item("Fire Quartz",383,2,0,1));    
        tempItems.put(384, new Item("Emerald",384,2,0,1));    
        tempItems.put(385, new Item("Aquamarine",385,2,0,1));    
        tempItems.put(386, new Item("Ruby",386,2,0,1));    
        tempItems.put(387, new Item("Amethyst",387,2,0,1));    
        tempItems.put(388, new Item("Topaz",388,2,0,1));    
        tempItems.put(389, new Item("Jade",389,2,0,1));    
        tempItems.put(390, new Item("Diamond",390,2,0,1));    
        tempItems.put(391, new Item("Prismatic Shard",391,2,0,1));    
        tempItems.put(392, new Item("Copper Ore",392,2,0,1));    
        tempItems.put(393, new Item("Iron Ore",393,2,0,1));    
        tempItems.put(394, new Item("Gold Ore",394,2,0,1));    
        tempItems.put(395, new Item("Iridium Ore",395,2,0,1));    
        tempItems.put(396, new Item("Coal",396,2,0,1));
        tempItems.put(397, new Item("Mixed seeds", 397, 20 , 0 , 1));   
        tempItems.put(557, new Product("Dried Mushrooms", 557, 325, 0, 1, true, 50, 20, ProductQuality.NORMAL, false, false));
        allItems = Collections.unmodifiableMap(tempItems);
        }

    public static Item getItemById(int id) {
        return allItems.get(id);
    }
    public static Plant getPlantById(int id) {
        return allPlants.get(id);
    }
}
