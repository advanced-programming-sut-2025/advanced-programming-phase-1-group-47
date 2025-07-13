package com.StardewValley.controllers;

import com.StardewValley.Main;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.User;
import com.StardewValley.View.*;

public class MainMenuController {
    public void logout(){
        App.loggedUser = null;
        Main.getMain().setScreen(new LoginView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));

    }
}
