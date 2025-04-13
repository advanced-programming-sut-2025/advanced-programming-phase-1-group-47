package models;

import java.util.ArrayList;
import models.enums.Menu;

public class App {
    public static final ArrayList<User> users = new ArrayList<>();
    private ArrayList<Game> games;
    // private ArrayList<User> loggedUsers;
    private Menu menu = Menu.LoginMenu;
    private User loggedUser = null;
    public  void set_logged_in_user(User user){
        this.loggedUser = user;
    }
    public User get_logged_in_user(){
        return loggedUser;
    }
    public  Menu getMenu() {
        return menu;
    }
    public void changeMenu(Menu newMenu) {
        menu = newMenu;
    }
}