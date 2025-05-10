package models;

import java.util.ArrayList;
import models.enums.Menu;

public class App {

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Player> players = new ArrayList<>();
    public static User loggedInUser = null;
    public static Point[] farmStart = new Point[]{new Point(0,0), new Point(110,0), new Point(0,80), new Point(110,80),};
    public static Game currentGame;
    public static ArrayList<Game> games;

    public static Menu currentMenu = Menu.LoginMenu;

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
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