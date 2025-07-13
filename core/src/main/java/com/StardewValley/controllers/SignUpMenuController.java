package com.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.StardewValley.DataBase.UserDBCommands;
import com.StardewValley.Main;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.User;
import com.StardewValley.View.*;

import java.sql.SQLException;

public class SignUpMenuController {
    private SignUpView view;
    private boolean isSigningUp = false;
    UserDBCommands commands = new UserDBCommands();

    public void setView(SignUpView view) {
        this.view = view;
    }

    public void back() {
        Main.getMain().setScreen(new InitPageView(new InitPageController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void signUp(String username, String password, String select, String securityQuestion) {
        if (isSigningUp) return;
        isSigningUp = true;

        if (username.isEmpty() || password.isEmpty() || securityQuestion.isEmpty()) {
            view.showMessage(false, "You Should Fill All Fields");
            isSigningUp = false;
            return;
        }

        if (!isValid(password)) {
            view.showMessage(false, "Password must be 8+ characters with uppercase, digit, and symbol.");
            isSigningUp = false;
            return;
        }

        if (commands.getUser(username) != null) {
            view.showMessage(false, "Username already exists. Please choose another.");
            isSigningUp = false;
            return;
        }

        view.getSignUpMenu().setDisabled(true);
        view.showMessage(true, "");

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
//                Gdx.app.postRunnable(() -> {
//                    try {
//                        commands.saveUser(user);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    isSigningUp = false;
//                    view.getSignUpMenu().setDisabled(false);
//                    Main.getMain().setScreen(new LoginView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
//                });
            }
        }, 1.3f);
    }

    public boolean isValid(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@_()*&%$#]).{8,}$";
        return password.matches(pattern);
    }
}
