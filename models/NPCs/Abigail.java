package models.NPCs;

import java.util.ArrayList;
import models.NPC;
import models.Point;
import models.things.Item;
import models.things.relations.Quest;
public class Abigail {
    private static Abigail instance = new Abigail();
    private Abigail() {}
    public static Abigail getInstance() {
        return instance;
    }
    public NPC abigailBuilder(){
        ArrayList<Item> favorites = favoriteBuilder();
        ArrayList<Item> possibleGifts = giftBuilder();
        ArrayList<String> responses = responseBuilder();
        Quest quest1 = questBuilder1();
        Quest quest2 = questBuilder2();
        Quest quest3 = questBuilder3();
        NPC abigail = new NPC("abigail" , "Student" , quest1 , quest2 , quest3 , favorites , responses , possibleGifts , 1
         , new Point(58, 59));


        return  abigail;
    }
    private ArrayList<Item> favoriteBuilder() {
        ArrayList<Item> favorites = new ArrayList<>();
        favorites.add(new Item("Stone", 2, 2, 0, 1));
        favorites.add(new Item("Iron Ore", 1, 10, 0, 1));
        favorites.add(new Item("Coffee", 6, 150, 0, 1));
        return favorites;
    }
    private ArrayList<Item> giftBuilder() {
        ArrayList<Item> possibleGifts = new ArrayList<>();
        possibleGifts.add(new Item("Stone", 2, 2, 0, 40));
        possibleGifts.add(new Item("Pumpkin Pie", 4, 385, 0, 1));
        possibleGifts.add(new Item("Coffee", 6, 150, 0, 1));
        return possibleGifts;
    }
    private Quest questBuilder1() {
        Item requieredItems = new Item("Gold Bar", 44, 250, 0, 1);
        Item rewards = new Item("Friendship Level", 201, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 0 , 1 , true );
        return quest;
    }
    private Quest questBuilder2() {
        Item requieredItems = new Item("Pumpkin", 338, 320, 301, 1);
        Item rewards = new Item("null item", 0, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 500 , 2 , false);
        return quest;
    }
    private Quest questBuilder3() {
        Item requieredItems = new Item("Wheat", 328, 25, 301, 50);
        Item rewards = new Item("Iridium Sprinkler", 40, 1000, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 0 , 3 , false);
        return quest;
    }
 
    private ArrayList<String> responseBuilder() {
        ArrayList<String> responses = new ArrayList<>();
        //RESPONSE SETUP
        //normal(spring)
        responses.add("“Oh, hey. Taking a break from work?“");
        responses.add("“Oh, hi! Do you ever hang out at the cemetery? It's a peaceful place to spend some time alone.”");
        responses.add("“Ugh... I'm not in a good mood right now.”");
        responses.add("“Hey. Sorry in advance if I say anything rude. I didn't get much sleep last night. What do you want?”");
        responses.add("“The fresh mountain air is nice on a day like this. I wonder if the frogs will make an appearance soon.”");
        //summer
        responses.add("“My pet guinea pig, David, just hates this hot weather. He's fussy.”");
        responses.add("“The air is so thick with honey and nectar all summer. I almost feel dizzy.”");
        responses.add("“Ew, I hope I don't get a tan this summer.”");
        responses.add("“I'm looking forward to fall... the cool mountain breeze, the swirling red petals, the smell of mushrooms... *sigh*”");
        responses.add("“Do you ever get an urge to go exploring?”");
        //fall
        responses.add("“I need to stretch my legs and get some fresh air today.”");
        responses.add("“The air is so thick with honey and nectar all summer. I almost feel dizzy.”");
        responses.add("“I can't wait to see some pumpkins this year. The spirit's eve festival happens at the end of the season.It's pretty low-key compared to what you'd see in Zuzu City, but for a small town like this it's a lot of fun.”");
        responses.add("“Do you have any scarecrows on your farm?”");
        responses.add("“Are you growing pumpkins on your farm this year? Save one for me!”");
        //winter
        responses.add("“Well, fall is over... But I like winter, too!”");
        responses.add("“It's just too cold to go outside much. But I do enjoy building a snowgoon.”");
        responses.add("“Hi. Is it boring to be a farmer during the winter?”");
        responses.add("“It must be nice not having crops to worry about this time of year.”");
        responses.add("“It's so cold, I wish we had a hot cup of cocoa to share.”");
        
        return responses;
    }
}
