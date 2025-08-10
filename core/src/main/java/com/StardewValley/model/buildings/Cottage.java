package com.StardewValley.model.buildings;

import com.StardewValley.View.Helpers.DialogUtils;
import com.StardewValley.View.Helpers.InventoryDialog;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.StardewValley.View.GameScreen.*;
import static com.StardewValley.View.GameScreen.dialogStage;

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
        try {
            // 1. مطمئن شوید label مقداردهی شده است
            if (messageLabel == null) {
                messageLabel = new Label("", skin);
                messageLabel.setFontScale(1.2f);
                stage.addActor(messageLabel);
            }

            // 2. تنظیم متن و استایل
            messageLabel.setText(text);
            messageLabel.setColor(color);
            messageLabel.setFontScale(1.2f); // اندازه فونت را مجدداً تنظیم کنید

            // 3. محاسبه موقعیت پس از تنظیم متن
            messageLabel.pack(); // این خط حیاتی است!
            messageLabel.setPosition(
                    (Gdx.graphics.getWidth() - messageLabel.getWidth()) / 2,
                    Gdx.graphics.getHeight() - 100
            );

            // 4. نمایش پیام
            messageLabel.setVisible(true);

            // 5. مخفی کردن خودکار پس از 3 ثانیه
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (messageLabel != null) {
                        messageLabel.setVisible(false);
                    }
                }
            }, 3);
        } catch (Exception e) {
            Gdx.app.error("Cottage", "Error showing message: " + e.getMessage());
        }
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

            Image bg = new Image(GameAssetManager.COTTAGEIn);
            bg.setFillParent(true);
            stage.addActor(bg);

            // دیالوگ اصلی (غیر Modal)
            Dialog dialog = new Dialog("", skin) {
                @Override
                protected void result(Object obj) {
                    closeCottageMenu();
                }
            };

            dialog.setModal(false); // غیر Modal باشد تا کلیک‌ها به اجزای دیگر برسد
            dialog.setMovable(false);
            dialog.setFillParent(true);
            dialog.setBackground((Drawable) null);

            Table contentTable = dialog.getContentTable();
            contentTable.defaults().pad(20);

            Table imagesTable = new Table();
            imagesTable.center();

            // تصویر یخچال
            Image fridgeImage = new Image(new Texture("Special_item/Mini-Fridge.png"));
            fridgeImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    DialogUtils.openDialog(
                            GameAssetManager.getGameAssetManager().getSkin()        ,
                            stage,
                            "Fridge",
                            "Pick some Thing To Put In Fridge Or Take",
                            "Special_item/Mini-Fridge.png",
                            700, 400,
                            (Gdx.graphics.getWidth() - 700) / 2f,
                            Gdx.graphics.getHeight() * 0.7f,
                            new DialogUtils.DialogButton("food", "food", () -> {
                                DialogUtils.closeDialog();
                                InventoryDialog.show(()->{});
                            }),
                            new DialogUtils.DialogButton("Cancel", "cancel", DialogUtils::closeDialog)
                    );
                }
            });
            // تصویر اجاق گاز
            Image stoveImage = new Image(GameAssetManager.BARN_OUT_TEXTURE);
            stoveImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showMessage("grege", Color.GREEN);

                }
            });

            // تصویر کوره صنعتگری
            Image furnaceImage = new Image(GameAssetManager.DINOSAUR_TEXTURE);
            furnaceImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showMessage("tege", Color.GREEN);
                    // اینجا می‌توانید صفحه صنعتگری را باز کنید
                }
            });

            // اضافه کردن تصاویر به جدول
            imagesTable.add(fridgeImage).size(100, 100).pad(15);
            imagesTable.add(stoveImage).size(100, 100).pad(15);
            imagesTable.add(furnaceImage).size(100, 100).pad(15);

            // دکمه خروج
            TextButton exitButton = new TextButton("EXIT", skin);
            exitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    closeCottageMenu();
                }
            });

            // چیدمان نهایی
            contentTable.add(imagesTable).row();
            contentTable.add(exitButton).width(150).height(50).padTop(30);

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