package controllers;

import models.App;
import models.Result;
import models.enums.Menu;

public class MainMenuController {

    public Result<String> EnterMenu(String menu_name) {

        switch (menu_name) {
            case "loginMenu" -> {
                App.setCurrentMenu(Menu.LoginMenu);
                App.setLoggedInUser(null);
                return new Result<>(true, "You are now in loginMenu!\n");
            }
            case "profileMenu" -> {
                App.setCurrentMenu(Menu.ProfileMenu);
                return new Result<>(true, "You are now in profileMenu!\n");
            }
            case "gameMenu" -> {
                App.setCurrentMenu(Menu.GameMenu);
                return new Result<>(true, "You are now in gameMenu!\n");
            }
        }

        return new Result<>(false, "Invalic menu name!\n");
    }

    public Result<String> ExitMenu() {
        return null;
    }

    public Result<String> ShowCurrentMenu() {
        App.getCurrentMenu().toString();
        return new Result<>(true, "");
    }

    public Result<String> LogOut() {
        App.setCurrentMenu(Menu.LoginMenu);
        App.setLoggedInUser(null);
        return new Result<>(true, "You are now in loginMenu!\n");
    }

}
