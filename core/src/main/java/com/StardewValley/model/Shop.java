package com.StardewValley.model;

import com.StardewValley.controllers.shopMenuController;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.enums.ShopType;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import static com.StardewValley.View.GameScreen.TILE_SIZE;
import static com.StardewValley.View.GameScreen.isOutOfRealGame;

public class Shop {
    private ShopType type;
    private int startingHour;
    private int stoppingHour;
    private ArrayList<Item> permaStock;
    private ArrayList<Item> springStock;
    private ArrayList<Item> summerStock;
    private ArrayList<Item> fallStock;
    private ArrayList<Item> winterStock;
    private boolean isPlayerInside;

    // تایمر داخل بودن بازیکن داخل فروشگاه
    private float insideTimer = 0f;
    // وضعیت باز بودن منوی فروشگاه
    private boolean isMenuOpen = false;

    // UI Stage و Skin
    private Stage stage;
    private Skin skin;
    private Label messageLabel;
    public Shop(ShopType type, ArrayList<Item> permastock, ArrayList<Item> springStock,
                ArrayList<Item> summerStock, ArrayList<Item> fallStock,
                int startingHour, int stoppingHour, ArrayList<Item> winterStock) {
        try {
            this.type = type;
            this.permaStock = permastock != null ? permastock : new ArrayList<>();
            this.springStock = springStock != null ? springStock : new ArrayList<>();
            this.summerStock = summerStock != null ? summerStock : new ArrayList<>();
            this.fallStock = fallStock != null ? fallStock : new ArrayList<>();
            this.winterStock = winterStock != null ? winterStock : new ArrayList<>();
            this.startingHour = startingHour;
            this.stoppingHour = stoppingHour;

            initUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Shop() {
        try {
            this.permaStock = new ArrayList<>();
            this.springStock = new ArrayList<>();
            this.summerStock = new ArrayList<>();
            this.fallStock = new ArrayList<>();
            this.winterStock = new ArrayList<>();
            this.startingHour = 0;
            this.stoppingHour = 24;
            this.type = null;

            initUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        try {
            skin = GameAssetManager.getGameAssetManager().getSkin();
            stage = new Stage(new ScreenViewport());

            // Initialize message label
            messageLabel = new Label("", skin);
            messageLabel.setFontScale(1.2f);
            messageLabel.setColor(Color.YELLOW);
            messageLabel.setPosition(100, Gdx.graphics.getHeight() - 50);
            messageLabel.setVisible(false);
            stage.addActor(messageLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessage(String text, Color color) {
        messageLabel.setText(text);
        messageLabel.setColor(color);
        messageLabel.setVisible(true);

        // Center the message
        messageLabel.setX((Gdx.graphics.getWidth() - messageLabel.getWidth()) / 2);
        messageLabel.setY(Gdx.graphics.getHeight() - 100);

        // Set timer to hide message after 3 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                messageLabel.setVisible(false);
            }
        }, 3);
    }

    private void createShopMenu() {
        try {
            stage.clear();
            Image bg = new Image(type.getInTexture());
            bg.setFillParent(true);
            stage.addActor(bg);

            Dialog dialog = new Dialog("", skin) {
                @Override
                protected void result(Object obj) {
                    if (obj instanceof Boolean && (Boolean) obj) {
                        closeShopMenu();
                    }
                }
            };

            dialog.setModal(true);
            dialog.setMovable(true);
            dialog.pad(20);  // Reduced padding
            dialog.setSize(850, 700);  // Smaller dialog size
            dialog.setPosition(
                    (stage.getWidth() - dialog.getWidth()) / 2,
                    (stage.getHeight() - dialog.getHeight()) / 2
            );

            Table dialogTable = dialog.getContentTable();
            dialogTable.defaults().pad(10);  // Reduced padding

            // Header section - moved shop name to be more centered
            Table headerTable = new Table();
            Texture shopTexture = type != null && type.getOutTexture() != null ? type.getOutTexture() : new Texture("default_shop.png");
            Image shopImg = new Image(shopTexture);
            shopImg.setSize(60, 60);  // Smaller image
            Label title = new Label(type != null ? type.name() : "Shop", skin, "title");
            title.setFontScale(1.5f);

            headerTable.add(shopImg).size(60).padRight(20);
            headerTable.add(title).center().expandX();  // Centered title
            dialogTable.add(headerTable).left().colspan(5).fillX();
            dialogTable.row();

            // Filter section
            Table filterTable = new Table();
            Table itemsTable = new Table();
            final SelectBox<String> filterSelect = new SelectBox<>(skin);
            filterSelect.setItems("All Items", "Available Items");

            final Runnable[] refreshItems = new Runnable[1];

            refreshItems[0] = () -> {
                itemsTable.clearChildren();
                String selected = filterSelect.getSelected();
                Season currentSeason = App.currentGame.time.getSeason();

                ArrayList<Item> items = new ArrayList<>();

                if (selected.equals("All Items")) {
                    items.addAll(permaStock);
                    items.addAll(springStock);
                    items.addAll(summerStock);
                    items.addAll(fallStock);
                    items.addAll(winterStock);
                    items = new ArrayList<>(new LinkedHashSet<>(items));
                } else {
                    items.addAll(permaStock);
                    switch (currentSeason) {
                        case SPRING -> items.addAll(springStock);
                        case SUMMER -> items.addAll(summerStock);
                        case FALL -> items.addAll(fallStock);
                        case WINTER -> items.addAll(winterStock);
                    }
                    items.removeIf(item -> item.getAmount() <= 0);
                }

                // Condensed column headers
                itemsTable.add(new Label("Item", skin, "subtitle")).width(100);
                itemsTable.add(new Label("Price", skin, "subtitle")).width(80);
                itemsTable.add(new Label("Stock", skin, "subtitle")).width(80).padLeft(60);
                itemsTable.add(new Label("Qty", skin, "subtitle")).width(100).padLeft(70);
                itemsTable.add(new Label("", skin, "subtitle")).width(120);  // Empty header for button
                itemsTable.row();

                for (Item item : items) {
                    try {
                        boolean isAvailable = item.getAmount() > 0;
                        boolean isInSeason = permaStock.contains(item) ||
                                (currentSeason == Season.SPRING && springStock.contains(item)) ||
                                (currentSeason == Season.SUMMER && summerStock.contains(item)) ||
                                (currentSeason == Season.FALL && fallStock.contains(item)) ||
                                (currentSeason == Season.WINTER && winterStock.contains(item));

                        // Product info
                        Table productTable = new Table();
                        Image img = new Image(item.getImage());
                        img.setSize(40, 40);  // Smaller image
                        Label name = new Label(item.getName(), skin);
                        name.setFontScale(1.0f);  // Smaller font

                        if (!isAvailable || !isInSeason) {
                            img.setColor(Color.DARK_GRAY);
                            name.setColor(Color.DARK_GRAY);
                        }

                        productTable.add(img).size(40).padRight(10);
                        productTable.add(name).left();
                        itemsTable.add(productTable).left().pad(3);  // Reduced padding

                        // Price
                        Label priceLabel = new Label(String.valueOf(item.getValue() + "$"), skin);
                        priceLabel.setFontScale(1.0f);
                        if (!isAvailable || !isInSeason) {
                            priceLabel.setColor(Color.DARK_GRAY);
                        }
                        itemsTable.add(priceLabel).center().pad(3);

                        // Stock
                        Label stockLabel = new Label(String.valueOf(item.getAmount()), skin);
                        stockLabel.setFontScale(1.0f);
                        if (item.getAmount() == 1) {
                            stockLabel.setColor(Color.RED);
                        } else if (item.getAmount() <= 0) {
                            stockLabel.setColor(Color.DARK_GRAY);
                        }
                        itemsTable.add(stockLabel).center().pad(3);

                        // Quantity input
                        TextField quantityField = new TextField("1", skin);
                        quantityField.setAlignment(Align.center);
                        quantityField.setMessageText("Qty");
                        quantityField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
                        quantityField.setMaxLength(3);
                        quantityField.setWidth(80);  // Smaller width
                        if (!isAvailable || !isInSeason) {
                            quantityField.setDisabled(true);
                            quantityField.setColor(Color.DARK_GRAY);
                        }
                        itemsTable.add(quantityField).center().pad(3);

                        // Buy button
                        TextButton buyBtn = new TextButton("Buy", skin);
                        buyBtn.getLabel().setFontScale(1.0f);  // Smaller font

                        if (!isAvailable || !isInSeason || App.currentGame.currentPlayer.getMoney() < item.getValue()) {
                            buyBtn.setColor(Color.DARK_GRAY);
                            buyBtn.setDisabled(true);
                        }
                        buyBtn.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                try {
                                    int quantity = Integer.parseInt(quantityField.getText());
                                    if (quantity <= 0) {
                                        showMessage("Should Be Higher Than Zero", Color.RED);
                                        return;
                                    }

                                    shopMenuController controller = new shopMenuController();
                                    Result<String> result = controller.buy(Shop.this, item.getName(), quantity);

                                    if (result.success) {
                                        showMessage(result.getData(), Color.GREEN);
                                        stockLabel.setText(String.valueOf(item.getAmount()));
                                        Item temp = item.returnSimiliar();
                                        temp.setAmount(quantity);
                                        App.currentGame.currentPlayer.getInvetory().addItem(temp);
                                        if (item.getAmount() <= 0) {
                                            stockLabel.setColor(Color.DARK_GRAY);
                                            buyBtn.setDisabled(true);
                                            quantityField.setDisabled(true);
                                        }

                                    } else {
                                        showMessage(result.getData(), Color.RED);
                                    }
                                } catch (NumberFormatException e) {
                                    showMessage("لطفا عدد معتبر وارد کنید", Color.RED);
                                } catch (Exception e) {
                                    showMessage("خطا در خرید", Color.RED);
                                    Gdx.app.error("Shop", "Error: " + e.getMessage());
                                }
                            }
                        });

                        itemsTable.add(buyBtn).width(100).pad(3);  // Smaller button
                        itemsTable.row();

                        // Add separator
                        itemsTable.row();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            filterTable.add(new Label("Filter:", skin)).padRight(10);
            filterTable.add(filterSelect).width(180);  // Smaller width
            dialogTable.add(filterTable).left().colspan(5);
            dialogTable.row();

            // Items table
            itemsTable.top();
            itemsTable.defaults().pad(5);  // Reduced padding

            filterSelect.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    refreshItems[0].run();
                }
            });

            ScrollPane scrollPane = new ScrollPane(itemsTable, skin);
            scrollPane.setFadeScrollBars(false);
            scrollPane.setScrollingDisabled(true, false);
            scrollPane.setScrollbarsOnTop(true);
            dialogTable.add(scrollPane).colspan(5).width(800).height(500);  // Smaller dimensions
            dialogTable.row();

            // Exit button
            TextButton exitBtn = new TextButton("Exit", skin);
            exitBtn.getLabel().setFontScale(1.1f);  // Smaller font
            exitBtn.setWidth(150);  // Smaller width
            dialogTable.add(exitBtn).colspan(5).padTop(10);  // Reduced padding
            exitBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    closeShopMenu();
                }
            });
            dialog.show(stage);
            refreshItems[0].run();

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



    public void update(Vector2 playerPos, float delta) {
        try {
            if (isMenuOpen) {
                stage.act(delta);
                Gdx.input.setInputProcessor(stage);
                isOutOfRealGame = true;
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    closeShopMenu();
                }
                return;
            }

            if(type == null || type.getPosition() == null) return;

            float x = type.getPosition().x * TILE_SIZE;
            float y = type.getPosition().y * TILE_SIZE;
            float width = type.getWidth() * TILE_SIZE;
            float height = type.getHeight() * TILE_SIZE;

            boolean insideNow = playerPos.y > x && playerPos.y < (x + width)
                    && playerPos.x > y && playerPos.x < (y + height);

            if (insideNow) {
                if (!isPlayerInside) {
                    insideTimer = 0f;
                }
                insideTimer += delta;

                if (insideTimer >= 2f && !isMenuOpen) {
                    insideTimer = 0f;
                    openShopMenu();
                }
            } else {
                insideTimer = 0f;
                if (isMenuOpen) {
                    closeShopMenu();
                }
            }

            isPlayerInside = insideNow;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(SpriteBatch batch) {
        try {
            if(type == null) return;
            Texture tex = isPlayerInside ? type.getInTexture() : type.getOutTexture();
            batch.draw(tex, type.getPosition().y * TILE_SIZE, type.getPosition().x * TILE_SIZE,
                    type.getWidth() * TILE_SIZE, type.getHeight() * TILE_SIZE);
            if (isMenuOpen) {
                stage.draw();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openShopMenu() {
        try {
            isMenuOpen = true;
            isOutOfRealGame = true;
            createShopMenu();
            Gdx.input.setInputProcessor(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeShopMenu() {
        try {
            isMenuOpen = false;
            isOutOfRealGame = false;
            Gdx.input.setInputProcessor(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFallStock(Item fallStock) {
        try {
            if (fallStock != null) this.fallStock.add(fallStock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addWinterStock(Item winterStock) {
        try {
            if (winterStock != null) this.winterStock.add(winterStock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSummerStock(Item summerStock) {
        try {
            if (summerStock != null) this.summerStock.add(summerStock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getPermaStock() {
        try {
            return permaStock;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Item> getFallStock() {
        try {
            return fallStock;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Item> getWinterStock() {
        try {
            return winterStock;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Item> getSpringStock() {
        try {
            return springStock;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Item> getSummerStock() {
        try {
            return summerStock;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Item> getStock() {
        try {
            ArrayList<Item> combined = new ArrayList<>(permaStock);
            Season season = App.currentGame.time.getSeason();
            switch (season) {
                case SPRING -> combined.addAll(springStock);
                case SUMMER -> combined.addAll(summerStock);
                case FALL -> combined.addAll(fallStock);
                case WINTER -> combined.addAll(winterStock);
            }
            return combined;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ShopType getType() {
        try {
            return type;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setType(ShopType type) {
        try {
            this.type = type;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStartingHour() {
        try {
            return startingHour;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setStartingHour(int startingHour) {
        try {
            this.startingHour = startingHour;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStoppingHour() {
        try {
            return stoppingHour;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setStoppingHour(int stoppingHour) {
        try {
            this.stoppingHour = stoppingHour;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
