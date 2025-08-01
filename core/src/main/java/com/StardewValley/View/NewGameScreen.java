//package com.StardewValley.View;
//
//import com.StardewValley.model.App;
//import com.StardewValley.model.GameAssetManager;
//import com.StardewValley.model.Player;
//import com.StardewValley.model.User;
//import com.badlogic.gdx.*;
//import com.badlogic.gdx.graphics.*;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.*;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//import com.badlogic.gdx.utils.viewport.*;
//
//import java.util.*;
//import java.util.List;
//
//public class NewGameScreen implements Screen {
//
//    private Stage stage;
//    private Skin skin;
//    private SpriteBatch batch;
//    private Game game; // Your main Game class
//    private TextButton startButton;
//    private SelectBox<String> playerBox1, playerBox2, playerBox3;
//    private SelectBox<String> mapBox1, mapBox2, mapBox3;
//
//    private List<User> allUsers;
//
//    public NewGameScreen() {
//        batch = new SpriteBatch();
//        stage = new Stage(new ScreenViewport(), batch);
//        Gdx.input.setInputProcessor(stage);
//        skin = GameAssetManager.getGameAssetManager().getSkin(); // فرض بر اینه که داریش
//
//        allUsers = App.getUsers(); // لیست کاربران
//
//        createUI();
//    }
//
//    private void createUI() {
//        Table table = new Table();
//        table.setFillParent(true);
//        stage.addActor(table);
//
//        Label title = new Label("Start New Game", skin);
//        table.add(title).colspan(2).pad(20);
//        table.row();
//
//        playerBox1 = createUserBox();
//        playerBox2 = createUserBox();
//        playerBox3 = createUserBox();
//
//        mapBox1 = createMapBox();
//        mapBox2 = createMapBox();
//        mapBox3 = createMapBox();
//
//        table.add(new Label("Player 1:", skin));
//        table.add(playerBox1).pad(5);
//        table.row();
//        table.add(new Label("Map 1:", skin));
//        table.add(mapBox1).pad(5);
//        table.row();
//
//        table.add(new Label("Player 2:", skin));
//        table.add(playerBox2).pad(5);
//        table.row();
//        table.add(new Label("Map 2:", skin));
//        table.add(mapBox2).pad(5);
//        table.row();
//
//        table.add(new Label("Player 3:", skin));
//        table.add(playerBox3).pad(5);
//        table.row();
//        table.add(new Label("Map 3:", skin));
//        table.add(mapBox3).pad(5);
//        table.row();
//
//        startButton = new TextButton("Start Game", skin);
//        startButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                startGame();
//            }
//        });
//
//        table.add(startButton).colspan(2).padTop(20);
//    }
//
//    private SelectBox<String> createUserBox() {
//        SelectBox<String> box = new SelectBox<>(skin);
//        List<String> usernames = allUsers.stream().map(User::getUsername).toList();
//        box.setItems(usernames.toArray(new String[0]));
//        return box;
//    }
//
//    private SelectBox<String> createMapBox() {
//        SelectBox<String> box = new SelectBox<>(skin);
//        box.setItems("1", "2", "3"); // اگر مپ‌های خاصی داری اینجا بگذار
//        return box;
//    }
//
//    private void startGame() {
//        List<Player> players = new ArrayList<>();
//        String[] selectedPlayers = {
//                playerBox1.getSelected(),
//                playerBox2.getSelected(),
//                playerBox3.getSelected()
//        };
//
//        Set<String> uniqueNames = new HashSet<>();
//
//        for (String name : selectedPlayers) {
//            if (name != null && !name.isEmpty() && uniqueNames.add(name)) {
//                User user = App.findPlayer(name);
//                if (user != null) {
//                    Player player = new Player(
//                            user.getUsername(), user.getPassword(), user.getEmail(),
//                            user.getNickname(), user.getGender(),
//                            user.getSecurityQuestion(), user.getSecurityAnswer()
//                    );
//                    players.add(player);
//                }
//            }
//        }
//
//        if (players.isEmpty()) return;
//
//        // انتخاب مپ‌ها
//        String[] farmNames = new String[players.size() + 1];
//        if (players.size() > 0) farmNames[0] = mapBox1.getSelected();
//        if (players.size() > 1) farmNames[1] = mapBox2.getSelected();
//        if (players.size() > 2) farmNames[2] = mapBox3.getSelected();
//
//        // ساخت بازی و رفتن به صفحه گیم
//        com.StardewValley.model.Game newGame = new com.StardewValley.model.Game(players);
//        com.StardewValley.model.Map newMap = new com.StardewValley.model.Map(farmNames);
//        newGame.map = newMap;
//        App.setCurrentGame(newGame);
//
//        for (Player player : App.getCurrentGame().getPlayers()) {
//            player.setupRelations();
//        }
//
//        App.currentGame.setNpc();
//
//        game.setScreen(new GameScreen()); // رفتن به صفحه‌ی اصلی بازی
//    }
//
//    @Override public void show() {}
//    @Override public void render(float delta) {
//        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.act(delta);
//        stage.draw();
//    }
//    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
//    @Override public void pause() {}
//    @Override public void resume() {}
//    @Override public void hide() {}
//    @Override public void dispose() {
//        stage.dispose();
//        batch.dispose();
//        skin.dispose();
//    }
//}
