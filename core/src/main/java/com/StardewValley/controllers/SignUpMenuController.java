package com.StardewValley.controllers;

import java.sql.SQLException;

import com.StardewValley.DataBase.UserDBCommands;
import com.StardewValley.Main;
import com.StardewValley.View.InitPageView;
import com.StardewValley.View.LoginView;
import com.StardewValley.View.SignUpView;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;

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

        // Check if database is initialized
        if (com.StardewValley.DataBase.DataBaseInit.getConnection() == null) {
            view.showMessage(false, "Database not initialized. Please restart the game.");
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
            Gdx.app.error("SignUp", "Error checking existing user: " + e.getMessage());
            view.showMessage(false, "Database connection error. Please try again.");
            isSigningUp = false;
            return;
        }

        view.getSignUpMenu().setDisabled(true);
        view.showMessage(true, "Creating account...");

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    try {
                        // Create user with required parameters
                        User user = new User(username, password, username + "@example.com", username, 
                                           com.StardewValley.model.enums.Gender.Male, securityQuestion, securityQuestion);
                        commands.saveUser(user);
                        view.showMessage(true, "Account created successfully!");
                        
                        // Wait a moment then redirect to login
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Gdx.app.postRunnable(() -> {
                                    isSigningUp = false;
                                    view.getSignUpMenu().setDisabled(false);
                                    Main.getMain().setScreen(new LoginView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                                });
                            }
                        }, 1.0f);
                        
                    } catch (SQLException e) {
                        Gdx.app.error("SignUp", "Database error during signup: " + e.getMessage());
                        e.printStackTrace();
                        view.showMessage(false, "Database error: " + e.getMessage());
                        isSigningUp = false;
                        view.getSignUpMenu().setDisabled(false);
                    } catch (Exception e) {
                        Gdx.app.error("SignUp", "Unexpected error during signup: " + e.getMessage());
                        e.printStackTrace();
                        view.showMessage(false, "Unexpected error: " + e.getMessage());
                        isSigningUp = false;
                        view.getSignUpMenu().setDisabled(false);
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
