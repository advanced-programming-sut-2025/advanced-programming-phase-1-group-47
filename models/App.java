package models;

import java.util.ArrayList;
import models.enums.Menu;

public class App {

    private static ArrayList<User> users = new ArrayList<>();

    private static ArrayList<Game> games = new ArrayList<>();

    private static User loggedInUser = null;

    private static Menu currentMenu = Menu.LoginMenu;

    private static Game currentGame;

    public static Game getCurrentGame() {
        return currentGame;
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
    public static void logOut(){

    }

}