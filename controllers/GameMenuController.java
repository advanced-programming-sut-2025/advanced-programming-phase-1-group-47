package controllers;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import models.*;
import models.buildings.Building;
import models.enums.Season;
import models.enums.TileType;
import models.things.Item;
import models.things.relations.Gift;
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
    public Result<String> talkToPlayer(String username , String messege) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equals(username)) {
                if(!isPlayerNear(player))
                    return new Result<>(false, "Player too far away!");
                if(!player.GetHasTalkedToPlayer(App.getCurrentGame().getCurrentPlayer())){
                    player.addFriendshipXP(20, App.getCurrentGame().getCurrentPlayer());
                    App.getCurrentGame().getCurrentPlayer().addFriendshipXP(20, player);
                }
                messege = App.getCurrentGame().getCurrentPlayer().getUsername() + " : " + messege;
                player.addMessegeToTalkHistory(App.getCurrentGame().getCurrentPlayer(), messege);
                App.getCurrentGame().getCurrentPlayer().addMessegeToTalkHistory(player, messege);
                player.setHasBeenTalkedTo(App.getCurrentGame().getCurrentPlayer(), true);
                App.getCurrentGame().getCurrentPlayer().setHasBeenTalkedTo(player, true);
                return new Result<>(true, messege);
            }
        }
        return new Result<>(false, "can't find player username!");
    }
    public Result<String> giveGiftToPlayer(String username , String itemName , String itemAmount) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equals(username)) {
                if(player.getFriendshipLevel().get(App.getCurrentGame().getCurrentPlayer()) == 0)
                    return new Result<>(false, "You don't know that player enough to send Gifts!");
                if(!isPlayerNear(player))
                    return new Result<>(false, "Player too far away!");
                boolean validAmount = false;
                int amount = 0;
                try {
                    amount =Integer.parseInt(itemAmount);
                    validAmount = true;
                } catch (NumberFormatException e) {
                    validAmount = false;
                }
                if(!validAmount)
                    return new Result<>(false, "item amount invalid");
                for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                    if(item.getName().equalsIgnoreCase(itemName)) {
                        if(item.getAmount() < amount)
                            return new Result<>(false, "You Don't have enough of that Item!");
                        Item giftedItem = new Item(item , amount);
                        item.reduceAmount(amount);
                        if(item.getAmount() == 0)
                            App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().remove(item);
                        player.addGiftToPendingGifts(App.getCurrentGame().getCurrentPlayer(), new Gift(giftedItem, App.getGiftIdCounter()));
                        App.addGiftIdCounter();
                        return new Result<>(true, "Gift Given!");
                    }
                }
                return new Result<>(false, "You Don't Have That Item!");
            }
        }
        return new Result<>(false, "can't find player username!");
    }
    public Result<String> hugPlayer(String username) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equals(username)) {
                if(!isPlayerNear(player))
                    return new Result<>(false, "Player too far away!");
                if(!player.GetHasHuggedPlayer(App.getCurrentGame().getCurrentPlayer())){
                    player.addFriendshipXP(60, App.getCurrentGame().getCurrentPlayer());
                    App.getCurrentGame().getCurrentPlayer().addFriendshipXP(60, player);
                }
                player.setHasHuggedPlayer(App.getCurrentGame().getCurrentPlayer(), true);
                App.getCurrentGame().getCurrentPlayer().setHasHuggedPlayer(player, true);
                return new Result<>(true, "You hugged " + username + "!");
            }
        }
        return new Result<>(false, "can't find player username!");
    }
    private boolean isPlayerNear(Player player) {
        int dx = Math.abs(player.getCoordinates().getX() - App.getCurrentGame().getCurrentPlayer().getCoordinates().getX());
        int dy = Math.abs(player.getCoordinates().getY() - App.getCurrentGame().getCurrentPlayer().getCoordinates().getY());
        return dx < 2 && dy < 2;
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
    public Result<String> handleNewGame(Matcher matcher, Scanner scanner) {
        String player1 = matcher.group("player1");
        String player2 = matcher.group("player2");
        String player3 = matcher.group("player3");

        Player p1 = App.findPlayer(player1);
        Player p2 = App.findPlayer(player2);
        Player p3 = App.findPlayer(player3);
        String[] farmNames = new String[4];
        for(int i = 0; i < 4; i++){
            String input = scanner.nextLine();
            if ((matcher = models.enums.commands.GameMenu.gamemap.getMatcher(input)) != null) {
                farmNames[i] = matcher.group("map_number");
            }
        }

        Game newGame = new Game(new Map(farmNames), p1, p2, p3);
        App.setCurrentGame(newGame);
        TileType lastTileType = TileType.COTTAGE;
        App.setCurrentGame(newGame);
        return new Result<>(true, "New game started with players: " + player1 + ", " + player2 + ", " + player3);
    }

    public Result<String> FinishQuest(int QuestIndex) {
        for (NPC npc : App.getCurrentGame().getNpcs()) {
            if (npc.getQuest1().getQuestID() == QuestIndex) {
                if(!isNPCHere(npc))
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest1() , npc);
                
            }
            if (npc.getQuest2().getQuestID() == QuestIndex) {
                if(!isNPCHere(npc))
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest2() , npc);
            }
            if (npc.getQuest3().getQuestID() == QuestIndex) {
                if(!isNPCHere(npc))
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest3() , npc);
            }
            
        }
        return new Result<>(false , "invalid Quest index");
    }

    public Result<String> listQuests() {
        StringBuilder output = new StringBuilder();
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
    
        for (NPC npc : App.getCurrentGame().getNpcs()) {
            Quest[] quests = {npc.getQuest1(), npc.getQuest2(), npc.getQuest3()};
    
            for (Quest quest : quests) {
                if (quest == null) continue;
                Boolean isActive = quest.getIsActive().get(currentPlayer);
                if (isActive != null && isActive) {
                    output.append(quest.getQuestID())
                          .append(" :For ").append(npc.getName())
                          .append(" Items needed : ")
                          .append(quest.getRequiermentItems().getName())
                          .append(" ").append(quest.getRequiermentItems().getAmount())
                          .append(" :The Rewards : ")
                          .append(quest.getRewards().getName())
                          .append(" ").append(quest.getRewards().getAmount())
                          .append(" And the Reward Money Of :")
                          .append(quest.getRewardMoney())
                          .append("\n");
                }
            }
        }
    
        return new Result<>(true, output.toString());
    }
    
    //for finishquest
    private boolean isNPCHere(NPC npc) {
        int dx = Math.abs(npc.getCoordinates().getX() - App.getCurrentGame().getCurrentPlayer().getCoordinates().getX());
        int dy = Math.abs(npc.getCoordinates().getY() - App.getCurrentGame().getCurrentPlayer().getCoordinates().getY());
        return dx < 2 && dy < 2;
    }
    private Result<String> finishQuest2(Quest quest , NPC npc) {
        if(quest.isIsDone())
            return new Result<>(false , "Quest Already Done!");
        if(!quest.getIsActive().get(App.getCurrentGame().getCurrentPlayer()))
            return new Result<>(false , "You don't have access to that Quest yet!");
        for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.questEquals(quest.getRequiermentItems())) {
                /*check if item is giftable
                *TODO
                *TODO
                *check if item is giftable
                */
                item.reduceAmount(quest.getRequiermentItems().getAmount());
                if(item.getAmount() == 0)
                    App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().remove(item);
                if(quest.getRewards().getItemID() == 201)
                   npc.addFriendship(200, App.getCurrentGame().getCurrentPlayer()); 
                else
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