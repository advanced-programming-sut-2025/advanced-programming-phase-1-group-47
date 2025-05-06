package models.NPCs;

import java.util.ArrayList;
import models.NPC;
import models.things.Item;
import models.things.relations.Quest;

public class Leah {
    private static Leah instance = new Leah();
    private Leah() {}
    public static Leah getInstance() {
        return instance;
    }
    public NPC leahBuilder(){
        ArrayList<Item> favorites = favoriteBuilder();
        ArrayList<Item> possibleGifts = giftBuilder();
        ArrayList<String> responses = responseBuilder();
        Quest quest1 = questBuilder1();
        Quest quest2 = questBuilder2();
        Quest quest3 = questBuilder3();

        
        NPC abigail = new NPC("Leah" , "Artist" , quest1 , quest2 , quest3 , favorites , responses , possibleGifts , 4);
        return  abigail;
    }
    private ArrayList<Item> favoriteBuilder() {
        ArrayList<Item> favorites = new ArrayList<>();
        favorites.add(new Item("Salad", 35, 110, 0, 1));
        favorites.add(new Item("Grape", 50, 80, 49, 1));
        favorites.add(new Item("WINE", 34, 0, 0, 1));
        return favorites;
    }
    private ArrayList<Item> giftBuilder() {
        ArrayList<Item> possibleGifts = new ArrayList<>();
        possibleGifts.add(new Item("Salad", 35, 110, 0, 1));
        possibleGifts.add(new Item("Pumpkin Pie", 4, 385, 0, 1));
        possibleGifts.add(new Item("Grape", 50, 80, 49, 1));
        return possibleGifts;
    }
    private Quest questBuilder1() {
        Item requieredItems = new Item("HardWood", 46, 15, 0, 10);
        Item rewards = new Item("null item", 0, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 500 , 7);
        return quest;
    }
    private Quest questBuilder2() {
        Item requieredItems = new Item("Salmon", 151, 75, 0, 1);
        Item rewards = new Item("Salmon Dinner RECIPE", 100, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 0 , 8);
        return quest;
    }
    private Quest questBuilder3() {
        Item requieredItems = new Item("Wood", 36, 2, 0, 200);
        Item rewards = new Item("Deluxe Scarecrow", 41, 0, 0, 3);
        Quest quest = new Quest(requieredItems, rewards , 0 , 9);
        return quest;
    }
 
    private ArrayList<String> responseBuilder() {
        ArrayList<String> responses = new ArrayList<>();
        //RESPONSE SETUP
        //normal(spring)
        responses.add("“Hi. Oh, you want to talk? The landscape around here gives me a lot of ideas. The terrain is almost like a sculpture itself.");
        responses.add("I don't make art for money. It's just an urge that I have. Farming seems like a very rewarding profession. You get to create delicious food for everyone! You're probably busy. Sorry.”");
        responses.add("“Hello, neighbor. We both live outside of town. Does that mean something?”");
        responses.add("“It's simpler to be friends with the trees. They don't have much to say.”");
        responses.add("“I love to decorate for the different seasons.”");
        //summer
        responses.add("“You can tell it's summer by the sweet smell of nectar wafting through the air!”");
        responses.add("“I found some wild fruits this morning! Sorry, I don't have any left. Keep looking, I'm sure you'll find something.”");
        responses.add("“The sound of farm animals is great, isn't it?”");
        responses.add("“The flowing water keeps my house a little bit cooler in summer. I can't tolerate heat very well.”");
        responses.add("“What should I do on such a warm, lazy day? *sigh* Any ideas?”");
        //fall
        responses.add("“How quickly the seasons change! It's shocking.”");
        responses.add("“I wonder if I could make a collage out of dried leaves? They're just so colorful... I can't help but think of the potential for art projects.”");
        responses.add("“Decorations accentuate the feeling of the season. I think it's a worthwhile tradition.”");
        responses.add("“There's a lot of good places to walk around here. It feels good to stretch your legs.”");
        responses.add("“It's so nice to live by the river. I fall asleep to the soothing sound of water every night.”");
        //winter
        responses.add("“I love the way everything looks when it's covered with fresh snow.”");
        responses.add("“It gets pretty cold in my little cabin. I just snuggle up under a huge blanket, and I'm okay.”");
        responses.add("“I saw a snow rabbit early this morning! They're rare, aren't they?”");
        responses.add("“Time seems to move slower here. When I lived in the city, the year went by so fast.”");
        responses.add("“Do you ever take a whole day off? It's a refreshing break.”");
        
        return responses;
    }
}
