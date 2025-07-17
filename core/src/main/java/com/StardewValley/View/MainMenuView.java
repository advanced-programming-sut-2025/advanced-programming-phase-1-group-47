//package com.StardewValley.View;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Pixmap;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.*;
//import com.badlogic.gdx.scenes.scene2d.InputListener;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.Align;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.StardewValley.controllers.MainMenuController;
//import com.StardewValley.Main;
//import com.StardewValley.model.App;
//import com.StardewValley.model.User;
//
//import java.util.ArrayList;
//
//public class MainMenuView implements Screen {
//    private Stage stage;
//    private Table table;
//    private final ArrayList<TextButton> menuButtons = new ArrayList<>();
//    private Label messageLabel;
//    private Texture bgTexture;
//    private Image background;
//    private Skin skin;
//    private final MainMenuController controller;
//
//    public MainMenuView(MainMenuController controller, Skin skin) {
//        this.controller = controller;
//        this.skin = skin;
//
//        controller.setView(this);
//    }
//
//    @Override
//    public void show() {
//        if (stage == null) {
//            stage = new Stage(new ScreenViewport(), Main.getBatch());
//        }
//        Gdx.input.setInputProcessor(stage);
//
//        if (bgTexture != null) {
//            bgTexture.dispose();
//            bgTexture = null;
//        }
//
//        if (Gdx.files.internal("BackGrounds/MainMenubg.jpg").exists()) {
//            try {
//                bgTexture = new Texture(Gdx.files.internal("BackGrounds/MainMenubg.jpg"));
//            } catch (Exception e) {
//                System.err.println("Error loading background texture: " + e.getMessage());
//                bgTexture = createEmptyTexture();
//            }
//        } else {
//            System.err.println("Background image not found: BackGrounds/MainMenubg.jpg");
//            bgTexture = createEmptyTexture();
//        }
//
//        if (background != null) {
//            background.remove();
//        }
//        background = new Image(bgTexture);
//        background.setFillParent(true);
//        stage.addActor(background);
//
//        setupUI();
//
//        App.addFlashingTitleLabel(stage, skin, language.MAIN_MENU_TITLE.get());
//
//        if (messageLabel == null) {
//            messageLabel = new Label("", skin);
//            messageLabel.setFontScale(1.1f);
//            messageLabel.setVisible(false);
//            messageLabel.setAlignment(Align.center);
//            stage.addActor(messageLabel);
//        }
//    }
//
//    private Texture createEmptyTexture() {
//        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//        pixmap.setColor(Color.BLACK);
//        pixmap.fill();
//        Texture tex = new Texture(pixmap);
//        pixmap.dispose();
//        return tex;
//    }
//
//    private void setupUI() {
//        if (table != null) {
//            table.clear();
//            table.remove();
//        }
//
//        table = new Table();
//        table.setFillParent(true);
//        table.pad(40);
//
//        float buttonWidth = stage.getViewport().getWorldWidth() * 0.25f;
//        float buttonHeight = stage.getViewport().getWorldHeight() * 0.08f;
//
//        // سمت چپ: اطلاعات کاربر
//        Table leftTable = new Table();
//        leftTable.top().left();
//
//        User currentUser = App.loggedUser;
//        Image avatarImage;
//        try {
//            avatarImage = new Image(new Texture(Gdx.files.internal(currentUser.getAvatarPath())));
//        } catch (Exception e) {
//            System.err.println("Error loading avatar image, using default. Details: " + e.getMessage());
//            avatarImage = new Image(new Texture(Gdx.files.internal("Avatars/default.jpg")));
//        }
//        avatarImage.setSize(100, 100);
//        avatarImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));
//
//        Label usernameLabel = new Label(language.USER_LABEL.get() + currentUser.getUsername(), skin);
//        Label scoreLabel = new Label(language.SCORE_LABEL.get() + currentUser.getScore(), skin);
//        Label killLabel = new Label(language.KILL_LABEL.get() + currentUser.getKillNumber(), skin);
//        Label timeLabel = new Label(language.BEST_TIME_LABEL.get() + currentUser.getMostSurvivalTime() + "s", skin);
//
//        for (Label label : new Label[]{usernameLabel, scoreLabel, killLabel, timeLabel}) {
//            label.setFontScale(1.2f);
//        }
//
//        leftTable.add(avatarImage).size(100, 100).padBottom(20).row();
//        leftTable.add(usernameLabel).pad(5).left().row();
//        leftTable.add(scoreLabel).pad(5).left().row();
//        leftTable.add(killLabel).pad(5).left().row();
//        leftTable.add(timeLabel).pad(5).left().row();
//
//        // سمت راست: دکمه‌ها
//        Table rightTable = new Table();
//        rightTable.top().padLeft(80);
//        rightTable.defaults().width(buttonWidth).height(buttonHeight).pad(15);
//
//        int delay = 0;
//        for (int i = 0; i < menuButtons.size(); i++) {
//            TextButton button = menuButtons.get(i);
//
//            button.getLabel().setFontScale(1.2f);
//            button.getColor().a = 0f;
//
//            button.addAction(Actions.sequence(
//                Actions.delay(delay * 0.1f),
//                Actions.parallel(
//                    Actions.fadeIn(0.7f),
//                    Actions.moveBy(100, 0, 0.6f)
//                )
//            ));
//
//            addHoverEffect(button);
//
//            final int index = i;
//            addClickListenerWithSound(button, () -> handleButtonClick(index));
//
//            rightTable.add(button).fillX().row();
//            delay++;
//        }
//
//        table.add(leftTable).top().left().expandX();
//        table.add().width(100).expand();
//        table.add(rightTable).top().left().expandX();
//
//        stage.addActor(table);
//
//        // تنظیم محل پیام
//        if (messageLabel != null) {
//            messageLabel.setSize(400, 40);
//            messageLabel.setPosition(stage.getViewport().getWorldWidth() / 2f, stage.getViewport().getWorldHeight() * 0.1f, Align.center);
//        }
//    }
//
//    private void handleButtonClick(int buttonIndex) {
//        switch (buttonIndex) {
//            case 0:
//                controller.continueLastGame();
//                break;
//            case 1:
//                controller.startNewGame();
//                break;
//            case 2:
//                controller.openSettings();
//                break;
//            case 3:
//                controller.showScoreboard();
//                break;
//            case 4:
//                controller.openProfile();
//                break;
//            case 5:
//                controller.showTutorial();
//                break;
//            case 6:
//                controller.logout();
//                break;
//            default:
//                System.err.println("Unknown button index: " + buttonIndex);
//                break;
//        }
//    }
//
//    private void addClickListenerWithSound(TextButton button, Runnable action) {
//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                button.addAction(Actions.sequence(
//                    Actions.scaleTo(0.95f, 0.95f, 0.05f),
//                    Actions.scaleTo(1f, 1f, 0.05f)
//                ));
//                if (App.sfxPlaying)
//                    App.playMusic("Music/MenuChange.mp3", 0.7f, false);
//                action.run();
//            }
//        });
//    }
//
//    private void addHoverEffect(final TextButton button) {
//        button.addListener(new InputListener() {
//            @Override
//            public boolean mouseMoved(InputEvent event, float x, float y) {
//                button.setColor(0.7f, 0.85f, 1f, 1f);
//                return true;
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                button.setColor(1f, 1f, 1f, 1f);
//            }
//        });
//    }
//
//    public void showMessage(boolean success, String message) {
//        if (messageLabel == null) {
//            System.err.println("Message label not initialized!");
//            return;
//        }
//        messageLabel.clearActions();
//        messageLabel.setText(message);
//        messageLabel.setColor(success ? Color.GREEN : Color.RED);
//        messageLabel.setVisible(true);
//        messageLabel.getColor().a = 1f;
//
//        messageLabel.addAction(Actions.sequence(
//            Actions.delay(3f),
//            Actions.fadeOut(0.5f),
//            Actions.run(() -> messageLabel.setVisible(false))
//        ));
//    }
//
//    @Override
//    public void render(float delta) {
//        ScreenUtils.clear(0, 0, 0, 1);
//        if (stage != null) {
//            stage.act(delta);
//            stage.draw();
//        }
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        if (stage != null) {
//            stage.getViewport().update(width, height, true);
//            setupUI();
//        }
//    }
//
//    @Override
//    public void pause() {}
//
//    @Override
//    public void resume() {}
//
//    @Override
//    public void hide() {
//        if (stage != null) {
//            stage.clear();
//        }
//    }
//
//    @Override
//    public void dispose() {
//        if (stage != null) {
//            stage.dispose();
//        }
//        if (bgTexture != null) {
//            bgTexture.dispose();
//        }
//    }
//}
