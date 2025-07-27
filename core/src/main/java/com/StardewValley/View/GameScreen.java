package com.StardewValley.View;

import com.StardewValley.controllers.GameMenuController;
import com.StardewValley.model.*;
import com.StardewValley.model.Game;
import com.StardewValley.model.enums.Season;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.StardewValley.model.things.tools.Scythe;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;

public class GameScreen implements Screen {

    private static OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private int scrollDelta = 0;

    private static final int TILE_SIZE = 30;
    private Tile[][] tileMap;
    private Vector2 playerPosition;
    private float speed = 350f;
    private float stateTime;
    private TextureAtlas playerAtlas;
    private Animation<TextureRegion>[] playerAnimations;
    private int moveDirection = 2;

    private HashMap<TileType, Texture> tileTextures;
    // UI
    private Stage mainStage, dialogStage;
    Texture CottageFinal;
    private Skin skin;
    private Dialog mapDialog;
    Texture mapTexture;
    private boolean isMapDialogOpen = false;

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
        initEnergyBar(); // Initialize energy bar
    }

    private boolean isNpcTile(TileType type) {
        return type == TileType.ROBIN ||
                type == TileType.ABIGEL ||
                type == TileType.LEAH ||
                type == TileType.SEBASTIAN ||
                type == TileType.HARVEY;
    }

    private void loadTextures() {
        tileTextures = new HashMap<>();
        playerAtlas = new TextureAtlas(Gdx.files.internal("game/character/sprites_player.atlas"));
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
        tileTextures.put(TileType.COTTAGE, GameAssetManager.COTTAGEOut);
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

    private void showEnergy() {
        Gdx.app.log("Energy", String.valueOf(App.getCurrentGame().getCurrentPlayer().getEnergy().getCurrentEnergy()));
    }

    private void handleInput(float delta) {
        if (isMapDialogOpen) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.N)) closeMapDialog();
            return;
        }

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) showMapDialog();
        GameMenuController controller = new GameMenuController();
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            try {
                controller.setUpNextDay();
            } catch (Exception e) {
                Gdx.app.log("GameScreen", "Failed to set up next day\n + " + e.getMessage());
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) App.currentGame.time.setHour(App.currentGame.time.getHour() + 1);

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

    private void initEnergyBar() {
        // Initialize Pixmap for energy bar
        int width = (int)(camera.viewportWidth * 0.03f); // Width of energy bar
        int height = (int)(camera.viewportHeight * 0.3f); // Height of energy bar
        energyBarPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        Gdx.app.log("GameScreen", "Energy bar Pixmap initialized: " + width + "x" + height);

        // Ensure textures are loaded
        if (backgroundTexture == null || fillTexture == null) {
            Gdx.app.error("GameScreen", "Energy bar textures not loaded properly");
            backgroundTexture = createSolidColorTexture(width, height, Color.GRAY);
            fillTexture = createSolidColorTexture(width, height, Color.GREEN);
        }

        updateEnergyBar(); // Create initial texture
    }

    private void updateEnergyBar() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        float energyPercent;
        try {
            energyPercent = (float) player.getEnergy().getCurrentEnergy() / player.getEnergy().getEnergyCap();
            if (Float.isNaN(energyPercent) || energyPercent < 0 || energyPercent > 1) {
                energyPercent = 1.0f; // Fallback to full bar
                Gdx.app.error("GameScreen", "Invalid energy percent: " + energyPercent);
            }
        } catch (Exception e) {
            energyPercent = 1.0f; // Fallback to full bar
            Gdx.app.error("GameScreen", "Failed to calculate energy percent: " + e.getMessage());
        }

        int width = (int)(camera.viewportWidth * 0.03f);
        int height = (int)(camera.viewportHeight * 0.3f);
        int fillHeight = (int)(height * 175 / 215 * energyPercent);
        int fillWidth = (int)(width * 0.5f);
        int fillX = (int)(width * 0.25f);
        int fillY = (int)(height * 8 / 215);

        // Clear Pixmap
        energyBarPixmap.setColor(0, 0, 0, 0);
        energyBarPixmap.fill();

        // Draw background
        try {
            if (!backgroundTexture.getTextureData().isPrepared()) backgroundTexture.getTextureData().prepare();
            Pixmap backgroundPixmap = backgroundTexture.getTextureData().consumePixmap();
            energyBarPixmap.drawPixmap(
                    backgroundPixmap,
                    0, 0, backgroundPixmap.getWidth(), backgroundPixmap.getHeight(),
                    0, 0, width, height
            );
            backgroundPixmap.dispose();
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to draw background: " + e.getMessage());
            energyBarPixmap.setColor(Color.GRAY);
            energyBarPixmap.fillRectangle(0, 0, width, height);
        }

        // Draw fill
        try {
            if (!fillTexture.getTextureData().isPrepared()) fillTexture.getTextureData().prepare();
            Pixmap fillPixmap = fillTexture.getTextureData().consumePixmap();
            energyBarPixmap.drawPixmap(
                    fillPixmap,
                    0, 0, fillPixmap.getWidth(), fillPixmap.getHeight(),
                    fillX, height - fillY - fillHeight, fillWidth, fillHeight
            );
            fillPixmap.dispose();
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to draw fill: " + e.getMessage());
            energyBarPixmap.setColor(Color.GREEN);
            energyBarPixmap.fillRectangle(fillX, height - fillY - fillHeight, fillWidth, fillHeight);
        }

        // Dispose previous texture if exists
        if (energyBarTexture != null) {
            energyBarTexture.dispose();
        }

        // Create new texture
        energyBarTexture = new Texture(energyBarPixmap);
        Gdx.app.log("GameScreen", "Energy bar texture updated: " + energyPercent);
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        stateTime += delta;
        updateEnergyBar(); // Update energy bar texture each frame
        camera.position.set(playerPosition.x, playerPosition.y, 0);
        camera.update();

        Gdx.gl.glClearColor(0.2f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            int tileX = (int)(touchPos.x / TILE_SIZE);
            int tileY = (int)(touchPos.y / TILE_SIZE);

            try {
                Gdx.app.log("Tile Clicked", "Tile: (" + tileX + ", " + tileY + ") \n TileType: " + tileMap[tileY][tileX].type.toString());
                if (toolbarItems.get(selectedToolIndex) != null) {
                    Gdx.app.log("current Item ", toolbarItems.get(selectedToolIndex).getName());
                    toolbarItems.get(selectedToolIndex).useTool(tileMap[tileY][tileX]);
                    updateToolbar(); // Update toolbar after using an item
                }

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        show();
                    }
                }, 0.2f);
            } catch (Exception e) {
                Gdx.app.error("error", e.getMessage());
            }
            toolAnimTime = 0;
            isToolAnimating = true;
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int startX = (int)(camera.position.x - viewport.getWorldWidth() / 2) / TILE_SIZE - 1;
        int startY = (int)(camera.position.y - viewport.getWorldHeight() / 2) / TILE_SIZE - 1;
        int endX = (int)(camera.position.x + viewport.getWorldWidth() / 2) / TILE_SIZE + 1;
        int endY = (int)(camera.position.y + viewport.getWorldHeight() / 2) / TILE_SIZE + 1;

        Texture defaultTexture = tileTextures.get(TileType.EMPTY);

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                if (x >= 0 && y >= 0 && x < tileMap[0].length && y < tileMap.length) {
                    batch.draw(defaultTexture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                if (x >= 0 && y >= 0 && x < tileMap[0].length && y < tileMap.length) {
                    Tile tile = tileMap[y][x];
                    if (tile.type != TileType.EMPTY) {
                        Texture tileTex = getTextureForTileType(tile.type);
                        batch.draw(tileTex, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        if (isNpcTile(tile.type)) {
                            float drawX = (x-2) * TILE_SIZE;
                            float drawY = (y + 1) * TILE_SIZE;
                            batch.draw(GameAssetManager.NPCHOUSE, drawX, drawY, TILE_SIZE*5, TILE_SIZE*5);
                        }
                        if (tile.type == TileType.FORAGING) {
                            try {
                                batch.draw(new Texture("Foraging/" + AllTheItemsInTheGame.getPlantById(tile.id).getName() + ".png"), x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            } catch (NullPointerException e) {
                                Gdx.app.log("error", e.getMessage());
                            }
                        }
                    }
                }
            }
        }

        for (Shop shop : App.getCurrentGame().getShops()) {
            shop.update(playerPosition);
            shop.render(batch);
        }
        if (playerPosition.x >= 200 && playerPosition.x <= 410 && playerPosition.y >= 200 && playerPosition.y <= 410)
            CottageFinal = GameAssetManager.COTTAGEIn;
        else
            CottageFinal = GameAssetManager.COTTAGEOut;
        batch.draw(CottageFinal, 200, 200, TILE_SIZE * 7, TILE_SIZE * 7);
        TextureRegion currentFrame = playerAnimations[moveDirection].getKeyFrame(stateTime, true);
        batch.draw(currentFrame, playerPosition.x, playerPosition.y, TILE_SIZE, TILE_SIZE * 2);

        if (toolTextures.get(selectedToolIndex) != null) {
            Texture toolTex = toolTextures.get(selectedToolIndex);
            boolean flipX = false;
            float toolX = playerPosition.x;
            float toolY = playerPosition.y;

            float animOffset = 5f;

            if (isToolAnimating) {
                toolAnimTime += delta;
                float progress = toolAnimTime / toolAnimDuration;
                if (progress > 1f) {
                    isToolAnimating = false;
                    progress = 1f;
                }
                float curve = (float)Math.sin(progress * Math.PI);
                animOffset *= curve;
            }

            switch (moveDirection) {
                case 0: // Down
                    toolY -= TILE_SIZE * 0.4f - animOffset;
                    toolX += TILE_SIZE * 0.2f;
                    break;
                case 1: // Right
                    toolX += TILE_SIZE * 0.6f + animOffset;
                    toolY += TILE_SIZE * 0.3f;
                    break;
                case 2: // Up
                    toolY += TILE_SIZE * 1.1f + animOffset;
                    toolX += TILE_SIZE * 0.2f;
                    break;
                case 3: // Left
                    flipX = true;
                    toolX -= TILE_SIZE * 0.6f - animOffset;
                    toolY += TILE_SIZE * 0.3f;
                    break;
            }

            batch.draw(
                    toolTex,
                    toolX,
                    toolY,
                    TILE_SIZE,
                    TILE_SIZE,
                    0, 0,
                    toolTex.getWidth(),
                    toolTex.getHeight(),
                    flipX,
                    false
            );
        }

        // Draw energy bar texture
        if (energyBarTexture != null) {
            batch.draw(
                    energyBarTexture,
                    playerPosition.x + 350,
                    playerPosition.y - 150,
                    energyBarTexture.getWidth(),
                    energyBarTexture.getHeight()
            );
        } else {
            Gdx.app.error("GameScreen", "Energy bar texture is null");
        }
        batch.end();

        TextureRegion clockTexture = TimeAndDate.renderClockToTexture();
        batch.begin();
        batch.draw(clockTexture, playerPosition.x + 220, playerPosition.y + 58, TILE_SIZE*30, 15*TILE_SIZE);
        batch.end();

        mainStage.act(delta);
        mainStage.draw();

        if (isMapDialogOpen) {
            dialogStage.act(delta);
            dialogStage.draw();
        }
    }

    public static OrthographicCamera getCamera() {
        return camera;
    }

    private void showMapDialog() {
        if (isMapDialogOpen) return;
        isMapDialogOpen = true;

        if (dialogStage == null) dialogStage = new Stage(viewport, batch);
        dialogStage.clear();

        Dialog mapDialog = new Dialog("Map", skin) {
            @Override
            protected void result(Object object) {
                closeMapDialog();
            }
        };

        final int miniTileSize = 8;
        final int mapWidth = tileMap[0].length;
        final int mapHeight = tileMap.length;
        final int pixmapWidth = mapWidth * miniTileSize;
        final int pixmapHeight = mapHeight * miniTileSize;

        Pixmap pixmap = new Pixmap(pixmapWidth, pixmapHeight, Pixmap.Format.RGBA8888);

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TileType type = tileMap[y][x].type;
                Texture tex = getTextureForTileType(type);
                if (!tex.getTextureData().isPrepared()) tex.getTextureData().prepare();
                Pixmap tilePixmap = tex.getTextureData().consumePixmap();

                pixmap.drawPixmap(
                        tilePixmap,
                        0, 0, tilePixmap.getWidth(), tilePixmap.getHeight(),
                        x * miniTileSize,
                        (mapHeight - y - 1) * miniTileSize,
                        miniTileSize,
                        miniTileSize
                );
                tilePixmap.dispose();
            }
        }

        Texture npcHouseTexture = GameAssetManager.NPCHOUSE;
        if (!npcHouseTexture.getTextureData().isPrepared()) npcHouseTexture.getTextureData().prepare();
        Pixmap npcHousePixmap = npcHouseTexture.getTextureData().consumePixmap();

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TileType type = tileMap[y][x].type;
                if (isNpcTile(type)) {
                    int drawX = x * miniTileSize;
                    int drawY = (mapHeight - y - 1) * miniTileSize;

                    pixmap.drawPixmap(
                            npcHousePixmap,
                            0, 0, npcHousePixmap.getWidth(), npcHousePixmap.getHeight(),
                            drawX, drawY,
                            miniTileSize,
                            miniTileSize
                    );
                }
            }
        }
        npcHousePixmap.dispose();

        Texture shopIconTexture = new Texture("Plank_Cabin_Stage_3.png");
        if (!shopIconTexture.getTextureData().isPrepared()) shopIconTexture.getTextureData().prepare();
        Pixmap shopPixmap = shopIconTexture.getTextureData().consumePixmap();

        for (Shop shop : App.getCurrentGame().getShops()) {
            Vector2 loc = shop.getType().getPosition();
            int shopX = (int)(loc.x / TILE_SIZE);
            int shopY = (int)(loc.y / TILE_SIZE);

            int drawX = shopX * miniTileSize;
            int drawY = (mapHeight - shopY - 1) * miniTileSize;

            pixmap.drawPixmap(
                    shopPixmap,
                    0, 0, shopPixmap.getWidth(), shopPixmap.getHeight(),
                    drawX, drawY,
                    miniTileSize,
                    miniTileSize
            );
        }
        shopPixmap.dispose();
        shopIconTexture.dispose();

        int px = (int)(playerPosition.x / TILE_SIZE);
        int py = (int)(playerPosition.y / TILE_SIZE);
        Texture playerTex = GameAssetManager.ABIGEL;
        if (!playerTex.getTextureData().isPrepared()) playerTex.getTextureData().prepare();
        Pixmap playerPixmap = playerTex.getTextureData().consumePixmap();

        pixmap.drawPixmap(
                playerPixmap,
                0, 0, playerPixmap.getWidth(), playerPixmap.getHeight(),
                px * miniTileSize,
                (mapHeight - py - 1) * miniTileSize,
                miniTileSize,
                miniTileSize
        );
        playerPixmap.dispose();

        mapTexture = new Texture(pixmap);
        pixmap.dispose();

        Image mapImage = new Image(new TextureRegionDrawable(new TextureRegion(mapTexture)));
        ScrollPane scrollPane = new ScrollPane(mapImage, skin);
        scrollPane.setScrollingDisabled(false, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollbarsOnTop(true);

        mapDialog.getContentTable().add(scrollPane)
                .width(600)
                .height(500)
                .pad(10);

        mapDialog.button("Close");
        dialogStage.addActor(mapDialog);
        mapDialog.show(dialogStage);
        Gdx.input.setInputProcessor(dialogStage);
    }

    private Pixmap TextureToPixmap(Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        return texture.getTextureData().consumePixmap();
    }

    private void closeMapDialog() {
        isMapDialogOpen = false;
        if (mapDialog != null) {
            mapDialog.hide();
            mapDialog.remove();
            mapDialog = null;
        }
        mapTexture.dispose();
        dialogStage.clear();
        Gdx.input.setInputProcessor(mainStage);
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
        initEnergyBar();
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