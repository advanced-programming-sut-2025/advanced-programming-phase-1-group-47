package com.StardewValley.model.enums;

import com.StardewValley.View.AppMenu;
import com.StardewValley.View.LoginView;
import com.StardewValley.View.SignUpView;
import com.StardewValley.controllers.LoginMenuController;
import com.StardewValley.controllers.SignUpMenuController;
import com.StardewValley.model.GameAssetManager;
import com.badlogic.gdx.Screen;

import java.util.Scanner;


public enum Menu {
    LoginMenu(new LoginView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin())),
    SignUpMenu(new SignUpView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin())),
    ;
    private final Screen menu;

    Menu(Screen menu) {
        this.menu = menu;
    }

}
