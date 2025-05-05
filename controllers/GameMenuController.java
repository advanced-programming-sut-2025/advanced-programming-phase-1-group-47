package controllers;
import java.util.Random;
import models.App;
import models.NPC;
import models.Result;
import models.buildings.Building;
import models.enums.Season;
import models.things.Item;

public class GameMenuController {

    public Result<String> exit() {
        return null;
    }
    public Result <String> changeMenu() {
        return null;
    }
    public Result<String> showMenu() {
        return null;
    }
    public Result<String> SetUpNextDay() {
        return null;
    }
    public Result<String> crowAttack() {
        return null;
    }
    public Result<String> Build(Building building) {
        return null;
    }
    public Result<String> BuyAnimal() {
        return null;
    }
    public Result<String> EatFood() {
        return null;
    }
    public Result<String> TalkToNPC(String npcname) {
        Random rand = new Random();
        int randomNumber = rand.nextInt(5);
        if(App.getCurrentGame().getTime().getSeason().equals(Season.SUMMER))
            randomNumber+=5;
        if(App.getCurrentGame().getTime().getSeason().equals(Season.FALL))
            randomNumber+=10;
        if(App.getCurrentGame().getTime().getSeason().equals(Season.WINTER))
            randomNumber+=15;
        for(NPC npc : App.getCurrentGame().getNpcs()){
            if(npc.getName().equals(npcname)){
                npc.addFriendship(20 , App.getCurrentGame().getCurrentPlayer());
                return new Result(true , npc.getResponses().get(randomNumber));
            }
        }
        return new Result(false , "No NPC found with that name!");
        
    }
    public Result<String> GiveGiftToNPC(String npcName , String giftName) {
        for (NPC npc : App.getCurrentGame().getNpcs()){
            if(npc.getName().equalsIgnoreCase(npcName)) {
                for(Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                    if(item.getName().equalsIgnoreCase(giftName)){
                        App.getCurrentGame().getCurrentPlayer().getInvetory().removeItem(item);
                        for(Item favitem : npc.getFavorites()) {
                            if(favitem.getItemID() == item.getItemID() || favitem.getItemID() == item.getParentItemID()){
                                npc.addFriendship(200, App.getCurrentGame().getCurrentPlayer());
                                return new Result<>(true, "Thanks! I love this Gift!");                                
                            }
                        }
                        npc.addFriendship(50, App.getCurrentGame().getCurrentPlayer());
                        return new Result<>(true, "Thanks!");
                    }
                }
                return new Result<>(false , "You Don't have that Item!");
            }
        }
        return new Result<>(false , "NPC name incorrect");
    }
    public Result<String> FinishQuest(int QuestIndex) {

        return null;
    }
}
