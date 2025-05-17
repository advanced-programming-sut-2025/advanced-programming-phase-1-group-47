package controllers;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import models.things.machines.Operation;
import models.*;
import models.Map;
import models.NPCs.*;
import models.Shops.Blacksmith;
import models.Shops.Carpenter;
import models.Shops.FishShop;
import models.Shops.JojaMart;
import models.Shops.MarniesRanch;
import models.Shops.TheSaloon;
import models.Shops.pierres;
import models.enums.Gender;
import models.enums.Menu;
import models.enums.ProductQuality;
import models.enums.Season;
import models.enums.TileType;
import models.enums.Weather;
import models.things.Food.Food;
import models.things.Item;
import models.things.machines.Machine;
import models.things.products.Product;
import models.things.relations.Gift;
import models.things.relations.Quest;
import models.things.tools.*;

import javax.swing.plaf.ButtonUI;

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
    public Result<String> BuildGreenHouse() {
        Player player = App.currentGame.currentPlayer;
        // check the inventory and do the buy
        {
            if (false)
                return new Result<>(false, "You dont have the money to fixed the green house");
        }
        App.getCurrentGame().map.farms[App.currentGame.turn].getGreenHouses().hasRepeare = true;
        return new Result<>(true,"The Green House has been Repaired!");
    }
    public Result<String> showEnergy() {
        return new Result<>(false,String.valueOf(App.currentGame.currentPlayer.EnergyObject.getCurrentEnergy()));
    }
    public Result<String> setEnergy(Matcher matcher) {
        Player player = App.currentGame.currentPlayer;
        try{
            player.EnergyObject.setCurrentEnergy(Integer.parseInt(matcher.group("value")));
        }
        catch (Exception e){
            return new Result<>(false,e.getMessage());
        }
        return new Result<>(true,"new Energy: " + String.valueOf(player.EnergyObject.getCurrentEnergy()));
    }
    public void setEnergyCapacity() {
        Player player = App.currentGame.currentPlayer;
        player.EnergyObject.setEnergyCap(999999999);
    }
    private void CrowAttack() {
        ArrayList<Plant> plants = new ArrayList<>(App.getCurrentGame().getPlants());
        int totalPlants = plants.size();

        if (totalPlants < 16)
            return;

        Random random = new Random();
        int chance = (totalPlants / 16) * 25;

        while (chance >= 100 && !plants.isEmpty()) {
            int index = random.nextInt(plants.size());
            CrowAttackPlant(plants.get(index));
            plants.remove(index);
            chance -= 100;
        }

        int randomRoll = random.nextInt(4) + 1;
        if (randomRoll <= (chance / 25) && !plants.isEmpty()) {
            int index = random.nextInt(plants.size());
            CrowAttackPlant(plants.get(index));
        }
    }
    private void CrowAttackPlant(Plant plant) {
        if(plant.isIsReUsable()){
            if(plant.getCurrentStage() == -1)
                plant.setCurrentStage(-2);
            plant.setCurrentStageCount(0);
        }
        else{
            App.getCurrentGame().getPlants().remove(plant);
            //remove plant from Farm too @sarsar
        }
    }
    public Result<String> InventoryTrashTotal(Matcher matcher) {
        Player player = App.currentGame.currentPlayer;
        String itemName = matcher.group("itemName");
        Item item = player.getInvetory().findItemFromName(itemName);
        if (item == null)
            return new Result<>(false,"Item not found");
        player.getInvetory().removeItem(item);
        player.addMoney(item.getValue() * item.getAmount() * (player.trashCanLevel * 15)/100);
        return new Result<>(true,"Item removed");
    }
    public Result<String> InventoryTrash(Matcher matcher) {
        Player player = App.currentGame.currentPlayer;
        String itemName = matcher.group("itemName");
        int number;
        try{
            number = Integer.parseInt(matcher.group("number"));
        }
        catch (Exception e){
            return new Result<>(false,"Wrong number format");
        }
        Item item = player.getInvetory().findItemFromName(itemName);
        if (item == null)
            return new Result<>(false,"Item not found");
        if (number > item.getAmount())
            return new Result<>(false,"You don't have this much of this Item");
        if (number == item.getAmount())
            player.getInvetory().removeItem(item);
        else
            item.setAmount(item.getAmount() - number);
        player.addMoney(item.getValue() * number * (player.trashCanLevel * 15)/100);

        return new Result<>(true,"Item removed");
    }
    public Result<String> EquipTool(Matcher matcher) {
        Player player = App.currentGame.currentPlayer;
        String toolName = matcher.group("toolName");
        Item tool = player.getInvetory().findItemFromName(toolName);
        if (tool == null)
            return new Result<>(false,"You don't have this Tool");
        player.currentToll = tool;
        return new Result<>(true,"You Equped the tool " + toolName);
    }
    public Result<String> showCurrentTool() {
        Player player = App.currentGame.currentPlayer;
        if (player.currentToll == null)
            return new Result<>(false,"no Current Toll");
        return new Result<>(true,"Current Toll: " + player.currentToll.getName());
    }
    public Result<String> showAvailableTools(){
        Player player = App.currentGame.currentPlayer;
        StringBuilder result = new StringBuilder();
        for(Item item : player.getInvetory().getItems()){
            if ((item.getItemID() >=54 && item.getItemID() <=58) || item.getItemID() == 61 || item.getItemID() == 52){
                result.append(item.getName());
            }
        }
        return new Result<>(true, result.toString());
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
                    System.out.println(player.getUsername() + " has no Talked To Player " + App.getCurrentGame().getCurrentPlayer().getUsername());
                    player.addFriendshipXP(20, App.getCurrentGame().getCurrentPlayer());
                    App.getCurrentGame().getCurrentPlayer().addFriendshipXP(20, player);
                }
                messege = App.getCurrentGame().getCurrentPlayer().getUsername() + " : " + messege;
                player.addMessegeToTalkHistory(App.getCurrentGame().getCurrentPlayer(), messege);
                App.getCurrentGame().getCurrentPlayer().addMessegeToTalkHistory(player, messege);
                player.setHasBeenTalkedTo(App.getCurrentGame().getCurrentPlayer(), true);
                App.getCurrentGame().getCurrentPlayer().setHasBeenTalkedTo(player, true);
                player.addNotifToNotifications("You have a new messege!!!! - "+ messege);
                return new Result<>(true, messege);
            }
        }
        return new Result<>(false, "can't find player username!");
    }
    public Result<String> showHistory(String username) {
        boolean found = false;
        StringBuilder output = new StringBuilder();
        for(Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equals(username)){
                found = true;
                for(String messege : App.getCurrentGame().getCurrentPlayer().getTalkHistory().get(player)) {
                    output.append(messege)
                            .append("\n");
                }
            }
        }
        if(found) return new Result<String>(true, output.toString());
        return new Result<String>(false, "User not real!");
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
                        player.getInvetory().addItem(giftedItem);
                        App.addGiftIdCounter();
                        player.addNotifToNotifications("You have a new gift!!!!!!! (check using gift list)");
                        return new Result<>(true, "Gift Given!");
                    }
                }
                return new Result<>(false, "You Don't Have That Item!");
            }
        }
        return new Result<>(false, "can't find player username!");
    }
    public Result<String> showFriendships() {
        StringBuilder output = new StringBuilder();
        for(Player player : App.getCurrentGame().getPlayers()) {
            output.append("Username: ").append(player.getUsername()).append("\n")
                    .append("Friendship Level: ").append(player.getFriendshipLevel().get(App.getCurrentGame().getCurrentPlayer())).append("\n")
                    .append("Friendship XP: ");
            if(player.getFriendshipXP().get(App.getCurrentGame().getCurrentPlayer()) > (player.getFriendshipLevel().get(App.getCurrentGame().getCurrentPlayer()) + 1) * 100 )
                output.append((player.getFriendshipLevel().get(App.getCurrentGame().getCurrentPlayer()) + 1) * 100);
            else
                output.append(player.getFriendshipXP().get(App.getCurrentGame().getCurrentPlayer()) );
        }
        return new Result<>(true, output.toString());
    }

    public Result<String> hugPlayer(String username) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equals(username)) {
                if(player.getFriendshipLevel().get(App.getCurrentGame().getCurrentPlayer()) < 2)
                    return new Result<>(false, "You don't know that player enough to Hug");
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
        Point playerPOint = App.currentGame.map.farms[App.currentGame.turn].personPoint;
        TileType[] Neighbers = getSurroundingTiles(playerPOint);
        for (TileType type : Neighbers) {
            if (type != null &&  type.equals(TileType.PERSON)) {
                return true;
            }
        }
        return false;
    }
    public Result<String> TalkToNPC(String npcname) {
        if (!isNPCHere())
            return new Result<>(false, "There is No NPC Around You!");
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
                npc.addFriendship(20 , App.getCurrentGame().currentPlayer);
                return new Result<>(true , npc.getResponses().get(randomNumber));
            }
        }
        return new Result<>(false , "No NPC found with that name!");
    }
    public void nextDay() {
        Map map = App.currentGame.map;
        for(int i = 0 ; i < App.currentGame.getPlayers().size() ; i++) {

        }
        for (Player player : App.getCurrentGame().getPlayers()) {

        }
    }
    public Result<String> GiveGiftToNPC(String npcName , String giftName) {
        for (NPC npc : App.getCurrentGame().getNpcs()){
            if(npc.getName().equalsIgnoreCase(npcName)) {
                for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                    if(item.getName().equalsIgnoreCase(giftName)){
                        if(item.getValue() == -1)
                            return new Result<>(false, "You Can't gift that Item!");
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
    public Result<String> listGifts() {
        StringBuilder output = new StringBuilder();
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();

        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.equals(currentPlayer)) continue;
            List<Gift> giftsFromPlayer = currentPlayer.getPendingGifts().get(player);
            if (giftsFromPlayer == null || giftsFromPlayer.isEmpty()) continue;

            for (Gift gift : giftsFromPlayer) {
                output.append(player.getUsername())
                        .append(" : ")
                        .append(gift.getID())
                        .append(" > ")
                        .append(gift.getItem().getName())
                        .append(" NOT RATED YET\n");
            }
        }

        return new Result<>(true, output.toString());
    }

    public Result<String> showNpcs() {
        StringBuilder output = new StringBuilder();
        for(NPC npc : App.getCurrentGame().getNpcs()) {
            output.append(npc.getName())
                    .append(" : ")
                    .append(npc.getFriendship().get(App.getCurrentGame().getCurrentPlayer()))
                    .append("\n");
        }
        return new Result<>(false, output.toString());
    }

    public Result<String> listGiftHistory(String username) {
        boolean found = false;
        StringBuilder output = new StringBuilder();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                found = true;
                List<Gift> giftsFromPlayer = player.getGiftHistory().get(App.getCurrentGame().getCurrentPlayer());
                if (giftsFromPlayer == null || giftsFromPlayer.isEmpty()) continue;
                for (Gift gift : giftsFromPlayer) {
                    output.append(player.getUsername())
                            .append(" : ")
                            .append(gift.getID())
                            .append(" > ")
                            .append(gift.getItem().getName())
                            .append("\n");
                }
            }
        }
        if(found)
            return new Result<>(true , output.toString());
        return new Result<>(false, "username not found");
    }
    public Result<String> rateGift(String giftId , String rate) {
        if(!giftId.matches("\\d+") || !rate.matches("\\d+"))
            return new Result<>(false, "giftId or Rate Format invalid!");
        if( Integer.parseInt(rate) > 5 || Integer.parseInt(rate)<1)
            return new Result<>(false, "Use a number between 1 and 5 ");
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        for(Player player : App.getCurrentGame().getPlayers()) {
            if (player.equals(currentPlayer)) continue;
            List<Gift> giftsFromPlayer = currentPlayer.getPendingGifts().get(player);
            if (giftsFromPlayer == null || giftsFromPlayer.isEmpty()) continue;
            for(Gift gift : giftsFromPlayer){
                if(gift.getID() == Integer.parseInt(giftId)) {
                    player.addGiftToGiftHistory(currentPlayer, gift);
                    currentPlayer.addGiftToGiftHistory(player, gift);
                    giftsFromPlayer.remove(gift);
                    player.addFriendshipXP( (Integer.parseInt(rate) - 3) * 30 + 15 , currentPlayer);
                    currentPlayer.addFriendshipXP( (Integer.parseInt(rate) - 3) * 30 + 15 , player);
                    return new Result<>(true, "Gift Rated as a " + rate + " Out of five!");
                }
            }
        }
        return new Result<>(false, "Giftid invalid Check with Gift list");
    }
    public Result<String> showTalkHistory(String username) {
        boolean found = false;
        StringBuilder output = new StringBuilder();
        for(Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equals(username)){
                found = true;
                for(String messege : App.getCurrentGame().getCurrentPlayer().getTalkHistory().get(player)) {
                    output.append(messege)
                            .append("\n");
                }
            }
        }
        if(found) return new Result<String>(true, output.toString());
        return new Result<String>(false, "User not real!");
    }
    public Result<String> giveFlower(String username) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equals(username)) {
                if(!isPlayerNear(player))
                    return new Result<>(false, "Player too far away!");
                if(player.getFriendshipLevel().get(currentPlayer)!=2 || player.getFriendshipXP().get(currentPlayer) < 300)
                    return new Result<>(false, "You are not in a relationship position of giving a flower");
                for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                    if(item.getItemID() == 202) {
                        player.getInvetory().addItem(item);
                        currentPlayer.getInvetory().getItems().remove(item);
                        player.setFriendshipLevel(currentPlayer , 3);
                        currentPlayer.setFriendshipLevel(player , 3);
                        player.setFriendshipXP(currentPlayer , 0);
                        currentPlayer.setFriendshipXP(player , 0);
                        player.addNotifToNotifications(currentPlayer.getUsername() + " Gave you a flower!!!!");
                        return new Result<String>(true, "Flower Given!");
                    }
                }
                return new Result<>(false, "You don't have a flower in your inventory!");

            }
        }
        return new Result<>(false, "can't find player username!");
    }

    public Result<String> askMarriage(String username , String ring) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if(currentPlayer.getGender().equals(Gender.Female))
            return new Result<String>(false, "Only male players can ask for marriage.");
        if(!ring.equalsIgnoreCase("Wedding Ring"))
            return new Result<String>(false, "That is not a wedding ring!");
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(username.equalsIgnoreCase(player.getUsername())) {
                if(player.getGender().equals(Gender.Male))
                    return new Result<String>(false, "You are Gay!!!");
                if(player.getFriendshipLevel().get(currentPlayer)!=3 || player.getFriendshipXP().get(currentPlayer) < 400)
                    return new Result<String>(false, "You are not Close enough friends to do that!");
                for (Item item : currentPlayer.getInvetory().getItems()) {
                    if(item.getItemID() == 203){
                        currentPlayer.getInvetory().getItems().remove(item);
                        player.setPendingPartner(currentPlayer);
                        player.addNotifToNotifications("someone asked you to marry them : " + currentPlayer.getUsername() + "!!!!!!");
                        return new Result<String>(true, "proposal made");
                    }
                }
                return new Result<String>(false, "No Wedding ring!");
            }
        }
        return new Result<String>(false, "player not found!");
    }
    public Result<String> respondMarriage(String response , String username) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if (currentPlayer.getPendingPartner() == null)  
            return new Result<>(false, "You have no marriage Requests!");
        if (!currentPlayer.getPendingPartner().getUsername().equals(username))
           return new Result<>(false,"This person is not your marriage partner!");
        if (response.equalsIgnoreCase("accept")) {
            currentPlayer.getInvetory().addItem(new Item(AllTheItemsInTheGame.getItemById(203),1));
            currentPlayer.getPendingPartner().setFriendshipLevel(currentPlayer , 4);
            currentPlayer.setFriendshipLevel(currentPlayer.getPendingPartner() , 4);
            currentPlayer.getPendingPartner().setFriendshipXP(currentPlayer , 0);
            currentPlayer.setFriendshipXP(currentPlayer.getPendingPartner() , 0);
            currentPlayer.setPartner(currentPlayer.getPendingPartner());
            currentPlayer.getPendingPartner().setPartner(currentPlayer);
            currentPlayer.getPendingPartner().addNotifToNotifications("She Accepted!!! You are now married!");
            currentPlayer.setPendingPartner(null);
            return new Result<String>(true, "You are now married!");
        }
        if (response.equalsIgnoreCase("reject")) {
            currentPlayer.getPendingPartner().getInvetory().addItem(new Item(AllTheItemsInTheGame.getItemById(203),1));
            currentPlayer.getPendingPartner().setFriendshipLevel(currentPlayer , 0);
            currentPlayer.setFriendshipLevel(currentPlayer.getPendingPartner() , 0);
            currentPlayer.getPendingPartner().setFriendshipXP(currentPlayer , 0);
            currentPlayer.setFriendshipXP(currentPlayer.getPendingPartner() , 0);
            currentPlayer.getPendingPartner().addNotifToNotifications("She did not accept.");
            currentPlayer.setPendingPartner(null);
            return new Result<String>(true , "You declined the marriage proposal!");
        }
        return new Result<String>(false, "response invalid!");
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
    public Result<String> FinishQuest(int QuestIndex) {
        for (NPC npc : App.getCurrentGame().getNpcs()) {
            if (npc.getQuest1().getQuestID() == QuestIndex) {
                if(!isNPCHere())
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest1() , npc);

            }
            if (npc.getQuest2().getQuestID() == QuestIndex) {
                if(!isNPCHere())
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest2() , npc);
            }
            if (npc.getQuest3().getQuestID() == QuestIndex) {
                if(!isNPCHere())
                    return new Result<>(false , "NPC too far away!");
                return finishQuest2(npc.getQuest3() , npc);
            }

        }
        return new Result<>(false , "invalid Quest index");
    }
    //for finishquest
    private boolean isNPCHere() {
        Point playerPOint = App.currentGame.map.farms[App.currentGame.turn].personPoint;
        TileType[] Neighbers = getSurroundingTiles(playerPOint);
        for (TileType type : Neighbers) {
            if (type.equals(TileType.ABIGEL) ||  type.equals(TileType.SEBASTIAN) || type.equals(TileType.HARVEY)
                    || type.equals(TileType.LEAH ) || type.equals(TileType.ROBIN) ) {
                return true;
            }
        }
        return false;
    }
    private boolean isNear(TileType isType) {
        Point playerPOint = App.currentGame.map.farms[App.currentGame.turn].personPoint;
        TileType[] Neighbers = getSurroundingTiles(playerPOint);
        for (TileType type : Neighbers) {
            if (type.equals(isType)) {
                return true;
            }
        }
        return false;
    }
    public TileType[] getSurroundingTiles(Point center) {
        TileType[] surrounding = new TileType[8];
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},          {0, 1},
                {1, -1},  {1, 0},  {1, 1}
        };

        int count = 0;
        for (int[] dir : directions) {
            int nx = center.x + dir[0];
            int ny = center.y + dir[1];

            if (nx >= 0 && nx < App.currentGame.map.tiles.length &&
                    ny >= 0 && ny < App.currentGame.map.tiles[0].length) {

                surrounding[count++] = App.currentGame.map.tiles[nx][ny].type;
            } else {
                surrounding[count++] = null;
            }
        }
        return surrounding;
    }
    private Result<String> finishQuest2(Quest quest , NPC npc) {
        if(quest.isIsDone())
            return new Result<>(false , "Quest Already Done!");
        if(!quest.getIsActive().get(App.getCurrentGame().getCurrentPlayer()))
            return new Result<>(false , "You don't have access to that Quest yet!");
        for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.questEquals(quest.getRequiermentItems())) {
                item.reduceAmount(quest.getRequiermentItems().getAmount());
                if(quest.getRewards().getItemID() == 201) //201 is the friendship level item that shall not exist
                    npc.addFriendship(200, App.getCurrentGame().getCurrentPlayer());
                else if(npc.getFriendship().get(App.getCurrentGame().getCurrentPlayer()) >= 400)
                    App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().add(new Item(quest.getRewards(), quest.getRewards().getAmount() * 2));
                else
                    App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().add(quest.getRewards());
                App.getCurrentGame().getCurrentPlayer().addMoney(quest.getRewardMoney());
                quest.setIsDone(true);
                if(item.getAmount() == 0)
                    App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().remove(item);
                return new Result<>(true , "Quest completed");
            }
        }
        return new Result<>(false , "You don't have the quest Item (Or Enough of it)");
    }
    public Result<String> handleNewGame(Matcher matcher, Scanner scanner) {
        List<Player> players = new ArrayList<>();
        String[] playerGroups = {"player1", "player2", "player3"};

        for (String group : playerGroups) {
            String playerName = matcher.group(group);
            if (playerName != null && !playerName.isEmpty()) {
                User user = App.findPlayer(playerName);
                if (user == null) {
                    return new Result<>(false, "User not found: " + playerName);
                }
                Player player = new Player(
                        user.getUsername(), user.getPassword(), user.getEmail(),
                        user.getNickname(), user.getGender(),
                        user.getSecurityQuestion(), user.getSecurityAnswer()
                );
                players.add(player);
            }
        }

        if (players.size() < 1) {
            return new Result<>(false, "At least one player is required to start a game.");
        }

        String[] farmNames = new String[players.size() + 1];
        for (int i = 0; i < players.size() + 1; i++) {
            String input = scanner.nextLine();
            Matcher mapMatcher = models.enums.commands.GameMenu.gamemap.getMatcher(input);
            if (mapMatcher != null && mapMatcher.matches()) {
                farmNames[i] = mapMatcher.group("mapNumber");
            } else {
                return new Result<>(false, "Invalid map input for player " + (i + 1));
            }
        }

        Game newGame = new Game(players);
        Map newMap = new Map(farmNames);
        newGame.map = newMap;
        App.setCurrentGame(newGame);

        for (Player player : App.getCurrentGame().getPlayers()) {
            player.setupRelations();
        }

        App.currentGame.setNpc();
        String playerNames = players.stream().map(Player::getUsername).collect(Collectors.joining(", "));
        return new Result<>(true, "New game started with players: " + playerNames);
    }
    public void printMap() {
        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 120; j++) {
                TileType type = App.currentGame.map.tiles[i][j].type;
                System.out.print(type != TileType.EMPTY ? type.getSticker() : "++");
            }
            System.out.println();
        }
    }

    public void walk(Matcher matcher, Scanner scanner) {
        int[][] grid = new int[160][120];
        Set<TileType> walkable = Set.of(
                TileType.EMPTY, TileType.STONE, TileType.TREE,
                TileType.PERSON, TileType.DOOR, TileType.FARMWALL,
                TileType.GRASS,TileType.MACHINE,TileType.TILLED
        );

        Game game = App.currentGame;
        int turn = game.turn;
        Point offset = App.farmStart[turn];
        Point personRel = game.map.farms[turn].personPoint;
        Point personAbs = new Point(offset.x + personRel.x, offset.y + personRel.y);

        // ساخت گراف مسیر‌یابی
        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 120; j++) {
                TileType type = game.map.tiles[i][j].type;
                grid[i][j] = (type != null && walkable.contains(type)) ? 0 : 1;
            }
        }

        // گرفتن مقصد به صورت مطلق
        int targetX = Integer.parseInt(matcher.group("x"));
        int targetY = Integer.parseInt(matcher.group("y"));

        // مسیر‌یابی بین مختصات مطلق
        List<int[]> fullPath = shortestPath(grid, personAbs.x, personAbs.y, targetX, targetY);
        if (fullPath.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        List<int[]> turns = getTurns(fullPath);
        int energyCost = fullPath.size() + 10 * turns.size();
        int energyUnits = (int) Math.ceil(energyCost / 20.0);
        Player player = App.currentGame.currentPlayer; // استفاده از بازیکن فعلی
        int currentEnergy = player.EnergyObject.getCurrentEnergy();

        System.out.println("You need " + energyUnits + " energy to get there. Do you want to go? (yes/no)");
        String input = scanner.nextLine();
        if (!input.equalsIgnoreCase("yes")) return;

        int usedEnergy = 0;
        Point lastAbs = personAbs;

        for (int k = 1; k < fullPath.size(); k++) {
            int[] curr = fullPath.get(k);
            int[] prev = fullPath.get(k - 1);

            boolean isTurn = (k > 1) && (
                    curr[0] - prev[0] != prev[0] - fullPath.get(k - 2)[0] ||
                            curr[1] - prev[1] != prev[1] - fullPath.get(k - 2)[1]
            );

            usedEnergy += isTurn ? 11 : 1;

            if (Math.ceil(usedEnergy / 20.0) > currentEnergy) {
                System.out.printf("Energy ran out before reaching destination. Stopped at (%d, %d)%n", curr[0], curr[1]);

                int reducedEnergy = (player.EnergyObject.getEnergyCap() * 3) / 4;
                player.EnergyObject.setCurrentEnergy(reducedEnergy);
                nextTurn();
                return;
            }

            // آپدیت نقشه و جایگاه شخصیت
            game.map.tiles[lastAbs.x][lastAbs.y].type = game.map.farms[turn].lastTileType;
            game.map.farms[turn].lastTileType = game.map.tiles[curr[0]][curr[1]].type;
            game.map.tiles[curr[0]][curr[1]].type = TileType.PERSON;
            game.map.farms[turn].personPoint = new Point(curr[0] - offset.x, curr[1] - offset.y);
            lastAbs = new Point(curr[0], curr[1]);
        }

// انرژی نهایی پس از رسیدن به مقصد
        int finalUnitsUsed = (int) Math.ceil(usedEnergy / 20.0);
        player.EnergyObject.setCurrentEnergy(currentEnergy - finalUnitsUsed);
        System.out.println("Used energy: " + finalUnitsUsed + " units.");

        // بررسی همسایگی در مقصد اگر روی DOOR ایستاده‌ایم
        if (game.map.farms[turn].lastTileType == TileType.DOOR) {
            Point absPerson = lastAbs;

            int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

            TileType[] types = new TileType[8];
            int[] counts = new int[8];
            int uniqueCount = 0;

            for (int i = 0; i < 8; i++) {
                int nx = absPerson.x + dx[i];
                int ny = absPerson.y + dy[i];

                if (nx >= 0 && nx < 160 && ny >= 0 && ny < 120) {
                    Tile neighbor = game.map.tiles[nx][ny];
                    if (neighbor != null && neighbor.type != null) {
                        boolean found = false;
                        for (int j = 0; j < uniqueCount; j++) {
                            if (types[j] == neighbor.type) {
                                counts[j]++;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            types[uniqueCount] = neighbor.type;
                            counts[uniqueCount] = 1;
                            uniqueCount++;
                        }
                    }
                }
            }

            if (uniqueCount > 0) {
                int maxIndex = 0;
                for (int i = 1; i < uniqueCount; i++) {
                    if (counts[i] > counts[maxIndex]) maxIndex = i;
                }

                if (counts[maxIndex] >= 3) {
                    String menu = types[maxIndex].toString();
                    switch (menu) {
                        case "BLACKSMITH", "JOJAMART", "PIERRESSTORE", "CARPENTER",
                             "FISHSHOP", "MARNIESRANCH", "STARDROPSALOON" -> App.currentMenu = Menu.StoreMenu;
                        case "GREENHOUSE" -> App.currentMenu = Menu.GreenHouseMenu;
                        case "COTTAGE" -> App.currentMenu = Menu.cottageMenu;
                    }
                }
            }
        }
    }

    private Point getAbsolutePlayerPoint() {
        Game game = App.currentGame;
        int turn = game.turn;
        Point offset = App.farmStart[turn];
        Point rel = game.map.farms[turn].personPoint;
        return new Point(offset.x + rel.x, offset.y + rel.y);
    }
    public void checkEnteringMenu(){
        Game currentGame = App.currentGame;
        int turn = currentGame.turn;

        if (currentGame.map.farms[turn].lastTileType == TileType.DOOR) {
            Map map = currentGame.map;
            Point farmOffset = App.farmStart[turn];
            Point relPerson = map.farms[turn].personPoint;
            Point absPerson = new Point(farmOffset.x + relPerson.x, farmOffset.y + relPerson.y);

            int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

            TileType[] types = new TileType[8];
            int[] counts = new int[8];
            int uniqueCount = 0;

            for (int i = 0; i < 8; i++) {
                int nx = absPerson.x + dx[i];
                int ny = absPerson.y + dy[i];

                if (nx >= 0 && nx < map.tiles.length && ny >= 0 && ny < map.tiles[0].length) {
                    Tile neighbor = map.tiles[nx][ny];
                    if (neighbor != null && neighbor.type != null) {
                        boolean found = false;
                        for (int j = 0; j < uniqueCount; j++) {
                            if (types[j] == neighbor.type) {
                                counts[j]++;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            types[uniqueCount] = neighbor.type;
                            counts[uniqueCount] = 1;
                            uniqueCount++;
                        }
                    }
                }
            }
            if (uniqueCount == 0) {
                System.out.println("No valid surrounding tiles found.");
            } else {
                int maxIndex = 0;
                for (int i = 1; i < uniqueCount; i++) {
                    if (counts[i] > counts[maxIndex]) {
                        maxIndex = i;
                    }
                }
                System.out.println("Most common TileType around player: " + types[maxIndex] + " (" + counts[maxIndex] + " times)");
            }
        }
    }
    static class Node {
        int x, y, dist;
        Node parent;

        Node(int x, int y, int dist, Node parent) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.parent = parent;
        }
    }
    public static List<int[]> getTurns(List<int[]> path) {
        List<int[]> turns = new ArrayList<>();
        if (path.size() < 3) return turns;

        for (int i = 1; i < path.size() - 1; i++) {
            int[] prev = path.get(i - 1);
            int[] curr = path.get(i);
            int[] next = path.get(i + 1);

            int dx1 = curr[0] - prev[0];
            int dy1 = curr[1] - prev[1];
            int dx2 = next[0] - curr[0];
            int dy2 = next[1] - curr[1];

            if (dx1 != dx2 || dy1 != dy2) {
                turns.add(curr);
            }
        }

        return turns;
    }

    public static List<int[]> shortestPath(int[][] grid, int startX, int startY, int endX, int endY) {
        int n = grid.length;
        int m = grid[0].length;

        if (startX < 0 || startY < 0 || endX < 0 || endY < 0 ||
                startX >= n || startY >= m || endX >= n || endY >= m ||
                grid[startX][startY] == 1 || grid[endX][endY] == 1) {
            return Collections.emptyList();
        }

        boolean[][] visited = new boolean[n][m];
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startX, startY, 0, null));
        visited[startX][startY] = true;

        Node endNode = null;

        while(!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.x == endX && current.y == endY) {
                endNode = current;
                break;
            }
            for (int dir = 0; dir < 4; dir++) {
                int newX = current.x + dx[dir];
                int newY = current.y + dy[dir];
                if (newX >= 0 && newY >= 0 && newX < n && newY < m &&
                        grid[newX][newY] == 0 && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.add(new Node(newX, newY, current.dist + 1, current));
                }
            }
        }

        if (endNode == null) return Collections.emptyList();

        List<int[]> path = new ArrayList<>();
        Node curr = endNode;
        while (curr != null) {
            path.add(new int[]{curr.x, curr.y});
            curr = curr.parent;
        }
        Collections.reverse(path);
        return path;
    }
    public Result<String> showTime(){
        return new Result<>(true, "Spent Hours : " + App.currentGame.time.getHourOfDay());
    }
    public Result<String> showDate(){
        return new Result<>(true, "Date: " +  App.currentGame.time.getSeason() + " / " + App.currentGame.time.getDayOfSeason());
    }

    public Result<String> showDayWeek(){
        return new Result<>(true, "Day week: " + App.currentGame.time.getDayWeek());
    }

    public Result<String> showSeason(){
        return new Result<>(true, "Season: " + String.valueOf(App.currentGame.time.getSeason()));
    }

    public Result<String> cheateAdvanceTime(Matcher matcher){
        int hour = Integer.parseInt(matcher.group("time"));
        if(hour<0)
            return new Result<>(false, "Invalid hour format (Time | Time > 0");
        Time.hour += hour;
        for (int i=0; i< hour/24; i++) {
            setUpNextDay();
        }
        return new Result<>(false, "new Time: " + Time.getHour());
    }

    public Result<String> cheateAdvanceDate(Matcher matcher){
        int day = Integer.parseInt(matcher.group("day")) * 24;
        if(day < 0 )
            return new Result<>(false, "Invalid Day format (Day | Day > 0");
        Time.hour += day;
        for (int i=0; i< day; i++) {
            setUpNextDay();
        }
        return new Result<>(false, "new Day: " + App.currentGame.time.getDayOfSeason());
    }
    public Result<String> GiveGiftToNPC(NPC npc , Item gift) {
        return null;
    }
    public Result<String> showCraftInfo(String itemName){
        StringBuilder output = new StringBuilder();
        Plant plant = null;
        for (int i = 302 ; i < 357;i++){ //302-357 is the ids for plants and trees
            plant = AllTheItemsInTheGame.allPlants.get(i);
            if (plant != null && plant.getName().equals(itemName)) {
                output.append("Name: ")
                        .append(itemName)
                        .append("\n")
                        .append("Source: ")
                        .append(plant.getSource())
                        .append("\n")
                        .append("Stages: ");
                for (int stage : plant.getGrowStages())
                    output.append(stage).append("-");
                output.append("\n")
                        .append("Total Harvest Time: ")
                        .append(plant.getTotalHarvestTime())
                        .append("\n")
                        .append("One Time: ")
                        .append(!plant.isIsReUsable())
                        .append("\n")
                        .append("Regrowth Time: ")
                        .append(plant.getRegrowthTime())
                        .append("\n")
                        .append("Base Sell Price: ")
                        .append(plant.getBaseValue())
                        .append("\n")
                        .append("is Edible: ")
                        .append(plant.isIsEdible())
                        .append("\n")
                        .append("Base Energy: ")
                        .append(plant.getEnergy())
                        .append("\n")
                        .append("Base Health: ")
                        .append(plant.getHealth())
                        .append("\n")
                        .append("Season: ")
                        .append(plant.getSeasonOfGrowth())
                        .append("\n")
                        .append("Can Become Giant: ")
                        .append(plant.isCanBecomeGiant());
                return new Result<>(true, output.toString());
            }
        }
        //another loop for foraging HERE TODO

        return new Result<>(false, "Item does not Exist!");
    }
    public Result<String> Tunder(Point point){
        TileType type = App.currentGame.map.tiles[point.x][point.y].type;
        System.out.println(type.getSticker());
        if (type.equals(TileType.TREE)) {
            App.currentGame.map.tiles[point.x][point.y].type = TileType.COAL;
            return new Result<>(false, "Tunder Hitted the tree and Becomed Coal at point " + point.x + " " + point.y);
        }
        else if (type.equals(TileType.PLANT)) {
            App.currentGame.map.tiles[point.x][point.y].type = TileType.TILLED;
            for (int i =0;i < App.currentGame.players.size();i++) {
                for (HashMap.Entry<Point, Plant> entry : App.currentGame.map.farms[i].plantMap.entrySet()) {
                    Point temp = entry.getKey();
                    Plant plant = entry.getValue();
                    if (point.x == temp.x && point.y == temp.y) {
                        App.currentGame.map.farms[i].plantMap.remove(temp);
                        return new Result<>(true, "Plant Removed from PLayer " +
                                App.currentGame.players.get((i)).getUsername() + " farm at point " + point.x + ", " + point.y);
                    }
                }
            }

        }
        return new Result<>(true, "Tunder at " + point.x + ", " + point.y);
    }
    public Result<String> SellItem(Matcher matcher){
        String ItemName = matcher.group("productName");
        Player player = App.getCurrentGame().getCurrentPlayer();
        int amount;
        try{
            amount = Integer.parseInt(matcher.group("count"));
        }
        catch (NumberFormatException e){
            return new Result<>(false, "Invalid amount");
        }
        if (!isNear(TileType.SHIPPING_BIN)) {
            return new Result<>(false, "You arent Near a Shipping Bin a shipping bin");
        }
        for(Item i : player.getInvetory().getItems()){
            if (i.getName().equals(ItemName)){
                if (i.getAmount() < amount){
                    return new Result<>(false, "You do not have enough of this item");
                }
                else if (i.getAmount() == amount){
                    player.getInvetory().getItems().remove(i);
                }
                else{
                    i = new Item(i,i.getAmount() - amount);
                }
                i = new Item(i,amount);
                player.addToShippingBin(i);
                return new Result<>(true, "You selled the " + i.getName() + " item Successfully");
            }
        }
        return new Result<>(false, "Item does not Exist or You dont Have it");
    }


    public Plant getPlantFromMixedSeed() {
        int[] springIds = {304, 309, 310, 302, 313};
        int[] summerIds = {316, 318, 321, 328, 320, 326, 324};
        int[] fallIds   = {316, 330, 335, 338, 336, 326};

        Random random = new Random();
        int finalId = 0;

        Season currentSeason = App.getCurrentGame().getTime().getSeason();

        if (null != currentSeason) switch (currentSeason) {
            case WINTER:
                finalId = 341;
                break;
            case SPRING:
                finalId = springIds[random.nextInt(springIds.length)];
                break;
            case SUMMER:
                finalId = summerIds[random.nextInt(summerIds.length)];
                break;
            case FALL:
                finalId = fallIds[random.nextInt(fallIds.length)];
                break;
            default:
                break;
        }
        return AllTheItemsInTheGame.getPlantById(finalId);
    }
    public Result<String> plantPlant (String seedName , String direction) {
        System.out.println("plantPlant " + seedName + " " + direction);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        for(Item item : currentPlayer.getInvetory().getItems()){
            if (item.getName().equalsIgnoreCase(seedName)) {
                if(item.getItemID() > 456 || item.getItemID() < 402)
                    return new Result<>(false, "the Item you are attempting to plant is not a Seed!");
                Plant basePlant = null;
                if(item.getItemID() !=397)
                    basePlant = AllTheItemsInTheGame.getPlantById(item.getItemID() - 100);
                else{
                    basePlant = getPlantFromMixedSeed();
                }
                if (!isPlantInSeason(basePlant))
                    return new Result<>(false, "its not the perfect time to plant. come back in " + basePlant.getSeasonOfGrowth() + " for planting");
                item.reduceAmount(1);
                if(item.getAmount() == 0)
                    currentPlayer.getInvetory().getItems().remove(item);
                Point offset = getOffsetFromDirection(direction);
                if (offset == null) {
                    return new Result<>(false, "Invalid direction!");
                }
                Point current = App.currentGame.map.farms[App.currentGame.turn].personPoint;
                Point target = new Point(current.getX() + offset.getX(), current.getY() + offset.getY());
                if (!App.currentGame.map.tiles[target.x][target.y].type.equals(TileType.TILLED))
                    return new Result<>(false, "You are attempting to plant in a not tilled Ground!");
                Plant targetPlant = new Plant(basePlant, target);
                putPlantInGround(targetPlant);
                App.currentGame.map.farms[App.currentGame.turn].plantMap.put(target, targetPlant);
                return new Result<>(true, "Plant " + item.getName() + " is now planted in (" + target.x + ", " + target.y +") cordinates !");
            }
        }
        return new Result<>(false, "You don't have that seed/Item!");
    }
    public boolean isPlantInSeason(Plant plant) {
        Season plantSeason = plant.getSeasonOfGrowth();
        Season currentSeason = App.getCurrentGame().getTime().getSeason();
    
        if (plantSeason == currentSeason) return true;
        if (plantSeason == Season.OTHER_THAN_WINER && currentSeason != Season.WINTER) return true;
        if (plantSeason == Season.SPECIAL) return true;
        if (plantSeason == Season.SUMMER_FALL && (currentSeason == Season.SUMMER || currentSeason == Season.FALL)) return true;
        if (plantSeason == Season.SPRING_SUMMER && (currentSeason == Season.SPRING || currentSeason == Season.SUMMER)) return true;
    
        return false;
    }





    public Result<String> placeItem(String itemName , String direction) {
        System.out.println("PlaceItem " + itemName + " " + direction);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        for(Item item : currentPlayer.getInvetory().getItems()){
            if (item.getName().equalsIgnoreCase(itemName)) {
                if(!(item instanceof Machine))
                    return new Result<String>(false, "This Item can not be placed!");
                Point offset = getOffsetFromDirection(direction);
                if (offset == null) {
                    return new Result<>(false, "Invalid direction!");
                }
                Point current = App.currentGame.map.farms[App.currentGame.turn].personPoint;
                Point target = new Point(current.getX() + offset.getX(), current.getY() + offset.getY());
                if (!App.currentGame.map.tiles[target.x][target.y].type.equals(TileType.EMPTY))
                    return new Result<>(false, "You are attempting to put an item into a occupied space");
                item.reduceAmount(1);
                if(item.getAmount() == 0)
                    currentPlayer.getInvetory().getItems().remove(item);
                
                Machine originalMachine = (Machine) item;
                Machine machineToPlace = new Machine(originalMachine, 1, target);
                App.currentGame.map.tiles[target.x][target.y].type = TileType.MACHINE;
                return putMachineOnGround(machineToPlace);
            }
        }
        return new Result<>(false, "You don't have that Item!");
    }
    
    public Result<String> putMachineOnGround(Machine machine) {
        App.getCurrentGame().addMachineInMachines(machine);
        return new Result<>(false, "You have Placed down the Machine!");
    }

    public Point getOffsetFromDirection(String direction) {
        switch (direction.toUpperCase()) {
            case "NORTH": return new Point(-1, 0);
            case "SOUTH": return new Point(1, 0);
            case "EAST": return new Point(0, 1);
            case "WEST": return new Point(0, -1);
            case "NORTHEAST": return new Point(-1, 1);
            case "NORTHWEST": return new Point(-1, -1);
            case "SOUTHEAST": return new Point(1, 1);
            case "SOUTHWEST": return new Point(1, -1);
            default: return null;
        }
    }

    public Result<String> putPlantInGround (Plant plant) {
        App.getCurrentGame().addPlantInPlants(plant);
        Point placeInMap = plant.getPoint();
        App.currentGame.map.tiles[plant.getPoint().getX()][plant.getPoint().getX()].type = TileType.PLANT;
        if(plant instanceof Tree)
            App.currentGame.map.tiles[plant.getPoint().getX()][plant.getPoint().getX()].type = TileType.TREE;
        return new Result<>(true, "You have Planted the Plant!");
    }

    public Result<String> useArtisan(String itemName, String artisanName) {
        for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                boolean isItemFruit = false;
                boolean isItemVegtable = false;
                Point current = App.currentGame.map.farms[App.currentGame.turn].personPoint;
                if(item instanceof Product product){
                    isItemFruit = product.isIsFruit();
                    isItemVegtable = product.isIsVegetable();
                }
                for (Machine machine : App.getCurrentGame().getMachines()) {
                    Point machinePoint = machine.getPoint();
                    if (Math.abs(machinePoint.getX() - current.getX()) <= 1 &&
                        Math.abs(machinePoint.getY() - current.getY()) <= 1 &&
                        machine.getName().equalsIgnoreCase(artisanName)) {
                        for (Operation operation : machine.getOperations()) {
                            if (operation.getInput().getAmount() <= item.getAmount() &&
                            (operation.getInput().getItemID() == item.getItemID() || (operation.getInput().getItemID() == 300
                             && isItemFruit) || (operation.getInput().getItemID() == 299 && isItemVegtable)) ) {

                                item.reduceAmount(operation.getInput().getAmount());
                                if (item.getAmount() == 0) {
                                    App.getCurrentGame().getCurrentPlayer().getInvetory().removeItem(item);
                                }
                                machine.setCurrentOperation(new Operation(operation, item));
                                return new Result<>(true, "Artisan operation has started!");
                            }
                        }
                        return new Result<>(false, "Invalid item for that machine!");
                    }
                }
                return new Result<>(false, "There is no artisan machine nearby.");
            }
        }
        return new Result<>(false, "You don't have that item!");
    }
    public Result<String> artisanGet(String artisanName) {
        Point current = App.currentGame.map.farms[App.currentGame.turn].personPoint;
        for (Machine machine : App.getCurrentGame().getMachines()) {
            Point machinePoint = machine.getPoint();
            if (Math.abs(machinePoint.getX() - current.getX()) <= 1 &&
                Math.abs(machinePoint.getY() - current.getY()) <= 1 &&
                machine.getName().equalsIgnoreCase(artisanName)) {
                    if(machine.getCurrentOperation().getId() == 0)
                        return new Result<String>(false, "There's nothing in there!");
                    if(machine.getCurrentOperation().getReadyTime() != machine.getCurrentOperation().getCurrentTime())
                        return new Result<String>(false,"Product Not ready!");
                    App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(machine.getOutput());
                    machine.setCurrentOperation(new Operation(0, 0, AllTheItemsInTheGame.getItemById(0), AllTheItemsInTheGame.getItemById(0), false));
                    return new Result<String>(true, "Product Collected!");
            }
        }
        return new Result<String>(false, "Artisan Not Nearby!");
    }

    public Result<String> fertilizeGround(String fertilizer , String direction){
        boolean playerHasFertilizer = false;
        for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems())
            if(item.getName().equalsIgnoreCase(fertilizer)){
                playerHasFertilizer = true;
                break;
            }
        int fertilizerId = 0;
        if(fertilizer.equalsIgnoreCase("basicRetainingSoil"))
            fertilizerId = 176 ;
        if(fertilizer.equalsIgnoreCase("improvedRetainingSoil"))
            fertilizerId = 177;
        if(fertilizer.equalsIgnoreCase("deluxeRetainingSoil"))
            fertilizerId = 178;
        if(fertilizer.equalsIgnoreCase("SpeedGro"))
            fertilizerId = 179;
        if(fertilizerId == 0)
            return new Result<>(false, "That fertilizer is not real");
        if(!playerHasFertilizer)
            return new Result<>(false, "You Don't have that fertilizer!");
        for (Plant plant : App.getCurrentGame().getPlants()) {
            Point offset = getOffsetFromDirection(direction);
            if (offset == null) {
                return new Result<>(false, "Invalid direction!");
            }
            Point current = App.currentGame.map.farms[App.currentGame.turn].personPoint;
            Point target = new Point(current.getX() + offset.getX(), current.getY() + offset.getY());
            if(plant.getPoint().getX() == target.getX() && plant.getPoint().getY() == target.getY()) {
                if(plant.isHasBeenFertilized())
                    return new Result<>(false, "Plant already fertilized");
                plant.setHasBeenFertilized(true);
                plant.setFertilizerId(fertilizerId);
                return new Result<>(true, "Plant Fertilized!");

            }
        }
        return new Result<>(false, "No plant there!");
    }
    public Result<String> cheatItem(Matcher matcher) {
        String itemName = matcher.group("itemName");
        int amount;
        try {
            amount = Integer.parseInt(matcher.group("count"));
            if (amount <= 0) {
                return new Result<>(false, "Amount must be greater than zero.");
            }
        }
        catch (NumberFormatException e) {
            return new Result<>(false, "Invalid amount.");
        }
        for (HashMap.Entry<Integer, Item> entry : AllTheItemsInTheGame.allItems.entrySet()) {
            Item item = entry.getValue();
            if (item instanceof Machine machine1)
                item = new Machine(machine1, amount, new Point(0, 0));
            else if (item instanceof  Product product1)
                item = new Product(product1.getName(),product1.getItemID(),product1.getValue(),product1.getParentItemID()
                ,product1.getAmount(),product1.isIsEdible(),product1.getEnergy(),product1.getHealth(),product1.getQuality(),product1.isIsFruit(),product1.isIsVegetable());
            else
                item = new Item(item,amount);

            if (item.getName().equalsIgnoreCase(itemName)) {
                App.getCurrentGame()
                        .getCurrentPlayer()
                        .getInvetory().addItem(item);

                return new Result<>(true, amount + " × " + itemName + " added to inventory.");
            }
        }

        return new Result<>(false, "Item not found: " + itemName);
    }
    public String showSpecificCraftInfo(Plant plant) {
        int daysPast = 0;
        for (int i = 0 ; i <plant.getGrowStages().length; i++) {
            if(plant.getCurrentStage() > i)
                daysPast+=plant.getGrowStages()[i];
            if(plant.getCurrentStage() == i)
                daysPast+=plant.getCurrentStageCount();
        }
        StringBuilder output= new StringBuilder();
        output.append("\nCurrent Grow Stage: ");
        switch (plant.getCurrentStage()) {
            case -1:
                output.append("READY TO HARVEST");
                break;
            case -2:
                output.append("REGROWING");
                break;
            default:
                output.append(plant.getCurrentStage());
                break;
        }
        output.append("\n")
                .append("current Grow Stage Count (days in current grow stage) :")
                .append(plant.getCurrentStageCount())
                .append("\n")
                .append("is watered today? ")
                .append(plant.isHasBeenWatered())
                .append("\n")
                .append("is fertilized? ")
                .append(plant.isHasBeenFertilized())
                .append("\n")
                .append("Days Until Ready to Harvest: ");
        switch (plant.getCurrentStage()) {
            case -2:
                output.append(plant.getRegrowthTime() - plant.getCurrentStageCount());
                break;
            case -1:
                output.append(0);
                break;
            default:
                output.append(plant.getTotalHarvestTime() - daysPast);
                break;
        }
        return output.toString();
    }

    public Result<String> showPlant(String x ,String y) {
        if(!x.matches("\\d+") || !y.matches("\\d+"))
            return new Result<>(false, "Point invalid!");
        for (Plant plant : App.getCurrentGame().getPlants()) {
            if(plant.getPoint().getX() == Integer.parseInt(x) && plant.getPoint().getY() == Integer.parseInt(y)) {
                return new Result<>(true , showCraftInfo(plant.getName()).toString() + showSpecificCraftInfo(plant));
            }
        }
        return new Result<>(false, "Plant not found");

    }
    public Result<String> CheatGrowPlant(String x ,String y) {
        for (Plant plant : App.getCurrentGame().getPlants()) {
            if(plant.getPoint().getX() == Integer.parseInt(x) && plant.getPoint().getY() == Integer.parseInt(y)) {
                plant.grow();
                return new Result<String>(true, "plant grew once!");
            }
        }
        return new Result<String>(false, "Plant not found");
    }
    public Result<String> toolUse(Matcher matcher) {
        String direction = matcher.group("direction");
        Point offset = getOffsetFromDirection(direction);
        Player player = App.currentGame.currentPlayer;

        int turn = App.currentGame.turn;
        int baseX = App.currentGame.map.farms[turn].personPoint.x;
        int baseY = App.currentGame.map.farms[turn].personPoint.y;

        int offsetX = App.farmStart[turn].x + offset.x;
        int offsetY = App.farmStart[turn].y + offset.y;

        int targetX = baseX + offsetX;
        int targetY = baseY + offsetY;

        Point targetPoint = new Point(targetX, targetY);

        return new Result<>(true,player.currentToll.useTool(targetPoint));

    }
    /*@amoojoey need  the Use Sⅽythe method to lead into harvestPlant with this code in it:
    for (Plant plant : App.getCurrentGame().getPlants()) {
        if(plant.getPoint().getX() == Integer.parseInt(x) && plant.getPoint().getY() == Integer.parseInt(y)) {
            //LEAD TO HARVEST PLANT
            return harvestPlant(plant);
        }
    //LEAD TO HARVEST WEED
*   return ??
*
*
*   /@amoojoey need the Use Watering_Can function to have this at the end
    for (Plant plant : App.getCurrentGame().getPlants()) {
        if(plant.getPoint().getX() == Integer.parseInt(x) && plant.getPoint().getY() == Integer.parseInt(y)) {
            plant.setHasBeenWatered(true);
            return new Result<>(true,"Plant watered");
        }
    }
    return new Result<>(false,"No Plant there!");

*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*/

    public Result<String> harvestPlant(Plant plant) {
        System.out.println(showSpecificCraftInfo(plant));
        if(plant.getCurrentStage() != -1)
            return new Result<>(false, "Plant not ready to harvest yet!");
        if(!plant.isIsReUsable()){
            App.getCurrentGame().getPlants().remove(plant);
            App.currentGame.map.tiles[plant.getPoint().x][plant.getPoint().y].type = TileType.TILLED;
            for (HashMap.Entry<Point, Plant> entry : App.currentGame.map.farms[App.currentGame.turn].plantMap.entrySet()) {
                Plant temp = entry.getValue();
                if (plant.getName().equals(temp.getName())) {
                    App.currentGame.map.farms[App.currentGame.turn].plantMap.remove(entry.getKey());
                }
            }
        }
        else{
            plant.setCurrentStage(-2);
            plant.setCurrentStageCount(0);
        }
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(plant.harvestPlant());
        App.currentGame.currentPlayer.skillProgress(0,5);
            return new Result<>(true, "Plant harvested");
    }
    public void farmPlantPrint(){
        for (HashMap.Entry<Point, Plant> entry : App.currentGame.map.farms[App.currentGame.turn].plantMap.entrySet()) {
            Plant temp = entry.getValue();
            System.out.println(showPlant(String.valueOf(entry.getKey().x),String.valueOf(entry.getKey().y)).getData()) ;
        }
    }
    public Result<String> showWeather(){
        if (App.currentGame != null) {
            return new Result<>(true, App.getCurrentGame().weather.toString());
        }
        return new Result<>(false, "Enter a Game first");
    }
    public Result<String> changeWeather(Matcher matcher) {
        try{
            App.currentGame.weather = Weather.valueOf(matcher.group("Type").toUpperCase());
            return new Result<>(true, "Weather Changed!");
        }
        catch(IllegalArgumentException e){
            return new Result<>(false, "Invalid Weather Type!");
        }
    }

    public Result<String> showWeatherForecast(){
        if (App.currentGame != null) {
            return new Result<>(true, App.getCurrentGame().setWeather().toString());
        }
        return new Result<>(false, "Enter a Game first");
    }
    public Result<String> farming() {
        //fill here...

        //this if for energy
        App.getCurrentGame().getCurrentPlayer().skillProgress(0, 5);
        return null;
    }

    public Result<String> fishing() {
        //fill here...

        //this is for energy
        App.getCurrentGame().getCurrentPlayer().skillProgress(1, 5);
        return null;
    }

    public Result<String> mining() {
        //fill here...

        //this is for energy
        App.getCurrentGame().getCurrentPlayer().skillProgress(2, 10);
        return null;
    }

    public Result<String> foraging() {
        //fill here...

        //this is for energy
        App.getCurrentGame().getCurrentPlayer().skillProgress(3, 10);
        return null;
    }

    public void showInventory() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        System.out.println("Money: " + player.getMoney());
        for(Item i : player.getInvetory().getItems()){
            System.out.println(i.getName() + " " + i.getAmount());
        }
    }
    public void showShippingBin() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        for(Item i : player.playerShipping_bin){
            System.out.println(i.getName() + " " + i.getAmount());
        }
    }
    //har chi mikhaid update she too shab barai farda ro bezanid inja
    public void setUpNextDay() {
        CrowAttack();
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1;
        for (Plant plant : App.getCurrentGame().getPlants()) {
            if(plant.getFertilizerId() ==179){
                plant.grow();
                plant.setFertilizerId(0);
            }

            if (plant.isHasBeenWatered())  plant.grow();
            if (App.getCurrentGame().tomarrowsWeather.equals(Weather.RAINY))
                plant.setHasBeenWatered(true);
            else
                plant.setHasBeenWatered(false);
            if(plant.getFertilizerId() == 176 && randomNumber <4)
                plant.setHasBeenWatered(true);

            if(plant.getFertilizerId() == 177 && randomNumber <8)
                plant.setHasBeenWatered(true);
            if(plant.getFertilizerId() == 178)
                plant.setHasBeenWatered(true);
        }
        //CHANGE WEATHER INTO TOMARROWS WEATHER AND CREATE NEW TOMARROWS WEATHER HERE
        App.currentGame.nextDayWeather();

        for (NPC npc : App.getCurrentGame().getNpcs()) {
            for(Player player : App.getCurrentGame().getPlayers()) {
                npc.getHasBeenGiftedTo().put(player, false);
                npc.getHasBeenTalkedTo().put(player, false);
                if (npc.getFriendship().get(player) >= 200)
                    npc.getQuest2().getIsActive().put(player, true);
                if (npc.getFriendship().get(player) >= 600){
                    int randomGift = random.nextInt(3);
                    int randomChance = random.nextInt(2);
                    if(randomChance == 1){
                        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(npc.getPossibleGifts().get(randomGift));
                        App.getCurrentGame().getCurrentPlayer().addNotifToNotifications(npc.getName() + " Gave you a Gift!");
                    }
                }
            }
        }

        for(Player player1 : App.getCurrentGame().getPlayers()){
            ArrayList<Item> bufferItemCopy = new ArrayList<>(player1.getInvetory().getBufferInvetory());
            player1.getInvetory().getBufferInvetory().clear();
            for (Item bufferItem : bufferItemCopy){
                player1.getInvetory().addItem(bufferItem);
            }
            bufferItemCopy.clear();
            //shipping bin calclations
            ArrayList<Item> soldItems = player1.getPlayerShipping_bin();
            if(!(soldItems == null || soldItems.isEmpty()))
                for (Item item : soldItems) {
                    if (item instanceof Product product)
                        player1.addMoney( (int) Math.floor(product.getAmount() * product.getValue() * product.getQuality().getPriceCoefficient()));
                    else 
                        player1.addMoney(item.getAmount() * item.getValue());
                } 
            //shipping bin calculations
            //friendship reset
            for(Player player2 : App.getCurrentGame().getPlayers()) {
                if (!player1.GetHasTalkedToPlayer(player2) && !player1.getHasbeenHugged().get(player2) && !player1.getHasBeenGiftedTo().get(player2))
                    player1.reduceFriendshipXP(10, player2);
                player1.setHasBeenTalkedTo(player2, false);
                player2.setHasBeenTalkedTo(player1, false);
                player1.setHasBeenGiftedTo(player2, false);
                player2.setHasBeenGiftedTo(player1, false);
                player1.setHasHuggedPlayer(player2, false);
                player2.setHasHuggedPlayer(player1, false);
            }
        }
        setMap();
        setShops();
    }

    private void setShops() {
        App.getCurrentGame().BlacksmithStore = new Blacksmith().blacksmithBulider();
        App.getCurrentGame().JojaMartStore = new JojaMart().jojaBuilder();
        App.getCurrentGame().CarpenterStore = new Carpenter().carpenterBuilder();
        App.getCurrentGame().FishShopStore = new FishShop().fishShopBulider();
        App.getCurrentGame().MarniesRanchStore = new MarniesRanch().MarnieRanchBuilder();
        App.getCurrentGame().TheSaloonStore = new TheSaloon().theSaloonBuilder();
        App.getCurrentGame().pierresStore = new pierres().pierresBuilder();
    }

    public Result<String> eat(String name) {
        for(Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getName().equals(name)) {
                if(item instanceof Product product) {
                    if(product.isEdible()){
                        App.getCurrentGame().getCurrentPlayer().getInvetory().reduceAmount(item, 1);
                    }
                    return product.eat();
                }

                if(item instanceof Food food) {
                    App.getCurrentGame().getCurrentPlayer().getInvetory().reduceAmount(item, 1);
                    return food.eat();
                }

                return new Result<>(false, "You can not eat this");
            }
        }

        return new Result<>(false, "You doesn't have this food!");
    }

    public void setMap(){
        int playerNUmber = App.getCurrentGame().getPlayers().size();
        for(int i=0; i < playerNUmber; i++){
            Map map = App.currentGame.map;
            Random rand = new Random();
            for (int j=0;i<rand.nextInt(10,20);i++){
                int x = rand.nextInt(App.farmStart[i%playerNUmber].x , App.farmStart[i%playerNUmber].x + 49);
                int y = rand.nextInt(App.farmStart[i%playerNUmber].y,App.farmStart[i%playerNUmber].y + 39);
                int t = rand.nextInt(Map.farmHeight);
                if (map.tiles[x][y].type == TileType.EMPTY){
                    int mod = t % 8;
                    if (mod == 0) {
                        map.tiles[x][y].type = TileType.FORAGING;
                    } else if (mod == 1 || mod == 2) {
                        map.tiles[x][y].type = TileType.TREE;
                    } else if (mod == 3 || mod == 4) {
                        map.tiles[x][y].type = TileType.STONE;
                    } else {
                        map.tiles[x][y].type = TileType.GRASS;
                    }
                }
            }
        }
    }
    public Result<String> BuildBuilding(Matcher matcher) {
        String buildingName = matcher.group("buildingNme");
        int x,y;
        Point farmStart = App.farmStart[App.getCurrentGame().turn];
        try{
            x = Integer.parseInt(matcher.group("x"));
            y = Integer.parseInt(matcher.group("y"));
        }
        catch(Exception e){
            return new Result<>(false, "Invalid Point !");
        }
        if (!((x > farmStart.x && x < farmStart.x + 50) && (y > farmStart.y && y < farmStart.y + 40) )){
            return new Result<>(false, "That part is not in Your Farm!");
        }
        if (!App.currentGame.map.tiles[x][y].type.equals(TileType.EMPTY)){
            return new Result<>(false, "You can not Build Here this point is a " + App.currentGame.map.tiles[x][y].type + " !");
        }
        if (buildingName.toLowerCase().equals("shipping_bin")){
            Building building = new Building(FarmBuilding.SHIPPING_BIN);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.SHIPPING_BIN;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        if (buildingName.toLowerCase().equals("shipping_bin")){
            Building building = new Building(FarmBuilding.SHIPPING_BIN);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.SHIPPING_BIN;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        else if (buildingName.toLowerCase().equals("deluxe_barn")){
            Building building = new Building(FarmBuilding.DELUXE_BARN);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.DELUXE_BARN;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        else if (buildingName.toLowerCase().equals("barn")){
            Building building = new Building(FarmBuilding.BARN);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.BARN;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        else if (buildingName.toLowerCase().equals("big_barn")){
            Building building = new Building(FarmBuilding.BIG_BARN);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.BIG_BARN;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        else if (buildingName.toLowerCase().equals("coop")){
            Building building = new Building(FarmBuilding.COOP);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.COOP;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        else if (buildingName.toLowerCase().equals("big_coop")){
            Building building = new Building(FarmBuilding.BIG_COOP);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.BIG_COOP;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        else if (buildingName.toLowerCase().equals("deluxe_coop")){
            Building building = new Building(FarmBuilding.DELUXE_COOP);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.DELUXE_COOP;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        else if (buildingName.toLowerCase().equals("well")){
            Building building = new Building(FarmBuilding.WELL);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.WELL;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }
        else if (buildingName.toLowerCase().equals("big_barn")){
            Building building = new Building(FarmBuilding.BIG_BARN);
            for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                if (item.getName().equals(building.getName())) {
                    App.currentGame.map.tiles[x][y].type = TileType.BIG_BARN;
                    App.currentGame.map.farms[App.currentGame.turn].BuildingMap.put(new Point(x,y), building);
                    item = new Item(item, item.getAmount() -1);
                    return new Result<>(true, "new building " + buildingName + " got Build at (" + x + ", " + y + ")");
                }
            }
        }

        return new Result<>(false, "You dont have that BUilding");
    }
    public void nextTurn(){

        for(Player player : App.getCurrentGame().getPlayers()) {
            if(player.getBuff() != null) {
                player.getBuff().setHour(player.getBuff().getHour() - 1);
                if(player.getBuff().getHour() == 0)
                    player.setBuff(null);
            }
        }

        App.currentGame.currentPlayer = App.getCurrentGame().getPlayers().get((App.currentGame.turn + 1)%(App.currentGame.players.size()));
        App.currentGame.turn = (App.currentGame.turn + 1)%(App.currentGame.players.size());
        System.out.println(App.getCurrentGame().currentPlayer.printNotifications());
        App.getCurrentGame().currentPlayer.resetNotifications();
        if ((App.currentGame.turn) == 0) {
            App.currentGame.time.setHour(App.currentGame.time.getHour() + 1);
            for (Machine machine : App.getCurrentGame().getMachines()) {
                if(machine.getCurrentOperation().getInput().getItemID() != 0 &&
                 machine.getCurrentOperation().getCurrentTime() != machine.getCurrentOperation().getReadyTime()) {
                    machine.getCurrentOperation().increaseCurrentTime(1);
                 }
            }
        }
        if (App.currentGame.time.getHour() == 22){
            App.currentGame.time.setHour(App.currentGame.time.getHour() + 11);
            setUpNextDay();
            return;
        }



    }

}
//