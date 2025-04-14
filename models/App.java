package models;

import java.util.ArrayList;
import models.enums.Menu;

public class App {
    public static final ArrayList<User> users = new ArrayList<>();

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        App.currentMap = currentMap;
    }
    private ArrayList<Game> games;
    private ArrayList<User> loggedUsers;
    private static Menu menu = Menu.LoginMenu;
    //private static User loggedUser = null;
    private static Game currentGame;
    private static Map currentMap;
    public  Menu getMenu() {
        return menu;
    }
    public void changeMenu(Menu newMenu) {
        menu = newMenu;
    }

    public ArrayList<User> getLoggedUsers() {
        return loggedUsers;
    }

    public void setLoggedUsers(ArrayList<User> loggedUsers) {
        this.loggedUsers = loggedUsers;
    }
}