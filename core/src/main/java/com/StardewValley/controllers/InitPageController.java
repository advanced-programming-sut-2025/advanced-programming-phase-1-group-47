package com.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.StardewValley.Main;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.View.*;

public class InitPageController {
    private InitPageView view;

    public void setView(InitPageView view) {
        this.view = view;
    }

    public void onLoginClicked() {
        Main.getMain().setScreen(new LoginView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void onSignupClicked() {
        Main.getMain().setScreen(new SignUpView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void exit(){
        Gdx.app.exit();
    }
    
    public void onLoginAsGuestClicked() {
        // Create a guest user and go to game mode selection
        // This allows guests to choose between singleplayer and multiplayer
        Main.getMain().setScreen(new GameModeSelectionView(new GameModeSelectionController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
