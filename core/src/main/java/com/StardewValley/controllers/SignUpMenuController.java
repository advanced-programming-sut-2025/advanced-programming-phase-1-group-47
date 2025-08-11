package com.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.StardewValley.DataBase.UserDBCommands;
import com.StardewValley.Main;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.User;
import com.StardewValley.View.*;
import com.StardewValley.View.GameModeSelectionView;
import com.StardewValley.controllers.GameModeSelectionController;

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

        try {
            if (commands.getUser(username) != null) {
                view.showMessage(false, "Username already exists. Please choose another.");
                isSigningUp = false;
                return;
            }
        } catch (Exception e) {
            // If database is not available, allow signup to proceed
            Gdx.app.log("SignUp", "Database check failed, proceeding with signup: " + e.getMessage());
        }

        view.getSignUpMenu().setDisabled(true);
        view.showMessage(true, "");

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    try {
                        // Create new user and save to database
                        User user = new User(username, password, "", "", null, securityQuestion, "");
                        
                        try {
                            commands.saveUser(user);
                            App.loggedUser = user;
                        } catch (SQLException e) {
                            // If database save fails, still create the user in memory
                            Gdx.app.log("SignUp", "Database save failed, using in-memory user: " + e.getMessage());
                            App.loggedUser = user;
                        }
                        
                        isSigningUp = false;
                        view.getSignUpMenu().setDisabled(false);
                        
                        // Redirect to game mode selection after successful signup
                        view.showMessage(true, "Sign up successful! Redirecting...");
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Gdx.app.postRunnable(() -> {
                                    Main.getMain().setScreen(new GameModeSelectionView(new GameModeSelectionController(), GameAssetManager.getGameAssetManager().getSkin()));
                                });
                            }
                        }, 1.5f);
                    } catch (Exception e) {
                        e.printStackTrace();
                        isSigningUp = false;
                        view.getSignUpMenu().setDisabled(false);
                        view.showMessage(false, "Error during signup: " + e.getMessage());
                    }
                });
            }
        }, 1.3f);
    }

    public boolean isValid(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@_()*&%$#]).{8,}$";
        return password.matches(pattern);
    }
}
