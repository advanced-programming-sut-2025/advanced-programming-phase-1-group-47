package controllers;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import models.*;
import models.Map;
import models.NPCs.*;
import models.buildings.Building;
import models.enums.Menu;
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
                        player.getInvetory().addItem(giftedItem);
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
                if(player.getFriendshipLevel().get(App.getCurrentGame().getCurrentPlayer()) > 1)
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
                        if(item.getValue() == -1)
                            return new Result<String>(false, "You Can't gift that Item!");
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
    public Result<String> giveFlower(String username) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equals(username)) {
                if(!isPlayerNear(player))
                    return new Result<>(false, "Player too far away!");
                if(player.getFriendshipLevel().get(currentPlayer)!=2 || player.getFriendshipXP().get(currentPlayer) < 300)
                    return new Result<>(false, "You are not in a relationship position of giving a flower");
                for(Item item : currentPlayer.getInvetory().getItems()) {
                    if(item.getItemID() == 202) {
                        player.getInvetory().addItem(item);
                        currentPlayer.getInvetory().getItems().remove(item);
                        player.setFriendshipLevel(currentPlayer , 3);
                        currentPlayer.setFriendshipLevel(player , 3);
                        player.setFriendshipXP(currentPlayer , 0);
                        currentPlayer.setFriendshipXP(player , 0);
                        return new Result<String>(true, "Flower Given!");
                    }
                }
                return new Result<String>(false, "You don't have a flower in your inventory!");

            }
        }
        return new Result<>(false, "can't find player username!");
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
    public Result<String> handleNewGame(Matcher matcher, Scanner scanner) {
            String player1 = matcher.group("player1");
            String player2 = matcher.group("player2");
            String player3 = matcher.group("player3");

            User u1 = App.findPlayer(player1);
            User u2 = App.findPlayer(player2);
            User u3 = App.findPlayer(player3);
            Player p1 = new Player(u1.getUsername(), u1.getPassword(), u1.getEmail(),u1.getNickname(),u1.getGender(),u1.getSecurityQuestion(),u1.getSecurityAnswer());
            Player p2 = new Player(u2.getUsername(), u2.getPassword(), u2.getEmail(), u2.getNickname(),u2.getGender(),u2.getSecurityQuestion(),u2.getSecurityAnswer());
            Player p3 = new Player(u3.getUsername(), u3.getPassword(), u3.getEmail(), u3.getNickname(),u3.getGender(),u3.getSecurityQuestion(),u3.getSecurityAnswer());


            String[] farmNames = new String[4];
            for(int i = 0; i < 4; i++) {
                String input = scanner.nextLine();
                if ((matcher = models.enums.commands.GameMenu.gamemap.getMatcher(input)) != null) {
                    farmNames[i] = matcher.group("mapNumber");
                }
            }
            Game newGame = new Game((Player)p1, (Player)p2, (Player)p3);
            Map newMap = new Map(farmNames);
            newGame.map = newMap;
            App.setCurrentGame(newGame);
            TileType lastTileType = TileType.COTTAGE;
            return new Result<>(true, "New game started with players: " + player1 + ", " + player2 + ", " + player3);
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
        int farmWidth = 50, farmHeight = 40;

        // مسیر قابل عبور (0) و غیرقابل عبور (1)
        int[][] grid = new int[160][120];
        Set<TileType> walkable = Set.of(TileType.EMPTY, TileType.STONE, TileType.TREE, TileType.PERSON, TileType.DOOR, TileType.FARMWALL);
        Game currentGame = App.currentGame;
        int turn = currentGame.turn;
        Point offset = App.farmStart[turn];
        Point personRel = App.currentGame.map.farms[turn].personPoint;

        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 120; j++) {
                TileType tileType = App.currentGame.map.tiles[i][j].type;
                grid[i][j] = (tileType != null && walkable.contains(tileType)) ? 0 : 1;
            }
        }

        // گرفتن مقصد از ورودی کاربر (مختصات محلی مزرعه)
        int targetX = Integer.parseInt(matcher.group("x"));
        int targetY = Integer.parseInt(matcher.group("y"));
        targetX -= offset.x;
        targetY -= offset.y;
        // مسیر‌یابی
        List<int[]> fullPath = shortestPath(grid, personRel.x, personRel.y, targetX, targetY);
        if (fullPath.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        List<int[]> turns = getTurns(fullPath);
        int energyCost = fullPath.size() + 10 * turns.size();
        int energyUnits = (int) Math.ceil(energyCost / 20.0);

        int currentEnergy = 20;
        System.out.println("You need " + energyUnits + " energy to get there. Do you want to go? (yes/no)");
        String input = scanner.nextLine();
        if (!input.equalsIgnoreCase("yes")) return;

        int usedEnergy = 0;
        Point lastRel = personRel;

        for (int k = 1; k < fullPath.size(); k++) {
            int[] curr = fullPath.get(k);
            int[] prev = fullPath.get(k - 1);

            boolean isTurn = (k > 1) && (
                    curr[0] - prev[0] != prev[0] - fullPath.get(k - 2)[0] ||
                            curr[1] - prev[1] != prev[1] - fullPath.get(k - 2)[1]
            );

            usedEnergy += isTurn ? 11 : 1;
            if (usedEnergy > currentEnergy * 20) {
                System.out.println("Energy ran out before reaching destination. Stopped at (" + curr[0] + ", " + curr[1] + ")");
                break;
            }

            // پاک‌سازی جای قبلی
            App.currentGame.map.tiles[offset.x + lastRel.x][offset.y + lastRel.y].type = App.currentGame.map.farms[turn].lastTileType;

            // ذخیره‌ی tile جدید
            App.currentGame.map.farms[turn].lastTileType = App.currentGame.map.tiles[offset.x + curr[0]][offset.y + curr[1]].type;

            // گذاشتن شخصیت در مکان جدید
            App.currentGame.map.tiles[offset.x + curr[0]][offset.y + curr[1]].type = TileType.PERSON;
            App.currentGame.map.farms[turn].personPoint = new Point(curr[0], curr[1]);
            lastRel = new Point(curr[0], curr[1]);
        }
//        checkEnteringMenu();
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
                if (counts[maxIndex] == 5){
                    String menu = types[maxIndex].toString();
                    if (menu.equals("BLACKSMITH") || menu.equals("JOJAMART") || menu.equals("PIERRESSTORE") || menu.equals("CARPENTER") ||
                            menu.equals("FISHSHOP") || menu.equals("MARNIESRANCH") || menu.equals("STARDROPSALOON")){
                        App.currentMenu = Menu.StoreMenu;
                    }
                    else if (menu.equals("GREENHOUSE")){
                        App.currentMenu = Menu.GreenHouseMenu;
                    }
                    else if (menu.equals("COTTTAGE")){
                        App.currentMenu = Menu.cottageMenu;
                    }
                }
//                System.out.println("Most common TileType around player: " + types[maxIndex] + " (" + counts[maxIndex] + " times)");
            }
        }
        System.out.println("Used energy: " + Math.min(currentEnergy,usedEnergy / 20 + 1) + " units.");
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
        return new Result<>(true, "Date: " +  Time.getSeason() + " / " + App.currentGame.time.getDayOfSeason());
    }

    public Result<String> showDayWeek(){
        return new Result<>(true, "Day week: " + Time.getDayWeek());
    }
    public Result<String> showSeason(){
        return new Result<>(true, "Season: " + String.valueOf(Time.getSeason()));
    }
    public Result<String> cheateAdvanceTime(Matcher matcher){
        int hour = Integer.parseInt(matcher.group("time"));
        if(hour<0)
            return new Result<>(false, "Invalid hour format (Time | Time > 0");
        Time.hour += hour;
        return new Result<>(false, "new Time: " + Time.getHour());
    }
    public Result<String> cheateAdvanceDate(Matcher matcher){
        int day = Integer.parseInt(matcher.group("day")) * 24;
        if(day < 0 )
            return new Result<>(false, "Invalid Day format (Day | Day > 0");
        Time.hour += day;
        return new Result<>(false, "new Day: " + App.currentGame.time.getDayOfSeason());
    }
    public Result<String> GiveGiftToNPC(NPC npc , Item gift) {
        return null;
    }
    public Result<String> FinishQuest(Invetory playerItems , int QuestIndex) {
        return null;
    }
    public Result<String> showCraftInfo(String itemName){
        StringBuilder output = new StringBuilder();
        Plant plant = null;
        for (int i = 302 ; i < 343;i++){
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
}