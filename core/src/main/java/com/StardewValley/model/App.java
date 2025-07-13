package com.StardewValley.model;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Random;

import com.StardewValley.model.enums.Menu;

public class App {
    public static User loggedUser;
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Player> players = new ArrayList<>();
    public static User loggedInUser = null;
    public static Point[] farmStart = new Point[]{new Point(0,0), new Point(110,0), new Point(0,80), new Point(110,80),};
    public static Game currentGame = null;
    public static ArrayList<Game> games;

    public static Menu currentMenu = Menu.LoginMenu;

    public static int giftIdCounter = 0;
    public static int tradeIdCounter = 0;


    public static void addGiftIdCounter() {
        giftIdCounter++;
    }
    public static void addTradeIdCounter() {
        tradeIdCounter++;
    }
    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu newMenu) {
        currentMenu = newMenu;
    }

    public static User findPlayer(String playerName) {
        for (User user : App.getUsers()) {
            if (user.getUsername().equals(playerName)){
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }


    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static int getTradeIdCounter() {
        return tradeIdCounter;
    }
    //when user wants to logOut or change turn, this should be runned to reset all features like current game,
    //current menu, loggedInUser and...
    //fill it pls
    @Override
    public String toString() {
        return "Game Number " + 2;
    }
    public static void logOut(){

    }

    public static int getGiftIdCounter() {
        return giftIdCounter;
    }


    public static String randomAvatar(){
        Random rand = new Random();
        return "Avatars/avatar" + (rand.nextInt(2) + 1) + ".png";
    }

    public static void addFlashingTitleLabel(Stage stage, Skin skin, String message) {
        Label titleLabel = new Label(message, skin);
        titleLabel.setFontScale(1.5f);
        titleLabel.setAlignment(Align.center);
        titleLabel.setColor(Color.WHITE);

        Container<Label> container = new Container<>(titleLabel);
        container.setSize(stage.getViewport().getWorldWidth(), 60);
        container.setPosition(0, stage.getViewport().getWorldHeight() - 60);

        container.setColor(new Color(1, 1, 1, 0f)); // شفاف کامل

        stage.addActor(container);

        // انیمیشن چشمک‌زن متن و کانتینر (برای کانتینر رنگ ترنسپرنت پس‌زمینه چشمک می‌زند)
        container.addAction(Actions.forever(
            Actions.sequence(
                Actions.parallel(
                    Actions.color(new Color(0, 0, 1, 0.5f), 1.0f), // پس زمینه آبی خیلی شفاف
                    Actions.run(() -> titleLabel.addAction(Actions.color(Color.RED, 1f))) // متن زرد
                ),
                Actions.delay(1f),
                Actions.parallel(
                    Actions.color(new Color(1, 0, 0, 0.2f), 1.0f), // پس زمینه قرمز خیلی شفاف
                    Actions.run(() -> titleLabel.addAction(Actions.color(Color.WHITE, 1f))) // متن سفید
                ),
                Actions.delay(0.7f)
            )
        ));
    }
}
