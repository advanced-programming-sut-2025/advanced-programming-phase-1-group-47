package models.NPCs;

import java.util.ArrayList;
import models.NPC;
import models.Point;
import models.things.Item;
import models.things.relations.Quest;

public class Harvey {
    private static Harvey instance = new Harvey();
    private Harvey() {}
    public static Harvey getInstance() {
        return instance;
    }
    public NPC harveyBuilder(){
        ArrayList<Item> favorites = favoriteBuilder();
        ArrayList<Item> possibleGifts = giftBuilder();
        ArrayList<String> responses = responseBuilder();
        Quest quest1 = questBuilder1();
        Quest quest2 = questBuilder2();
        Quest quest3 = questBuilder3();

        
        NPC abigail = new NPC("Harvey" , "Doctor" , quest1 , quest2 , quest3 , favorites , responses , possibleGifts , 3,
                new Point(86,60));
        return  abigail;
    }
    private ArrayList<Item> favoriteBuilder() {
        ArrayList<Item> favorites = new ArrayList<>();
        favorites.add(new Item("PICKLED", 1201, 0, 0, 1)); //update
        favorites.add(new Item("WINE", 1101, 0, 0, 1)); //update
        favorites.add(new Item("Coffee", 6, 150, 0, 1));
        return favorites;
    }
    private ArrayList<Item> giftBuilder() {
        ArrayList<Item> possibleGifts = new ArrayList<>();
        possibleGifts.add(new Item("Cranberry Wine", 1434, 225, 0, 1)); //update
        possibleGifts.add(new Item("Pickled Potato", 10, 210, 0, 1));
        possibleGifts.add(new Item("Coffee", 6, 150, 0, 1));
        return possibleGifts;
    }
    private Quest questBuilder1() {
        Item requieredItems = new Item("PLANT", 301, 0, 0, 12);
        Item rewards = new Item("null item", 0, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 750 ,4 ,true);
        return quest;
    }
    private Quest questBuilder2() {
        Item requieredItems = new Item("Salmon", 151, 75, 0, 1);
        Item rewards = new Item("Friendship Level", 201, 0, 0, 1);
        Quest quest = new Quest(requieredItems, rewards , 0 , 5,false);
        return quest;
    }
    private Quest questBuilder3() {
        Item requieredItems = new Item("WINE", 34, 0, 0, 1);
        Item rewards = new Item("Salad", 252, 110, 0, 5); //Update
        Quest quest = new Quest(requieredItems, rewards , 0, 6,false);
        return quest;
    }
 















    private ArrayList<String> responseBuilder() {
        ArrayList<String> responses = new ArrayList<>();
        //RESPONSE SETUP
        //normal(spring)
        responses.add("“We sell a few over-the-counter medicines at the clinic... " + 
        "feel free to stop by if you're feeling exhausted. I know that being a farmer is pretty tiring work... don't overdo it!”");
        responses.add("“I feel responsible for the health of this whole community... it's kind of stressful. It's a pretty small community, and I'm fortunate to be able to build a good relationship with my patients.”");
        responses.add("“Feel free to stop by my office if you're ever feeling ill. You're young, though. You'll probably stay healthy without trying.”");
        responses.add("“Hmm... I'm struggling to make ends meet. I don't have enough patients. I guess I should try to get patients from the neighboring towns...”");
        responses.add("“Remember to cover your mouth when you sneeze. Then make sure to wash your hands.”");
        //summer
        responses.add("“It's okay to get a moderate amount of sunlight. Just don't get burnt. Okay. Take care! Stay healthy.”");
        responses.add("“Exercise is important, but don't get too exhausted, or you might end up at my clinic! Make sure and listen to your body.”");
        responses.add("“People don't get sick much during the summer. That's nice, but it also means less business for me.”");
        responses.add("“Care about your Health, Even though in the summer people don't get sick that much”");
        responses.add("“Hello, how is buisness on the farm in the warm weather?”");
        //fall
        responses.add("“Have you been going into the mines, [Player]? It's a dangerous place. I recommend against it.”");
        responses.add("“When you eat certain foods you'll perform better. Eating a 'Pumpkin Pie' will give you the nutrition you need to water and harvest crops better!”");
        responses.add("“It's pumpkin season they're very good for your health.”");
        responses.add("“Profits are rising for both of us in this Weather!”");
        responses.add("*Harvey is busy with his work*");
        //winter
        responses.add("“It's flu season, so you'd better be extra careful. Make sure to wash your hands often.”");
        responses.add("“I hate to say this, but I do make a lot more money during flu season. I guess if people stopped getting sick, I'd be out of business. Don't get the wrong idea! I want people to be healthy... really!”");
        responses.add("Be careful when working on the farm, It's Flu season!");
        responses.add("“Feel free to stop by my office if you think you have the flu,it is very cold outside.”");
        responses.add("*Harvey is busy with his work*");
        
        return responses;
    }
}
