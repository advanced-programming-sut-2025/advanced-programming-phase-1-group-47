package com.StardewValley.View;

import com.StardewValley.View.Helpers.EnergyHelper;
import com.StardewValley.View.Helpers.WeatherRenderer;
import com.StardewValley.controllers.GameMenuController;
import com.StardewValley.model.*;
import com.StardewValley.model.Game;
import com.StardewValley.model.Map;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import static com.StardewValley.model.App.*;

public class GameScreen implements Screen {

    private static OrthographicCamera camera = App.camera;
    private Viewport viewport = App.viewport;
    private SpriteBatch batch = App.batch;
    private int scrollDelta = 0;

    private static final int TILE_SIZE = 30;
    private Tile[][] tileMap;
    private Vector2 playerPosition;
    private float speed = 350f;
    private float stateTime;
    private TextureAtlas playerAtlas;
    private Animation<TextureRegion>[] playerAnimations;
    private int moveDirection = 2;
    private Rectangle currentDialogIconBounds = null;

    private HashMap<TileType, Texture> tileTextures;
    // UI
    private Stage mainStage, dialogStage;
    Dialog dialog;
    private Skin skin;
    private Dialog mapDialog;
    private boolean isMapDialogOpen = false;
    private EnergyHelper energyHelper;
    // Toolbar
    private boolean isToolAnimating = false;
    private float toolAnimTime = 0f;
    private final float toolAnimDuration = 0.3f;
    private static final int TOOLBAR_SIZE = 24;
    private Stack[] toolbarSlots = new Stack[TOOLBAR_SIZE];
    private int selectedToolIndex = 0;
    private ArrayList<Texture> toolTextures = new ArrayList<>();
    private ArrayList<Item> toolbarItems = new ArrayList<>();
    private Texture emptySlotTexture;
    GameMenuController controller = new GameMenuController();

    private Texture energyBarTexture; // Texture for energy bar
    private Pixmap energyBarPixmap; // Pixmap for creating energy bar texture
    private Texture backgroundTexture;
    private Texture fillTexture;

    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 400, camera);
        batch = new SpriteBatch();
        skin = GameAssetManager.getGameAssetManager().getSkin();
        playerPosition = new Vector2(100, 100);
        setupGame();
        App.getCurrentGame().loadShops();
        App.getCurrentGame().setNpc();
        loadTextures();
        stateTime = 0f;
        energyHelper = new EnergyHelper();
        energyHelper.initEnergyBar(); // Initialize energy bar
    }

    private void loadTextures() {
        tileTextures = new HashMap<>();
        playerAtlas = new TextureAtlas(Gdx.files.internal("game/character/sprites_Alex.atlas"));
        playerAnimations = new Animation[4];

        for (int dir = 0; dir < 4; dir++) {
            Array<TextureRegion> walkFrames = new Array<>();
            for (int frame = 0; frame < 4; frame++) {
                walkFrames.add(playerAtlas.findRegion("player_" + (13 - dir) + "_" + frame));
            }
            playerAnimations[dir] = new Animation<>(0.15f, walkFrames, Animation.PlayMode.LOOP);
        }

        tileTextures.put(TileType.GRASS, GameAssetManager.GRASS);
        tileTextures.put(TileType.EMPTY, GameAssetManager.EMPTY);
        tileTextures.put(TileType.STONE, GameAssetManager.STONE);
        tileTextures.put(TileType.PLANT, GameAssetManager.PLANT);
        tileTextures.put(TileType.LAKE, GameAssetManager.WATER);
        tileTextures.put(TileType.WALL, GameAssetManager.WALL);
        tileTextures.put(TileType.TREE, GameAssetManager.TREE);
        tileTextures.put(TileType.ROBIN, GameAssetManager.ROBIN);
        tileTextures.put(TileType.ABIGEL, GameAssetManager.ABIGEL);
        tileTextures.put(TileType.LEAH, GameAssetManager.LEAH);
        tileTextures.put(TileType.TILLED, GameAssetManager.TILLED);
        tileTextures.put(TileType.SEBASTIAN, GameAssetManager.SEBASTIAN);
        tileTextures.put(TileType.HARVEY, GameAssetManager.HARVEY);
        emptySlotTexture = new Texture("bar.png");

        // Load energy bar textures with fallback
        try {
            backgroundTexture = GameAssetManager.ENERGY_BAR_EMPTY != null ? GameAssetManager.ENERGY_BAR_EMPTY : new Texture(Gdx.files.internal("energy_bar_empty.png"));
            fillTexture = GameAssetManager.GREEN_SQUARE != null ? GameAssetManager.GREEN_SQUARE : new Texture(Gdx.files.internal("green_square.png"));
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to load energy bar textures: " + e.getMessage());
            // Fallback to solid color textures
            backgroundTexture = createSolidColorTexture(32, 128, Color.GRAY);
            fillTexture = createSolidColorTexture(32, 128, Color.GREEN);
        }
        GameAssetManager.load();
        // Update foraging tiles
        for (int y = 0; y <= 160; y++) {
            for (int x = 0; x <= 120; x++) {
                if (x >= 0 && y >= 0 && x < tileMap[0].length && y < tileMap.length) {
                    Tile tile = tileMap[y][x];
                    if (tile.type == TileType.FORAGING) {
                        try {
                            Random rand = new Random();
                            tile.id = rand.nextInt(21) + 357;
                        } catch (Exception e) {
                            Gdx.app.error("error", e.getMessage());
                        }
                    }
                }
            }
        }
        updateToolbarItems();
    }

    private Texture createSolidColorTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private void updateToolbarItems() {
        toolTextures.clear();
        toolbarItems.clear();

        for (Item x : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if (x.getImage() != null) {
                toolTextures.add(x.getImage());
                toolbarItems.add(x);
            }
        }

        while (toolTextures.size() < TOOLBAR_SIZE) {
            toolTextures.add(null);
            toolbarItems.add(null);
        }
    }

    private void setupGame() {
        try {
            Game newGame = new Game();
            Map newMap = new Map(new String[]{"1", "2", "3", "4"});
            newGame.map = newMap;
            App.setCurrentGame(newGame);
            tileMap = App.currentGame.map.tiles;
        } catch (Exception e) {
            e.printStackTrace();
            tileMap = new Tile[160][120];
            for (int y = 0; y < 160; y++) {
                for (int x = 0; x < 120; x++) {
                    tileMap[y][x] = new Tile(new com.StardewValley.model.Point(x, y), TileType.EMPTY);
                }
            }
        }
    }

    private void EnergyCounter(int energy) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        try {
            currentPlayer.setEnergy(new Energy(currentPlayer.getEnergy().getEnergyCap(),currentPlayer.getEnergy().getCurrentEnergy() - energy));
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to decrease energy: " + e.getMessage());
        }
    }


    private void handleInput(float delta) {
        boolean moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerPosition.x -= speed * delta;
            moveDirection = 3;
            moving = true;
            EnergyCounter(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerPosition.x += speed * delta;
            moveDirection = 1;
            moving = true;
            EnergyCounter(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerPosition.y += speed * delta;
            moveDirection = 2;
            moving = true;
            EnergyCounter(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerPosition.y -= speed * delta;
            moveDirection = 0;
            moving = true;
            EnergyCounter(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            try {
                controller.setUpNextDay();
            } catch (Exception e) {
                Gdx.app.log("GameScreen", "Failed to set up next day\n + " + e.getMessage());
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                App.currentGame.time.setHour(App.currentGame.time.getHour() + 100000);
            } else {
                App.currentGame.time.setHour(App.currentGame.time.getHour() + 1);
            }
        }



        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            if (toolTextures.get(i) == null || toolTextures.get(i).equals(emptySlotTexture)) {
                continue;
            }
            if (i < 9 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_1 + i)) {
                selectedToolIndex = i;
                updateToolbarHighlight();
            } else if (i == 9 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
                selectedToolIndex = i;
                updateToolbarHighlight();
            } else if (i == 10 && Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
                selectedToolIndex = i;
                updateToolbarHighlight();
            } else if (i == 11 && Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
                selectedToolIndex = i;
                updateToolbarHighlight();
            }
        }

        if (scrollDelta != 0) {
            int direction = scrollDelta > 0 ? 1 : -1;
            int originalIndex = selectedToolIndex;

            do {
                selectedToolIndex = (selectedToolIndex + direction + TOOLBAR_SIZE) % TOOLBAR_SIZE;
            } while ((toolTextures.get(selectedToolIndex) == null ||
                    toolTextures.get(selectedToolIndex).equals(emptySlotTexture)) &&
                    selectedToolIndex != originalIndex);

            updateToolbarHighlight();
            scrollDelta = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            int originalIndex = selectedToolIndex;
            do {
                selectedToolIndex = (selectedToolIndex + 1) % TOOLBAR_SIZE;
            } while ((toolTextures.get(selectedToolIndex) == null ||
                    toolTextures.get(selectedToolIndex).equals(emptySlotTexture)) &&
                    selectedToolIndex != originalIndex);
            updateToolbarHighlight();
        }

        if (!moving) stateTime = 0;
    }

    private void updateToolbarHighlight() {
        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Stack stack = toolbarSlots[i];
            if (stack.getChildren().size > 0 && stack.getChild(0) instanceof Container) {
                Container<?> cont = (Container<?>) stack.getChild(0);
                cont.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
            }
        }
    }

    private void updateToolbar() {
        updateToolbarItems();
        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Stack stack = toolbarSlots[i];
            Item item = (i < toolbarItems.size()) ? toolbarItems.get(i) : null;
            int count = (item != null) ? (int)(Math.log(item.getAmount()) / Math.log(2)) + 1 : 0;

            Texture tex = (item != null && item.getImage() != null) ? item.getImage() : emptySlotTexture;
            Image icon = new Image(new TextureRegion(tex));
            icon.setSize(36, 36);

            Container<Image> container = new Container<>(icon);
            container.background(new TextureRegionDrawable(new TextureRegion(new Texture("white.png"))));
            container.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
            container.size(44, 44);

            Label countLabel = new Label(count > 0 ? String.valueOf(count) : "", skin);
            countLabel.setAlignment(Align.bottomRight);
            countLabel.setFontScale(0.7f);
            countLabel.setColor(Color.WHITE);

            Table labelTable = new Table();
            labelTable.setFillParent(true);
            labelTable.bottom().right();
            labelTable.add(countLabel).padRight(5).padBottom(5);

            stack.clear();
            stack.add(container);
            stack.add(labelTable);
        }
    }

    private Texture getTextureForTileType(TileType type) {
        Texture tex = tileTextures.get(type);
        return (tex != null) ? tex : tileTextures.get(TileType.EMPTY);
    }

    private void updateState(float delta) {
        stateTime += delta;
        energyBarTexture = energyHelper.updateEnergyBar();
        camera.position.set(playerPosition.x, playerPosition.y, 0);
        camera.update();
    }
    private void clearScreen() {
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    private boolean dialogOpen = false;
    private Dialog currentDialog;

    private void handleTouchInteraction() {
        if (!Gdx.input.justTouched()) return;

        Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touch);

        handleToolUse((int)(touch.x / TILE_SIZE), (int)(touch.y / TILE_SIZE));

        if (!dialogOpen && currentDialogIconBounds != null &&
                currentDialogIconBounds.contains(touch.x, touch.y)) {
            openDialog("Robin", "HI Parsa :)", "Marnie_Happy.png");
        }

        toolAnimTime = 0;
        isToolAnimating = true;
    }

    private void handleToolUse(int tileX, int tileY) {
        try {
            if (toolbarItems.get(selectedToolIndex) != null) {
                toolbarItems.get(selectedToolIndex).useTool(tileMap[tileY][tileX]);
                updateToolbar();
            }

            Timer.schedule(new Timer.Task() {
                @Override public void run() {
                    updateTools();
                }
            }, 0.2f);
        } catch (Exception e) {
            Gdx.app.error("error", e.getMessage());
        }
    }

    private void openDialog(String title, String message, String imagePath) {
        dialogOpen = true;

        currentDialog = new Dialog(title, skin) {
            @Override
            protected void result(Object obj) {
                if (obj.equals(true)) {
                    this.hide();
                    dialogOpen = false;
                }
            }
        };

        BitmapFont bigFont = new BitmapFont();
        bigFont.getData().setScale(2f);
        Label.LabelStyle bigLabelStyle = new Label.LabelStyle(bigFont, Color.WHITE);

        currentDialog.getContentTable().clear();
        currentDialog.getContentTable().add(new Label(message, bigLabelStyle)).pad(20).row();
        currentDialog.getContentTable().add(new Image(new Texture(Gdx.files.internal(imagePath)))).size(100, 100).pad(10);
        currentDialog.button("LOVE BUTTON -->", true).pad(20);

        currentDialog.setSize(700, 400);
        currentDialog.setPosition((Gdx.graphics.getWidth() - 700) / 2f, Gdx.graphics.getHeight() * 0.7f);
        currentDialog.show(dialogStage);
    }
    private void drawGround() {
        Texture grassTexture = tileTextures.get(TileType.EMPTY);
        for (int y = startY(); y <= endY(); y++) {
            for (int x = startX(); x <= endX(); x++) {
                if (isInBounds(x, y)) {
                    batch.draw(grassTexture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
    private void drawTiles() {
        for (int y = startY(); y <= endY(); y++) {
            for (int x = startX(); x <= endX(); x++) {
                if (isInBounds(x, y)) {
                    Tile tile = tileMap[y][x];
                    if (tile.type != TileType.EMPTY && tile.type != TileType.COTTAGE) {
                        batch.draw(getTextureForTileType(tile.type), x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
    }
    private void drawCottages() {
        Set<String> drawn = new HashSet<>();
        for (int y = startY(); y <= endY(); y++) {
            for (int x = startX(); x <= endX(); x++) {
                if (isInBounds(x, y)) {
                    Tile tile = tileMap[y][x];
                    if (tile.type == TileType.COTTAGE) {
                        int groupX = (x / 7) * 7;
                        int groupY = (y / 7) * 7;
                        String key = groupX + "," + groupY;

                        if (!drawn.contains(key)) {
                            drawn.add(key);

                            boolean inside = playerPosition.x >= groupX * TILE_SIZE &&
                                    playerPosition.x <= (groupX + 7) * TILE_SIZE &&
                                    playerPosition.y >= groupY * TILE_SIZE &&
                                    playerPosition.y <= (groupY + 7) * TILE_SIZE;

                            Texture cottageTex = inside ? GameAssetManager.COTTAGEIn : GameAssetManager.COTTAGEOut;
                            batch.draw(cottageTex, groupX * TILE_SIZE, groupY * TILE_SIZE, TILE_SIZE * 7, TILE_SIZE * 7);
                        }
                    }
                }
            }
        }
    }
    private void drawNPCsAndForaging() {
        for (int y = startY(); y <= endY(); y++) {
            for (int x = startX(); x <= endX(); x++) {
                if (!isInBounds(x, y)) continue;
                Tile tile = tileMap[y][x];

                if (isNpcTile(tile.type)) {
                    batch.draw(GameAssetManager.NPCHOUSE, (x - 2) * TILE_SIZE, (y + 1) * TILE_SIZE, TILE_SIZE * 5, TILE_SIZE * 5);

                    Vector2 npcPos = new Vector2(x * TILE_SIZE, y * TILE_SIZE);
                    if (playerPosition.dst(npcPos) <= TILE_SIZE * 3) {
                        float iconX = x * TILE_SIZE;
                        float iconY = (y + 2) * TILE_SIZE;
                        batch.draw(GameAssetManager.DIALOG, iconX, iconY, TILE_SIZE, TILE_SIZE);
                        currentDialogIconBounds = new Rectangle(iconX, iconY, TILE_SIZE, TILE_SIZE);
                    }
                }

                if (tile.type == TileType.FORAGING) {
                    try {
                        String path = "Foraging/" + AllTheItemsInTheGame.getPlantById(tile.id).getName() + ".png";
                        batch.draw(new Texture(path), x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    } catch (NullPointerException e) {
                        Gdx.app.log("error", e.getMessage());
                    }
                }
            }
        }
    }
    private void drawUI() {
        if (energyBarTexture != null && App.currentGame.currentPlayer != null) {
            batch.draw(energyBarTexture,
                    camera.position.x - viewport.getWorldWidth() / 2 + 20,
                    camera.position.y - viewport.getWorldHeight() / 2 + 20,
                    energyBarTexture.getWidth(),
                    energyBarTexture.getHeight());
        }
    }
    private void drawPlayer() {
        TextureRegion currentFrame = playerAnimations[moveDirection].getKeyFrame(stateTime, true);
        batch.draw(currentFrame, playerPosition.x, playerPosition.y, TILE_SIZE, TILE_SIZE * 2);
    }
    private void drawTool() {
        Texture toolTex = toolTextures.get(selectedToolIndex);
        if (toolTex == null) return;

        boolean flipX = false;
        float toolX = playerPosition.x;
        float toolY = playerPosition.y;

        float offset = isToolAnimating ? 5f * (float)Math.sin(Math.min(toolAnimTime += Gdx.graphics.getDeltaTime(), toolAnimDuration) / toolAnimDuration * Math.PI) : 0;

        switch (moveDirection) {
            case 0: toolY -= TILE_SIZE * 0.4f - offset; toolX += TILE_SIZE * 0.2f; break;
            case 1: toolX += TILE_SIZE * 0.6f + offset; toolY += TILE_SIZE * 0.3f; break;
            case 2: toolY += TILE_SIZE * 1.1f + offset; toolX += TILE_SIZE * 0.2f; break;
            case 3: flipX = true; toolX -= TILE_SIZE * 0.6f - offset; toolY += TILE_SIZE * 0.3f; break;
        }

        batch.draw(toolTex, toolX, toolY, TILE_SIZE, TILE_SIZE,
                0, 0, toolTex.getWidth(), toolTex.getHeight(), flipX, false);
    }

    private void drawWeather() {
        WeatherRenderer.handleWeather(batch, camera, viewport);
    }
    private void handlePlayerEnergy() {
        Energy energy = App.currentGame.currentPlayer.getEnergy();
        if (energy.getCurrentEnergy() <= 0) {
            energy.setCurrentEnergy(energy.getEnergyCap() / 2);
            playerPosition.set(202, 198);
        }
    }
    private void renderShops(float delta) {
        for (Shop shop : App.getCurrentGame().getShops()) {
            shop.update(playerPosition);
            shop.render(batch);
        }
    }
    private int startX() {
        return (int)(camera.position.x - viewport.getWorldWidth() / 2) / TILE_SIZE - 1;
    }

    private int endX() {
        return (int)(camera.position.x + viewport.getWorldWidth() / 2) / TILE_SIZE + 1;
    }

    private int startY() {
        return (int)(camera.position.y - viewport.getWorldHeight() / 2) / TILE_SIZE - 1;
    }

    private int endY() {
        return (int)(camera.position.y + viewport.getWorldHeight() / 2) / TILE_SIZE + 1;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < tileMap[0].length && y < tileMap.length;
    }

    @Override
    public void render(float delta) {
        try {
            TextureRegion clockTexture = TimeAndDate.renderClockToTexture();
            handleInput(delta);
            updateState(delta);
            clearScreen();
            handleTouchInteraction();

            // شروع نقاشی
            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            drawGround();
            drawTiles();
            drawCottages();
            drawNPCsAndForaging();
            drawUI();
            drawPlayer();
            drawTool();
            batch.draw(clockTexture, playerPosition.x + 220, playerPosition.y + 58, TILE_SIZE * 30, 15 * TILE_SIZE);
            batch.end();
            // آب‌وهوا و فروشگاه‌ها
            drawWeather();
            renderShops(delta);

            // انرژی و وضعیت بازیکن
            handlePlayerEnergy();

            // استیج‌ها
            mainStage.act(delta);
            mainStage.draw();

            dialogStage.act(delta);
            dialogStage.draw();
        }
        catch (Exception e) {
            Gdx.app.log("error", e.getMessage());
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        if (mainStage != null) mainStage.getViewport().update(width, height, true);
        if (dialogStage != null) dialogStage.getViewport().update(width, height, true);
        // Reinitialize energy bar Pixmap on resize
        if (energyBarPixmap != null) {
            energyBarPixmap.dispose();
        }
        energyHelper.initEnergyBar();

    }

    @Override
    public void show() {
        mainStage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                scrollDelta += amountY;
                return true;
            }
        });
        dialogStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(dialogStage);
        updateTools();

    }
    public void updateTools(){
        Table toolbar = new Table();
        toolbar.bottom().center().padTop(800);
        toolbar.setFillParent(true);

        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Item item = (i < toolbarItems.size()) ? toolbarItems.get(i) : null;
            int count = (item != null) ? (int)(Math.log(item.getAmount()) / Math.log(2)) + 1 : 0;

            Texture tex = (item != null && item.getImage() != null) ? item.getImage() : emptySlotTexture;
            Image icon = new Image(new TextureRegion(tex));
            icon.setSize(36, 36);

            Container<Image> container = new Container<>(icon);
            container.background(new TextureRegionDrawable(new TextureRegion(new Texture("white.png"))));
            container.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
            container.size(44, 44);

            Label countLabel = new Label(count > 0 ? String.valueOf(count) : "", skin);
            countLabel.setAlignment(Align.bottomRight);
            countLabel.setFontScale(0.7f);
            countLabel.setColor(Color.WHITE);

            Table labelTable = new Table();
            labelTable.setFillParent(true);
            labelTable.bottom().right();
            labelTable.add(countLabel).padRight(5).padBottom(5);

            Stack stack = new Stack();
            stack.add(container);
            stack.add(labelTable);
            toolbarSlots[i] = stack;

            toolbar.add(stack).pad(5);
        }

        mainStage.addActor(toolbar);
    }

    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        for (Entry<TileType, Texture> entry : tileTextures.entrySet()) entry.getValue().dispose();
        for (Texture tex : toolTextures) if (tex != null) tex.dispose();
        playerAtlas.dispose();
        emptySlotTexture.dispose();
        if (mainStage != null) mainStage.dispose();
        if (dialogStage != null) dialogStage.dispose();
        skin.dispose();
        if (energyBarTexture != null) energyBarTexture.dispose();
        if (energyBarPixmap != null) energyBarPixmap.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (fillTexture != null) fillTexture.dispose();
    }
}