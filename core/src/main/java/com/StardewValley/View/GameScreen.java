package com.StardewValley.View;

import com.StardewValley.model.*;
import com.StardewValley.model.Game;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
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
    private Skin skin;
    private Dialog mapDialog;
    Texture mapTexture;
    private boolean isMapDialogOpen = false;

    // Toolbar
    private static final int TOOLBAR_SIZE = 12;
    private Stack[] toolbarSlots = new Stack[TOOLBAR_SIZE];
    private int selectedToolIndex = 0;
    private ArrayList<Texture> toolTextures = new ArrayList<>();
    private Texture emptySlotTexture;

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
        tileTextures.put(TileType.TILLED, GameAssetManager.TILLED);
        tileTextures.put(TileType.STONE, GameAssetManager.STONE);
        tileTextures.put(TileType.PLANT, GameAssetManager.PLANT);
        tileTextures.put(TileType.LAKE, GameAssetManager.WATER);
        tileTextures.put(TileType.WALL, GameAssetManager.WALL);
        tileTextures.put(TileType.TREE, GameAssetManager.TREE);
        tileTextures.put(TileType.ROBIN, GameAssetManager.ROBIN);
        tileTextures.put(TileType.ABIGEL, GameAssetManager.ABIGEL);
        tileTextures.put(TileType.LEAH, GameAssetManager.LEAH);
        tileTextures.put(TileType.SEBASTIAN, GameAssetManager.SEBASTIAN);
        tileTextures.put(TileType.HARVEY, GameAssetManager.HARVEY);
        emptySlotTexture = new Texture("bar.png");
        for(Item x : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()){
            if (x.getImage()!=null)
                toolTextures.add(x.getImage());
        }while (toolTextures.size() < TOOLBAR_SIZE) {
            toolTextures.add(null);
        }
    }

    private void setupGame() {
        try {
            Game newGame = new Game();
            Map newMap = new Map(new String[]{"2", "1", "3", "4"});
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
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerPosition.x += speed * delta;
            moveDirection = 1;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerPosition.y += speed * delta;
            moveDirection = 2;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerPosition.y -= speed * delta;
            moveDirection = 0;
            moving = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) showMapDialog();

        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            if (toolTextures.get(i) == null || toolTextures.get(i).equals(emptySlotTexture)) {
                continue;  // رد شدن از ابزارهای خالی، ولی حلقه ادامه پیدا کنه
            }
            // کلیدهای انتخاب ابزار از 1 تا 9
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
            int direction = scrollDelta > 0 ? 1 : -1; // wheel up => 1, down => -1
            int originalIndex = selectedToolIndex;

            do {
                selectedToolIndex = (selectedToolIndex + direction + TOOLBAR_SIZE) % TOOLBAR_SIZE;
            } while ((toolTextures.get(selectedToolIndex) == null ||
                    toolTextures.get(selectedToolIndex).equals(emptySlotTexture)) &&
                    selectedToolIndex != originalIndex);

            updateToolbarHighlight();
            scrollDelta = 0; // reset after handling
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

    private Texture getTextureForTileType(TileType type) {
        Texture tex = tileTextures.get(type);
        return (tex != null) ? tex : tileTextures.get(TileType.EMPTY);
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        stateTime += delta;

        camera.position.set(playerPosition.x, playerPosition.y, 0);
        camera.update();

        Gdx.gl.glClearColor(0.2f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
                            float drawX = (x-2) * TILE_SIZE ;
                            float drawY = (y + 1) * TILE_SIZE; // بالای کاشی فعلی
                            batch.draw(GameAssetManager.NPCHOUSE, drawX, drawY, TILE_SIZE*5, TILE_SIZE*5);
                        }

//                        if(tile.type == TileType.GREENHOUSE) {
//                            Gdx.app.log("GameScreen", "Greenhouse");
//                            batch.draw(GameAssetManager.BROKEN_GREENHOUSE, x, y, TILE_SIZE*5, TILE_SIZE*5);
//                        }
                    }
                }
            }
        }

        for (Shop shop : App.getCurrentGame().getShops()) {
            shop.update(playerPosition);
            shop.render(batch);
        }
        TextureRegion currentFrame = playerAnimations[moveDirection].getKeyFrame(stateTime, true);
        batch.draw(currentFrame, playerPosition.x, playerPosition.y, TILE_SIZE, TILE_SIZE * 2);

        // 5. رسم ابزار بازیکن
        if (toolTextures.get(selectedToolIndex) != null) {
            Texture toolTex = toolTextures.get(selectedToolIndex);
            boolean flipX = false;
            float toolX = playerPosition.x;
            float toolY = playerPosition.y;

            switch (moveDirection) {
                case 0: // پایین
                    toolY -= TILE_SIZE * 0.4f;
                    toolX += TILE_SIZE * 0.2f;
                    break;
                case 1: // راست
                    toolX += TILE_SIZE * 0.6f;
                    toolY += TILE_SIZE * 0.3f;
                    break;
                case 2: // بالا
                    toolY += TILE_SIZE * 1.1f;
                    toolX += TILE_SIZE * 0.2f;
                    break;
                case 3: // چپ
                    flipX = true;
                    toolX -= TILE_SIZE * 0.6f;
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

        batch.end();

        mainStage.act(delta);
        mainStage.draw();

        if (isMapDialogOpen) {
            dialogStage.act(delta);
            dialogStage.draw();
        }
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

        // -------- 1. ترسیم پس‌زمینه‌ی کاشی‌ها --------
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

        // -------- 2. رسم خانه‌های NPC (روی tileهای خاص) --------
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
                            miniTileSize, miniTileSize
                    );
                }
            }
        }
        npcHousePixmap.dispose();

        Texture shopIconTexture = new Texture("Plank_Cabin_Stage_3.png"); // آیکون فروشگاه (مثلاً 16x16)
        if (!shopIconTexture.getTextureData().isPrepared()) shopIconTexture.getTextureData().prepare();
        Pixmap shopPixmap = shopIconTexture.getTextureData().consumePixmap();

        for (Shop shop : App.getCurrentGame().getShops()) {
            Vector2 loc = shop.getType().getPosition(); // پیکسل
            int shopX = (int)(loc.x / TILE_SIZE);
            int shopY = (int)(loc.y / TILE_SIZE);

            int drawX = shopX * miniTileSize;
            int drawY = (mapHeight - shopY - 1) * miniTileSize;

            pixmap.drawPixmap(
                    shopPixmap,
                    0, 0, shopPixmap.getWidth(), shopPixmap.getHeight(),
                    drawX, drawY,
                    miniTileSize, miniTileSize
            );
        }
        shopPixmap.dispose();
        shopIconTexture.dispose();

        // -------- 4. رسم بازیکن --------
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

        // -------- 5. نهایی‌سازی و نمایش --------
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
        // WARNING: only works with textures created from Pixmap or file (not FrameBuffers)
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
    }

    @Override
    public void show() {
        mainStage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                scrollDelta += amountY; // بالا 1، پایین -1
                return true;
            }
        });

        Table toolbar = new Table();
        toolbar.bottom().center().padBottom(10);
        toolbar.setFillParent(true);

        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Texture tex = toolTextures.get(i);
            TextureRegionDrawable toolDrawable = new TextureRegionDrawable(
                    new TextureRegion((tex != null) ? tex : emptySlotTexture));
            Image icon = new Image(toolDrawable);
            icon.setSize(36, 36);

            Container<Image> container = new Container<>(icon);
            container.background(new TextureRegionDrawable(new TextureRegion(new Texture("white.png"))));
            container.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
            container.size(44, 44);

            Stack stack = new Stack();
            stack.add(container);
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
    }
}