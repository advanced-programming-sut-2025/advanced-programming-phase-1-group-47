package models;

import java.util.ArrayList;
import models.enums.Menu;

public class App {

    public static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<Game> games = new ArrayList<>();

    public static User loggedInUser = null;

    public static ArrayList<Player> players = new ArrayList<>();

    public static Map gameMap;

    public static Menu currentMenu = Menu.LoginMenu;

    public static Game currentGame;

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setGameMap(Map gameMap) {
        App.gameMap = gameMap;
    }

    public static void setCurrentGame(Game newCurrentGame) {
        currentGame = newCurrentGame;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu newMenu) {
        currentMenu = newMenu;
    }

    public static User findPlayer(String playerName) {
        for (User user : App.getUsers()) {
            if (user.getUsername().equals(playerName)){
                return user;
            }
        }
        return null;
    }
    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void setGames(ArrayList<Game> newGames) {
        games = newGames;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }


    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    //when user wants to logOut or change turn, this should be runned to reset all features like current game,
    //current menu, loggedInUser and...
    //fill it pls
    @Override
    public String toString() {
        return "Game Number " + 2;
    }
    public static void logOut(){

    }

}