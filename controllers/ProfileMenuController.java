package controllers;

import models.App;
import models.Result;

public class ProfileMenuController {

    public Result<String> ChangeUsername(String newUsername) {
        if(App.getUserByUsername(newUsername) != null){
            return new Result<>(false, "New username already Exists!\n");
        }

        if(!checkRegex(newUsername, "^[a-zA-Z0-9-]+$")) {
            return new Result<>(false, "New username format invalid!\n");
        }

        App.getLoggedInUser().setUsername(newUsername);
        return new Result<>(true, "Username changed successfully\n");

    }

    public Result<String> ChangeNickname(String newNickname) {
        App.getLoggedInUser().setNickname(newNickname);
        return new Result<>(true, "NickName changed successfully\n");
    }

    public Result<String> ChangePassword(String oldPassword, String newPassword) {
        if(!oldPassword.equals(App.getLoggedInUser().getPassword())){
            return new Result<>(false, "Password incorrect!\n");
        }

        if(!checkRegex(newPassword, "^[a-zA-Z0-9!#$%^&*()=+{}[]|\\/:;'\",<>?]+$")){
            return new Result<>(false, "Password format invalid!\n");
        }

        if(newPassword.equals(oldPassword)){
            return new Result<>(false, "New password can not be old password!\n");
        }

        App.getLoggedInUser().setPassword(newPassword);
        return new Result<>(true, "Password changed successfully!\n");
    }

    public Result<String> ChangeEmail(String newEmail) {
        if(!checkRegex(newEmail, "^[a-zA-Z][a-zA-Z0-9_.-]{3,7}@(?!-)[a-zA-Z][-a-zA-Z.]{1,5}[a-zA-Z](\\\\.org|\\\\.com|\\\\.net|\\\\.edu)$")) {
            return new Result<>(false, "New email format incorrect!\n");
        }

        if(App.getLoggedInUser().getEmail().equals(newEmail)){
            return new Result<>(false, "New email can not be old email!\n");
        }

        App.getLoggedInUser().setEmail(newEmail);
        return new Result<>(true, "Email changed successfully\n");
    }
    public Result<String> UserInfo() {
        App.getLoggedInUser().showInfo();
        return new Result<>(true, "");
    }

    public boolean checkRegex(String string, String Regex) {
        return string.matches(Regex);
    }


}
