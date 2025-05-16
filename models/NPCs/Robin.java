package models.NPCs;

import java.util.ArrayList;
import models.NPC;
import models.Point;
import models.things.Item;
import models.things.relations.Quest;

public class Robin {
    private static Robin instance = new Robin();
    private Robin() {}
    public static Robin getInstance() {
        return instance;
    }
    public NPC robinBuilder(){
        ArrayList<Item> favorites = favoriteBuilder();
        ArrayList<Item> possibleGifts = giftBuilder();
        ArrayList<String> responses = responseBuilder();
        Quest quest1 = questBuilder1();
        Quest quest2 = questBuilder2();
        Quest quest3 = questBuilder3();

        
        NPC abigail = new NPC("Robin" , "Carpenter" , quest1 , quest2 , quest3 , favorites , responses , possibleGifts , 5,
                new Point(106,58));
        return  abigail;
    }
    private ArrayList<Item> favoriteBuilder() {
        ArrayList<Item> favorites = new ArrayList<>();
        favorites.add(new Item("Wood", 36, 2, 0, 1));
        favorites.add(new Item("Iron Bar", 37, 120, 0, 1));
        favorites.add(new Item("Spaghetti", 47, 120, 0, 1)); //Update 
        return favorites;
    }
    private ArrayList<Item> giftBuilder() {
        ArrayList<Item> possibleGifts = new ArrayList<>();
        possibleGifts.add(new Item("Wood", 36, 2, 0, 50));
        possibleGifts.add(new Item("Iron Bar", 37, 120, 0, 2));
        possibleGifts.add(new Item("Spaghetti", 47, 120, 0, 1)); //Update
        return possibleGifts;
    }
    private Quest questBuilder1() {
        Item requieredItems = new Item("Wood", 36, 2, 0, 80);
        Item rewards = new Item("null item", 0, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 1000 , 10,true);
        return quest;
    }
    private Quest questBuilder2() {
        Item requieredItems = new Item("Iron Bar", 37, 120, 0, 10);
        Item rewards = new Item("Bee House", 43, -1, 0, 3);
        Quest quest = new Quest(requieredItems, rewards , 500 ,11,false);
        return quest;
    }
    private Quest questBuilder3() {
        Item requieredItems = new Item("Wood", 36, 2, 0, 1000);
        Item rewards = new Item("null item", 0, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 25000 ,12,false);
        return quest;
    }
 
    private ArrayList<String> responseBuilder() {
        ArrayList<String> responses = new ArrayList<>();
        //RESPONSE SETUP
        //normal(spring)
        responses.add("Have I told you that I built our house from the ground up? It's definitely been the highlight of my career so far.”\r\n");
        responses.add("“Hey, if you need any materials or blueprints, my shop is the place you're looking for! Plus, your business supports the local economy.”");
        responses.add("“Our house is in such a beautiful area, don't you think? I love the fresh air of the mountains.”");
        responses.add("“I found an ashtray in Sebastian's room, and it smelled really weird. Should I be worried about this?”");
        responses.add("“Sometimes I worry about Sebastian... he doesn't have many friends and doesn't really seem to care about his future very much... " + " I would talk to him about it but he never opens up to me.”");
        //summer
        responses.add("“Wood is a wonderful substance... it's versatile, cheap, strong, and each piece has it's own unique character!”");
        responses.add("“We're pretty insulated from the rest of the world here in Stardew Valley. It has its pros and cons.”\r\n");
        responses.add("“If you need any buildings on your farm upgraded, just ask me!");
        responses.add("“I'm looking forward to fall, I don't really enjoy the heat”");
        responses.add("“It's so warm outside,But i have to get this carpentry project done!”");
        //fall
        responses.add("“You're always welcome to visit us, even if you aren't in the market for a building upgrade! It can get pretty lonely up here in the mountains.”");
        responses.add("“Have you met the wild man that lives behind our house? I guess I don't really mind, as long as he doesn't bother us.”");
        responses.add("“Fall is a great season for some work!”");
        responses.add("*You find robin making a chair, She's very focused*");
        responses.add("“Sometimes i pass out from working too much,I don't mind it though.”");
        //winter
        responses.add("“Hey, it's a perfect time of year to gather wood. Maybe you can collect enough to upgrade your house!");
        responses.add("“It's just too cold to go outside much.”");
        responses.add("“My parents were bewildered when I told them I wanted to be a carpenter. They were pretty old-fashioned.”");
        responses.add("“Our house is in such a beautiful area, don't you think? Everything looks still after a fresh snow.”");
        responses.add("“I like the snow, It makes my work look so much prettier!”");
        
        return responses;
    }
}
