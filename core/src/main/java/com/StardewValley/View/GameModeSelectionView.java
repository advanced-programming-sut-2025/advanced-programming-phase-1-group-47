package com.StardewValley.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.StardewValley.controllers.GameModeSelectionController;
import com.StardewValley.Main;
import com.StardewValley.model.GameAssetManager;
import static com.StardewValley.model.App.addClickListenerWithSound;
import static com.StardewValley.model.App.addHoverEffect;
import static com.StardewValley.model.App.addFlashingTitleLabel;

public class GameModeSelectionView implements Screen {
    private Stage stage;
    private final Table table;
    private final GameModeSelectionController controller;
    private Texture bgTexture;
    private Image background;

    private TextButton singleplayerButton;
    private TextButton multiplayerButton;
    private TextButton backButton;
    private Label titleLabel;

    public GameModeSelectionView(GameModeSelectionController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        singleplayerButton = new TextButton("Singleplayer", skin);
        multiplayerButton = new TextButton("Multiplayer", skin);
        backButton = new TextButton("Back to Main Menu", skin);
        titleLabel = new Label("Choose Game Mode", skin);
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
        
        // Title
        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(2.0f);
        table.add(titleLabel).width(screenWidth * 0.8f).height(screenHeight * 0.15f).padBottom(30).row();
        
        // Game mode buttons
        table.defaults().pad(15).width(screenWidth * 0.45f).height(screenHeight * 0.12f);
        table.add(singleplayerButton).width(screenWidth * 0.35f).row();
        table.add(multiplayerButton).width(screenWidth * 0.35f).row();
        table.add(backButton).width(screenWidth * 0.25f).row();

        stage.addActor(table);

        addHoverEffect(singleplayerButton);
        addHoverEffect(multiplayerButton);
        addHoverEffect(backButton);
        
        addClickListenerWithSound(singleplayerButton, () -> controller.onSingleplayerClicked());
        addClickListenerWithSound(multiplayerButton, () -> controller.onMultiplayerClicked());
        addClickListenerWithSound(backButton, () -> controller.onBackClicked());
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
}
