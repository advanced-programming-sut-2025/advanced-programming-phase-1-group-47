package com.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.StardewValley.DataBase.UserDBCommands;
import com.StardewValley.Main;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.User;
import com.StardewValley.View.InitPageView;
import com.StardewValley.View.LoginView;
import com.StardewValley.View.GameModeSelectionView;
import com.StardewValley.controllers.GameModeSelectionController;

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
            // If database is not available, show appropriate message
            view.showMessage(false, "Database connection failed. Please try again later.");
            return;
        }

        view.showMessage(true, "login successful!");
        // Redirect to game mode selection after successful login
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    Main.getMain().setScreen(new GameModeSelectionView(new GameModeSelectionController(), GameAssetManager.getGameAssetManager().getSkin()));
                });
            }
        }, 1.5f);
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
        
        // Redirect to game mode selection after successful password recovery
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    Main.getMain().setScreen(new GameModeSelectionView(new GameModeSelectionController(), GameAssetManager.getGameAssetManager().getSkin()));
                });
            }
        }, 1.5f);
    }

    public void back() {
        Main.getMain().setScreen(new InitPageView(new InitPageController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
