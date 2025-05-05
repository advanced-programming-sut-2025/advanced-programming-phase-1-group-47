package controllers;

import models.App;
import models.Result;
import models.enums.Menu;

public class ProfileMenuController {

    public Result<String> ChangeUsername(String newUsername) {

        if(App.getUserByUsername(newUsername) != null){
            return new Result<>(false, "New username already Exists!");
        }

        if(!checkRegex(newUsername, "^[a-zA-Z0-9-]+$")) {
            return new Result<>(false, "New username format invalid!");
        }

        App.getLoggedInUser().setUsername(newUsername);

        return new Result<>(true, "Username changed successfully!");

    }

    public Result<String> ChangeNickname(String newNickname) {
        App.getLoggedInUser().setNickname(newNickname);
        return new Result<>(true, "NickName changed successfully!");
    }

    public Result<String> ChangePassword(String oldPassword, String newPassword) {
        if(!oldPassword.equals(App.getLoggedInUser().getPassword())){
            return new Result<>(false, "Password incorrect!");
        }

        if(!checkRegex(newPassword, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,}$")){
            return new Result<>(false, "Password format invalid!");
        }

        if(newPassword.equals(oldPassword)){
            return new Result<>(false, "New password can not be old password!");
        }

        App.getLoggedInUser().setPassword(newPassword);
        return new Result<>(true, "Password changed successfully!");
    }

    public Result<String> ChangeEmail(String newEmail) {
        if(!checkRegex(newEmail, "^[a-zA-Z][a-zA-Z0-9_.-]{1,63}@(?!-)[a-zA-Z][-a-zA-Z.]{2,63}\\.(org|com|net|edu)$")) {
            return new Result<>(false, "New email format incorrect!");
        }

        if(App.getLoggedInUser().getEmail().equals(newEmail)){
            return new Result<>(false, "New email can not be old email!");
        }

        App.getLoggedInUser().setEmail(newEmail);
        return new Result<>(true, "Email changed successfully!");
    }
    public Result<String> UserInfo() {
        App.getLoggedInUser().showInfo();
        return new Result<>(true, "");
    }

    public Result<String> ShowCurrentMenu() {
        System.out.println(App.getCurrentMenu().toString());
        return new Result<>(true, "");
    }

    public Result<String> EnterMenu(String menu_name) {

        switch (menu_name) {
            case "loginMenu" -> {
                App.setCurrentMenu(Menu.LoginMenu);
                App.setLoggedInUser(null);
                return new Result<>(true, "You are now in loginMenu!\n");
            }
            case "mainleMenu" -> {
                App.setCurrentMenu(Menu.MainMenu);
                return new Result<>(true, "You are now in mainMenu!\n");
            }
            case "gameMenu" -> {
                App.setCurrentMenu(Menu.GameMenu);
                return new Result<>(true, "You are now in gameMenu!\n");
            }
        }

        return new Result<>(false, "Invalid menu name!\n");
    }

    public boolean checkRegex(String string, String Regex) {
        return string.matches(Regex);
    }


}
