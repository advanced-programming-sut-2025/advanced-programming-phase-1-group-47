package com.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.StardewValley.DataBase.UserDBCommands;
import com.StardewValley.Main;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.User;
import com.StardewValley.View.InitPageView;
import com.StardewValley.View.LoginView;

public class LoginMenuController {
    private LoginView view;

    public void setView(LoginView view) {
        this.view = view;
    }

    public void login(String username, String password) {
        try {
            User user = UserDBCommands.getUser(username);
            if (user == null || !user.getPassword().equals(password)) {
                view.showMessage(false, "invalid username or password");
                return;
            }
            App.loggedUser = user;
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.error("Error: ", "An error occurred during login: " + e.getMessage());
            return;
        }

        view.showMessage(true, "login successful!");
    }

    public void forgotPassword(String username) {
        User user = UserDBCommands.getUser(username);
        if (user == null) {
            view.showMessage(false, "Username Doesnt Exist");
            return;
        }
        view.fogetPassWindow(user);
    }

    public void checkQuestion(String answer, User user) {
        if (!user.getSecurityQuestion().equals(answer)) {
            view.showMessage(false, "Wrong");
            return;
        }

        String welcomeMsg = "Welcome " +  user.getUsername();
        view.showMessage(true, welcomeMsg);

        App.loggedUser = user;
    }

    public void back() {
        Main.getMain().setScreen(new InitPageView(new InitPageController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
