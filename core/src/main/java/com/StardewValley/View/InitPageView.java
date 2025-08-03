package com.StardewValley.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.StardewValley.controllers.InitPageController;
import com.StardewValley.Main;
import com.StardewValley.model.App;
import static com.StardewValley.model.App.addClickListenerWithSound;
import static com.StardewValley.model.App.addHoverEffect;
import static com.StardewValley.model.App.addFlashingTitleLabel;

public class InitPageView implements Screen {
    private Stage stage;
    private final Table table;
    private final InitPageController controller;
    private Texture bgTexture;
    private Image background;

    private TextButton loginButton;
    private TextButton signupButton;
    private TextButton MainMenuButton;
    private TextButton multiplayerButton;
    private TextButton exitButton;

    public InitPageView(InitPageController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        loginButton = new TextButton("Login", skin);
        signupButton = new TextButton("Sign Up", skin);
        multiplayerButton = new TextButton("Multiplayer", skin);
        exitButton = new TextButton("Exit", skin);
        MainMenuButton = new TextButton("Main Menu", skin);
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), Main.getBatch());
        Gdx.input.setInputProcessor(stage);

        bgTexture = new Texture(Gdx.files.internal("BackGrounds/VahidInit.jpg"));
        bgTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);
        setupUI();
    }

    private void setupUI() {
        float screenWidth = stage.getViewport().getWorldWidth();
        float screenHeight = stage.getViewport().getWorldHeight();

        table.clear();
        table.setFillParent(true);
        table.align(Align.center);
        table.defaults().pad(15).width(screenWidth * 0.45f).height(screenHeight * 0.12f);
        table.add(loginButton).width(screenWidth * 0.30f).row();
        table.add(signupButton).width(screenWidth * 0.25f).row();
        table.add(multiplayerButton).width(screenWidth * 0.25f).row();
        table.add(MainMenuButton).width(screenWidth * 0.20f).row();
        table.add(exitButton).width(screenWidth * 0.15f).row();

        stage.addActor(table);

        addHoverEffect(loginButton);
        addHoverEffect(signupButton);
        addHoverEffect(multiplayerButton);
        addHoverEffect(exitButton);
        addHoverEffect(MainMenuButton);
        addClickListenerWithSound(loginButton, () -> controller.onLoginClicked());
        addClickListenerWithSound(signupButton, () -> controller.onSignupClicked());
        addClickListenerWithSound(multiplayerButton, () -> controller.onMultiplayerClicked());
        addClickListenerWithSound(exitButton, () -> controller.exit());
        addClickListenerWithSound(MainMenuButton, () -> {});
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        setupUI();
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    @Override
    public void hide() {
        if (stage != null) stage.clear();
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (bgTexture != null) bgTexture.dispose();
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getSignupButton() {
        return signupButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }
}
