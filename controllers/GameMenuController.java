package controllers;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

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
import models.buildings.Building;
import models.enums.Gender;
import models.enums.Menu;
import models.enums.Season;
import models.enums.TileType;
import models.enums.Weather;
import models.things.Item;
import models.things.relations.Gift;
import models.things.relations.Quest;
import models.things.tools.*;

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
        Player player = App.currentGame.currentPlayer;
        return new Result<>(false,String.valueOf(player.EnergyObject.getCurrentEnergy()));
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
    public Result<String> InventoryTrashTotal(Matcher matcher) {
        Player player = App.currentGame.currentPlayer;
        String itemName = matcher.group("itemName");
        Item item = player.getInvetory().findItemFromName(itemName);
        if (item == null)
            return new Result<>(false,"Item not found");
        player.getInvetory().removeItem(item);
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
                        player.getInvetory().addItem(item);
                        currentPlayer.getInvetory().getItems().remove(item);
                        player.setFriendshipLevel(currentPlayer , 4);
                        currentPlayer.setFriendshipLevel(player , 4);
                        player.setFriendshipXP(currentPlayer , 0);
                        currentPlayer.setFriendshipXP(player , 0);
                        currentPlayer.setPartner(player);
                        player.setPartner(currentPlayer);
                        player.addNotifToNotifications("You are now married to " + currentPlayer.getUsername() + "!!!!!!");
                        return new Result<String>(true, "You are now married!");
                    }
                }
                return new Result<String>(false, "No ring!");
            }
        }
        return new Result<String>(false, "player not found!");
    }
//    public Result<String>()
  
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
                TileType.GRASS
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
        Player player = App.currentGame.getPlayers().get((turn + 1) % 4);

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
            if (usedEnergy > player.EnergyObject.getCurrentEnergy() * 20) {
                System.out.printf("Energy ran out before reaching destination. Stopped at (%d, %d)%n", curr[0], curr[1]);
                break;
            }

            // حذف شخصیت از جای قبلی و بازیابی نوع قبلی
            game.map.tiles[lastAbs.x][lastAbs.y].type = game.map.farms[turn].lastTileType;

            // ذخیره‌ی tile جدید
            TileType newType = game.map.tiles[curr[0]][curr[1]].type;
            game.map.farms[turn].lastTileType = newType;

            // قرار دادن شخصیت در مکان جدید
            game.map.tiles[curr[0]][curr[1]].type = TileType.PERSON;

            // به‌روزرسانی مکان نسبی در مزرعه (برای سازگاری با سیستم)
            game.map.farms[turn].personPoint = new Point(curr[0] - offset.x, curr[1] - offset.y);

            lastAbs = new Point(curr[0], curr[1]);
        }

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

                if (counts[maxIndex] == 5) {
                    String menu = types[maxIndex].toString();
                    switch (menu) {
                        case "BLACKSMITH", "JOJAMART", "PIERRESSTORE", "CARPENTER",
                             "FISHSHOP", "MARNIESRANCH", "STARDROPSALOON" -> App.currentMenu = Menu.StoreMenu;
                        case "GREENHOUSE" -> App.currentMenu = Menu.GreenHouseMenu;
                        case "COTTTAGE" -> App.currentMenu = Menu.cottageMenu;
                    }
                }
            }
        }

        // کاهش انرژی به تعداد صحیح واحد انرژی مصرف‌شده
        int energyUnitsUsed = (int) Math.ceil(usedEnergy / 20.0);
        player.EnergyObject.setCurrentEnergy(player.EnergyObject.getCurrentEnergy() - energyUnitsUsed);
        System.out.println("Used energy: " + energyUnitsUsed + " units.");
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

    public Result<String> plantPlant (String seedName , String direction) {
        System.out.println("plantPlant " + seedName + " " + direction);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        for(Item item : currentPlayer.getInvetory().getItems()){
            if (item.getName().equalsIgnoreCase(seedName)) {
                if(item.getItemID() > 456 || item.getItemID() < 402)
                    return new Result<>(false, "the Item you are attempting to plant is not a Seed!");
                Plant basePlant = AllTheItemsInTheGame.getPlantById(item.getItemID() - 100);
                if (!basePlant.getSeasonOfGrowth().equals(App.currentGame.time.getSeason()))
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
                App.currentGame.map.tiles[target.x][target.y].type = TileType.PLANT;
                putPlantInGround(new Plant(basePlant, target));
                App.currentGame.map.farms[App.currentGame.turn].plantMap.put(target, basePlant);
                return new Result<>(true, "Plant " + item.getName() + " is now planted in (" + target.x + ", " + target.y +") cordinates !");
            }
        }
        return new Result<>(false, "You don't have that seed/Item!");
    }

    public Point getOffsetFromDirection(String direction) {
        switch (direction.toUpperCase()) {
            case "NORTH": return new Point(-1, 0);
            case "SOUTH": return new Point(1, 0);
            case "EAST": return new Point(0, 1);
            case "WEST": return new Point(0, -1);
            case "NORTH_EAST": return new Point(-1, 1);
            case "NORTH_WEST": return new Point(-1, -1);
            case "SOUTH_EAST": return new Point(1, 1);
            case "SOUTH_WEST": return new Point(1, -1);
            default: return null;
        }
    }

    public Result<String> putPlantInGround (Plant plant) {
        App.getCurrentGame().addPlantInPlants(plant);
        Point placeInMap = plant.getPoint();
        //Change tileType In Map @sarsar


        return new Result<>(true, "You have Planted the Plant!");
    }
    public Result<String> cheatItem(Matcher matcher) {
        String itemName = matcher.group("itemName");
        int amount;

        try {
            amount = Integer.parseInt(matcher.group("count"));
            if (amount <= 0) {
                return new Result<>(false, "Amount must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            return new Result<>(false, "Invalid amount.");
        }
        for (HashMap.Entry<Integer, Item> entry : AllTheItemsInTheGame.allItems.entrySet()) {
            Item item = entry.getValue();
            System.out.println(item.getItemID() + " " + item.getName() + " " + item.getAmount());
            if (item.getName().equalsIgnoreCase(itemName)) {
                // ساخت نسخه‌ی جدید از آیتم
                Item copiedItem = item;
                copiedItem.setAmount(amount);

                App.getCurrentGame()
                        .getCurrentPlayer()
                        .getInvetory()
                        .getItems()
                        .add(copiedItem);

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
        if(plant.getCurrentStage() != -1)
            return new Result<>(false, "Plant not ready to harvest yet!");
        if(!plant.isIsReUsable())
            App.getCurrentGame().getPlants().remove(plant);
        else{
            plant.setCurrentStage(-2);
            plant.setCurrentStageCount(0);
        }
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(plant.harvestPlant());

        //@sarsar change tiltype back (Point at plant.getpoint)
        App.currentGame.map.tiles[plant.getPoint().x][plant.getPoint().y].type = TileType.EMPTY;

        App.currentGame.currentPlayer.skillProgress(0,5);
        //@amoojoey give player 5XP skill in farming
        return new Result<>(true, "Plant harvested");
        //@sarsar change tiltype back (Point at plant.getpoint)
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

    //har chi mikhaid update she too shab barai farda ro bezanid inja 
    public void setUpNextDay() {
        for (Plant plant : App.getCurrentGame().getPlants()) {
            if (plant.isHasBeenWatered())  plant.grow();
            if (App.getCurrentGame().tomarrowsWeather.equals(Weather.RAINY))
                plant.setHasBeenWatered(true);
            else
                plant.setHasBeenWatered(false);
        }
        //CHANGE WEATHER INTO TOMARROWS WEATHER AND CREATE NEW TOMARROWS WEATHER HERE
        App.currentGame.nextDayWeather();

        for (NPC npc : App.getCurrentGame().getNpcs()) {
            for(Player player : App.getCurrentGame().getPlayers()) {
                npc.getHasBeenGiftedTo().put(player, false);
                npc.getHasBeenTalkedTo().put(player, false);
                if (npc.getFriendship().get(player) >= 200)
                    npc.getQuest2().getIsActive().put(player, true);
            }
        }

        for(Player player1 : App.getCurrentGame().getPlayers())
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
    public void nextTurn(){
        App.currentGame.currentPlayer = App.getCurrentGame().getPlayers().get((App.currentGame.turn + 1)%(App.currentGame.players.size()));
        App.currentGame.turn = (App.currentGame.turn + 1)%(App.currentGame.players.size());
        System.out.println(App.getCurrentGame().currentPlayer.printNotifications());
        App.getCurrentGame().currentPlayer.resetNotifications();
        if ((App.currentGame.turn) == 0) {
            App.currentGame.time.setHour(App.currentGame.time.getHour() + 1);
        }
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

}
//