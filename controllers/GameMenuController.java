package controllers;
import java.util.Random;
import java.util.regex.Matcher;

import models.*;
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
    public Result<String> handleNewGame(Matcher matcher) {
        String player1 = matcher.group("player1");
        String player2 = matcher.group("player2");
        String player3 = matcher.group("player3");
        Player p1 = new Player(player1);
        Player p2 = new Player(player2);
        Player p3 = new Player(player3);

        Game newGame = new Game(new Map(), p1, p2, p3);
        App.setCurrentGame(newGame);

        return new Result<>(true, "New game started with players: " + player1 + ", " + player2 + ", " + player3);
    }
    public Result<String> GiveGiftToNPC(NPC npc , Item gift) {
        return null;
    }
    public Result<String> FinishQuest(Invetory playerItems , int QuestIndex) {
        return null;
    }
}
