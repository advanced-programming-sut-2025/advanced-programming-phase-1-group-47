package models.NPCs;

import java.util.ArrayList;
import models.NPC;
import models.Point;
import models.things.Item;
import models.things.relations.Quest;

public class Sebastion {
    private static Sebastion instance = new Sebastion();
    private Sebastion() {}
    public static Sebastion getInstance() {
        return instance;
    }
    public NPC sebastionBuilder(){
        ArrayList<Item> favorites = favoriteBuilder();
        ArrayList<Item> possibleGifts = giftBuilder();
        ArrayList<String> responses = responseBuilder();
        Quest quest1 = questBuilder1();
        Quest quest2 = questBuilder2();
        Quest quest3 = questBuilder3();

        NPC abigail = new NPC("sebastian" , "Student" , quest1 , quest2 , quest3 , favorites , responses , possibleGifts , 2,
                new Point(76,48));
        return  abigail;
    }
    private ArrayList<Item> favoriteBuilder() {
        ArrayList<Item> favorites = new ArrayList<>();
        favorites.add(new Item("Wool", 3, 340, 0, 1));
        favorites.add(new Item("Pumpkin Pie", 4, 385, 0, 1));
        favorites.add(new Item("Pizza", 5, 300, 0, 1));
        return favorites;
    }
    private ArrayList<Item> giftBuilder() {
        ArrayList<Item> possibleGifts = new ArrayList<>();
        possibleGifts.add(new Item("Frozen Tear", 47, 75, 0, 1));
        possibleGifts.add(new Item("Pumpkin Pie", 4, 385, 0, 1));
        possibleGifts.add(new Item("Coffee", 6, 150, 0, 1));
        return possibleGifts;
    }
    private Quest questBuilder1() {
        Item requieredItems = new Item("Iron Ore", 1, 10, 0, 50);
        Item rewards = new Item("Diamond", 39, 750, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 0 ,13);
        return quest;
    }
    private Quest questBuilder2() {
        Item requieredItems = new Item("Pumpkin Pie", 4, 385, 0, 1);
        Item rewards = new Item("null item", 0, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 500 ,14);
        return quest;
    }
    private Quest questBuilder3() {
        Item requieredItems = new Item("Stone", 2, 2, 0, 150);
        Item rewards = new Item("Quartz", 38, 25, 0, 50);
        Quest quest = new Quest(requieredItems, rewards , 0 ,15);
        return quest;
    }
 

    private ArrayList<String> responseBuilder() {
        ArrayList<String> responses = new ArrayList<>();
        //RESPONSE SETUP
        //normal(spring)
        responses.add("“Um... need something?”");
        responses.add("“If I just disappeared would it really matter?”");
        responses.add("“I was thinking... people are like stones skipping over the water. Eventually we're going to sink.”");
        responses.add("“What am I going to do today? Probably nothing.”");
        responses.add("“You know, I should be doing something productive right now. I just lose focus too fast... Maybe I should drink more coffee?”");
        //summer
        responses.add("“Everyone is so happy in the sun. I don't get it.”"); //same sebby same
        responses.add("“I'm looking forward to the cold, damp season. I feel more at home.”");
        responses.add("“The temperature only starts to get comfortable at night. It always seems to stay a little cooler by the lake.”");
        responses.add("“Oh, hey. *yawn*”");
        responses.add("“I usually only go outside after dark. Does that sound weird to you?”");
        //fall
        responses.add("“So... it's the big harvest season, isn't it?”");
        responses.add("“Pumpkin spice this... pumpkin spice that... man, I do get sick of these seasonal fads.”");
        responses.add("“*yawn*... what time is it? I think I slept too much last night.”");
        responses.add("“Do you have any scarecrows on your farm?”");
        responses.add("“The sun is coming out less and less these days... wonderful.”");
        //winter
        responses.add("“I'm enjoying this gloomy weather... although I have to say, it's a little cold.”");
        responses.add("“Hey... It's hard to think of new things to talk about, sometimes. Even after you know someone. ...Sorry.”");
        responses.add("“The frogs aren't very happy in winter. Poor little guys.”");
        responses.add("“I wonder what's for dinner tonight... *grumble*”");
        responses.add("“Hey, don't let me stop you from getting your work done. If you aren't busy in winter, I don't mind if you stick around.”");
        
        return responses;
    }
}
