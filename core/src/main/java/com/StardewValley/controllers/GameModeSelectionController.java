package com.StardewValley.controllers;

import com.StardewValley.Main;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.View.*;
import com.StardewValley.View.GameScreen;
import com.StardewValley.View.InitPageView;
import com.StardewValley.controllers.InitPageController;

public class GameModeSelectionController {
    private GameModeSelectionView view;

    public void setView(GameModeSelectionView view) {
        this.view = view;
    }

    public void onSingleplayerClicked() {
        // Start singleplayer game - this is the current game flow
        Main.getMain().setScreen(new GameScreen());
    }

    public void onMultiplayerClicked() {
        // Start multiplayer lobby
        try {
            Main.getMain().setScreen(new LobbyView());
        } catch (Exception e) {
            System.err.println("Failed to start multiplayer lobby: " + e.getMessage());
            // Fallback to singleplayer if multiplayer fails
            Main.getMain().setScreen(new GameScreen());
        }
    }

    public void onBackClicked() {
        // Go back to main menu
        Main.getMain().setScreen(new InitPageView(new InitPageController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
