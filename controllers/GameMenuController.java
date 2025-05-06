package controllers;
import java.util.Random;
import models.App;
import models.NPC;
import models.Result;
import models.buildings.Building;
import models.enums.Season;
import models.things.Item;
import models.things.relations.Quest;

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
            if(npc.getName().equalsIgnoreCase(npcname)){
                npc.addFriendship(20 , App.getCurrentGame().getCurrentPlayer());
                return new Result<>(true , npc.getResponses().get(randomNumber));
            }
        }
        return new Result<>(false , "No NPC found with that name!");
        
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
        for (NPC npc : App.getCurrentGame().getNpcs()) {
            if (npc.getQuest1().getQuestID() == QuestIndex) {
                if(!isNPCHere())
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest1());
                
            }
            if (npc.getQuest2().getQuestID() == QuestIndex) {
                if(!isNPCHere())
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest2());
            }
            if (npc.getQuest3().getQuestID() == QuestIndex) {
                if(!isNPCHere())
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest3());
            }
            
        }
        return new Result<>(false , "invalid Quest index");
    }
    //for finishquest
    private boolean isNPCHere() {
        return false; //TODO
    }
    private Result<String> finishQuest2(Quest quest) {
        if(quest.isIsDone())
            return new Result<>(false , "Quest Already Done!");
        if(!quest.getIsActive().get(App.getCurrentGame().getCurrentPlayer()))
            return new Result<>(false , "You don't have access to that Quest yet!");
        for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.questEquals(quest.getRequiermentItems())) {
                item.reduceAmount(quest.getRequiermentItems().getAmount());
                if(item.getAmount() == 0)
                    App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().remove(item);
                App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().add(quest.getRewards());
                App.getCurrentGame().getCurrentPlayer().addMoney(quest.getRewardMoney());
                quest.setIsDone(true);
                return new Result<>(true , "Quest completed");
            }
        }
        return new Result<>(false , "You don't have the quest Item (Or Enough of it)");
    }

    //for finishquest
}