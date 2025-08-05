package com.StardewValley.model.buildings;

import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.Ground;
import com.StardewValley.model.Point;
import com.StardewValley.model.Refrigerator;
import com.StardewValley.model.enums.TileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.StardewValley.View.GameScreen.TILE_SIZE;
import static com.StardewValley.View.GameScreen.isOutOfRealGame;

public class Cottage extends Building {
    public Refrigerator fridge;
    public Point doorPosition;
    public boolean isPlayerInside;
    private float insideTimer;
    private boolean isMenuOpen;

    private Stage stage;
    private Skin skin;
    private Label messageLabel;

    public Cottage(Ground ground, Point doorPosition) {
        super(ground);
        this.doorPosition = doorPosition;
        this.getGround().set_tile_default(TileType.COTTAGE);
        this.fridge = new Refrigerator();
        this.isPlayerInside = false;
        this.insideTimer = 0f;
        this.isMenuOpen = false;

        initUI();
    }

    private void initUI() {
        skin = GameAssetManager.getGameAssetManager().getSkin();
        stage = new Stage(new ScreenViewport());

        // Initialize message label
        messageLabel = new Label("", skin);
        messageLabel.setFontScale(1.2f);
        messageLabel.setColor(Color.YELLOW);
        messageLabel.setPosition(100, Gdx.graphics.getHeight() - 50);
        messageLabel.setVisible(false);
        stage.addActor(messageLabel);
    }

    private void showMessage(String text, Color color) {
        messageLabel.setText(text);
        messageLabel.setColor(color);
        messageLabel.setVisible(true);
        messageLabel.setX((Gdx.graphics.getWidth() - messageLabel.getWidth()) / 2);
        messageLabel.setY(Gdx.graphics.getHeight() - 100);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                messageLabel.setVisible(false);
            }
        }, 3);
    }

    public void update(Vector2 playerPos, float delta) {
        if (isMenuOpen) {
            stage.act(delta);
            Gdx.input.setInputProcessor(stage);
            isOutOfRealGame = true;
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                closeCottageMenu();
            }
            return;
        }

        boolean insideNow = isPlayerNearDoor(playerPos);

        if (insideNow) {
            if (!isPlayerInside) {
                insideTimer = 0f;
            }
            insideTimer += delta;

            if (insideTimer >= 2f && !isMenuOpen) {
                insideTimer = 0f;
                openCottageMenu();
            }
        } else {
            insideTimer = 0f;
            if (isMenuOpen) {
                closeCottageMenu();
            }
        }

        isPlayerInside = insideNow;
    }

    public boolean isPlayerNearDoor(Vector2 playerPos) {
        float playerX = playerPos.y / TILE_SIZE;
        float playerY = playerPos.x / TILE_SIZE;
        Ground cottageGround = getGround();
        return (playerX >= cottageGround.startPoint.x && playerX <= cottageGround.endPoint.x && playerY >= cottageGround.startPoint.y && playerY <= cottageGround.endPoint.y);
    }

    private void openCottageMenu() {
        try {
            isMenuOpen = true;
            isOutOfRealGame = true;
            createCottageMenu();
            Gdx.input.setInputProcessor(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeCottageMenu() {
        try {
            isMenuOpen = false;
            isOutOfRealGame = false;
            Gdx.input.setInputProcessor(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCottageMenu() {
        try {
            stage.clear();

            Dialog dialog = new Dialog("Cottage Menu", skin) {
                @Override
                protected void result(Object obj) {
                    if (obj instanceof Boolean && (Boolean) obj) {
                        closeCottageMenu();
                    }
                }
            };

            dialog.setModal(true);
            dialog.setMovable(true);
            dialog.pad(20);
            dialog.setSize(600, 400);
            dialog.setPosition(
                    (stage.getWidth() - dialog.getWidth()) / 2,
                    (stage.getHeight() - dialog.getHeight()) / 2
            );

            Table contentTable = dialog.getContentTable();
            contentTable.defaults().pad(10);

            // Header
            Label title = new Label("Cottage Options", skin, "title");
            title.setFontScale(1.5f);
            contentTable.add(title).colspan(2).padBottom(20).row();

            // Buttons
            TextButton cookingButton = new TextButton("Cooking", skin);
            TextButton craftingButton = new TextButton("Crafting", skin);
            TextButton fridgeButton = new TextButton("Fridge", skin);
            TextButton exitButton = new TextButton("Exit", skin);

            cookingButton.getLabel().setFontScale(1.2f);
            craftingButton.getLabel().setFontScale(1.2f);
            fridgeButton.getLabel().setFontScale(1.2f);
            exitButton.getLabel().setFontScale(1.1f);

            // Button listeners
            cookingButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    showMessage("Cooking menu opened", Color.GREEN);
                    // Implement cooking functionality here
                }
            });

            craftingButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showMessage("Crafting menu opened", Color.GREEN);
                    // Implement crafting functionality here
                }
            });

            fridgeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showMessage("Fridge accessed", Color.GREEN);
                    // Implement fridge functionality here
                }
            });

            exitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    closeCottageMenu();
                }
            });

            // Layout
            contentTable.add(cookingButton).width(200).height(60).pad(10).row();
            contentTable.add(craftingButton).width(200).height(60).pad(10).row();
            contentTable.add(fridgeButton).width(200).height(60).pad(10).row();
            contentTable.add(exitButton).width(150).height(50).pad(10);

            dialog.show(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isMenuOpen() {
        return isMenuOpen;
    }

    public Stage getStage() {
        return stage;
    }

    public void render(SpriteBatch batch) {
        if (isMenuOpen) {
            stage.draw();
        }
    }
}