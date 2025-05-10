package controllers;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

import models.*;
import models.Map;
import models.NPCs.*;
import models.buildings.Building;
import models.enums.Season;
import models.enums.TileType;
import models.enums.commands.GameMenu;
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
                if (i == 50 || i == 110 || j == 40 || j == 80) {
                    System.out.print("🧱");
                    continue;
                }
                System.out.print(type != TileType.EMPTY ? type.getSticker() : "++");
            }
            System.out.println();
        }
    }
    public void walk(Matcher matcher, Scanner scanner) {
        int[][] grid = new int[50][40];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 40; j++) {
                Set<TileType> walkable = Set.of(TileType.EMPTY, TileType.STONE, TileType.TREE,TileType.COTTAGE, TileType.PERSON);
                if (walkable.contains(App.currentGame.map.tiles[i][j].type)) {
                    grid[i][j] = 0;
                }
                else {
                    grid[i][j] = 1;
                }
            }
        }

        Point personPoint = App.currentGame.map.farms[App.currentGame.turn].personPoint;
        int startX = personPoint.x, startY = personPoint.y;

        List<int[]> path = shortestPath(grid, startX, startY, Integer.parseInt(matcher.group("x")),
                Integer.parseInt(matcher.group("y")));
        List<int[]> turns = getTurns(path);
        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("Turns in the path:" + turns.size());
            System.out.println("Shortest path length: " + (path.size() - 1));
            System.out.println("Path:");
            for (int[] p : path) {
                System.out.println(Arrays.toString(p));
            }
            for (int k = 1; k < path.size(); k++) {
                int[] p = path.get(k);
                App.currentGame.map.tiles[personPoint.x][personPoint.y].type = App.currentGame.map.farms[App.currentGame.turn].getLastTileType();
                App.currentGame.map.tiles[personPoint.x][personPoint.y].type = App.currentGame.map.farms[App.currentGame.turn].lastTileType = App.currentGame.map.tiles[p[0]][p[1]].type;
                App.currentGame.map.tiles[p[0]][p[1]].type = TileType.PERSON;
                personPoint = new Point(p[0], p[1]);
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

        while (!queue.isEmpty()) {
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
        return new Result<>(true, "Spended Hours : " + Time.getHour()%24);
    }
    public Result<String> showDate(){
        return new Result<>(true, "Date: " +  Time.getSeason() + " / " + Time.getDayOfMonth());
    }
    public Result<String> showDatetime(){
        return new Result<>(true, "Date: " +  Time.getSeason() + "/" + Time.getDayOfMonth() + " ---" + Time.getHour() + ": 00");
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
        return new Result<>(false, "new Day: " + Time.getDayOfMonth());
    }
    public Result<String> GiveGiftToNPC(NPC npc , Item gift) {
        return null;
    }
    public Result<String> FinishQuest(Invetory playerItems , int QuestIndex) {
        return null;
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
