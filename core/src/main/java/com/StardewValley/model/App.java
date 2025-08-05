package com.StardewValley.model;

import java.util.ArrayList;
import java.util.Random;

import com.StardewValley.model.enums.Menu;
import com.StardewValley.model.enums.TileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

public class App {
    public static User loggedUser;
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Player> players = new ArrayList<>();
    public static User loggedInUser = null;
    public static Point[] farmStart = new Point[]{new Point(0,0), new Point(110,0), new Point(0,80), new Point(110,80),};
    public static Game currentGame = null;
    public static ArrayList<Game> games;
    public static OrthographicCamera camera;
    public static Viewport viewport;
    public static SpriteBatch batch;
    public static Menu currentMenu = Menu.LoginMenu;

    public static int giftIdCounter = 0;
    public static int tradeIdCounter = 0;

    public static boolean isNpcTile(TileType type) {
        return type == TileType.ROBIN ||
                type == TileType.ABIGEL ||
                type == TileType.LEAH ||
                type == TileType.SEBASTIAN ||
                type == TileType.HARVEY;
    }

    // باید این‌ها رو بیرون از متد تعریف کرده باشی (مثلاً بالای کلاس)
    public static Texture snowflakeTexture = new Texture(Gdx.files.internal("Weather/Snow.png"));
    public static Vector2 snowPos1 = new Vector2();
    public static Vector2 snowPos2 = new Vector2();
    public static float snowSpeed1 = 50f;
    public static float snowSpeed2 = 70f;
    public static boolean snowInitialized = false;


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
    static{

    }
    public static void addHoverEffect(final TextButton button) {
        button.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                button.setColor(0.7f, 0.65f, 0.5f, 1f);
                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setColor(1f, 1f, 1f, 1f); // رنگ پیش‌فرض
            }
        });
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
    public static void addClickListenerWithSound(TextButton button, Runnable action) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
    }
    public static void addFieldWithPlaceholder(Table table, TextField field, Label placeholder) {
        Stack stack = new Stack();
        stack.add(field);
        stack.add(placeholder);
        table.add(stack).left();
    }
}
