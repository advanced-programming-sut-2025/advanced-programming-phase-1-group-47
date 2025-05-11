package controllers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

import models.*;
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
        System.out.println(matcher.group("player1") + " " + matcher.group("player2"));
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
        App.setCurrentGame(newGame);
        Map newMap = new Map(farmNames);
        App.setGameMap(newMap);
        TileType lastTileType = TileType.COTTAGE;
        return new Result<>(true, "New game started with players: " + player1 + ", " + player2 + ", " + player3);
    }
    public void printMap() {
        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 120; j++) {
                TileType type = App.gameMap.tiles[i][j].type;
                if (i == 50 || i == 110 || j == 40 || j == 80) {
                    System.out.print("@@");
                    continue;
                }
                System.out.print(type != TileType.EMPTY ? type.getSticker() : "++");
            }
            System.out.println();
        }
    }
    public Result<String> showTime(){
        return new Result<>(true, "Spent Hours : " + Time.getHourOfDay());
    }
    public Result<String> showDate(){
        return new Result<>(true, "Date: " +  Time.getSeason() + " / " + Time.getDayOfSeason());
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
        return new Result<>(false, "new Day: " + Time.getDayOfSeason());
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

    public Result<String> crafting() {
        if(App.getCurrentGame().getCurrentPlayer().getEnergy() < 3) {
            return new Result<>(false, "Lack of energy!");
        }

        // fill here

        // this is for energy
        App.getCurrentGame().getCurrentPlayer().setEnergy(App.getCurrentGame().getCurrentPlayer().getEnergy() - 2);

        return null;
    }

    public Result<String> cooking() {
        if(App.getCurrentGame().getCurrentPlayer().getEnergy() < 4) {
            return new Result<>(false, "Lack of energy!");
        }

        // fill here

        // this is for energy
        App.getCurrentGame().getCurrentPlayer().setEnergy(App.getCurrentGame().getCurrentPlayer().getEnergy() - 3);

        return null;
    }
}
