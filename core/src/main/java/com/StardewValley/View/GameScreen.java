package com.StardewValley.View;

import com.StardewValley.model.App;
import com.StardewValley.model.Game;
import com.StardewValley.model.Map;
import com.StardewValley.model.Tile;
import com.StardewValley.model.enums.TileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map.Entry;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private static final int TILE_SIZE = 30;
    private Tile[][] tileMap;
    private Vector2 playerPosition;
    private float speed = 350f;
    private float stateTime;
    private TextureAtlas playerAtlas;
    private Animation<TextureRegion>[] playerAnimations;
    private int moveDirection = 2; // 0=up, 1=right, 2=down, 3=left

    private HashMap<TileType, Texture> tileTextures;
    private Texture playerTexture;
    private Animation<TextureRegion> walkAnimation;

    // ساختمان‌ها و NPCها (جدا از tile)
    private Texture barnTexture;
    private Texture npcAbigailTexture;
    private Texture shopTexture;

    // مکان‌های ویژه
    private Vector2 barnPos = new Vector2(30 * TILE_SIZE, 30 * TILE_SIZE);
    private Vector2 npcPos = new Vector2(35 * TILE_SIZE, 32 * TILE_SIZE);
    private Vector2 shopPos = new Vector2(40 * TILE_SIZE, 28 * TILE_SIZE);

    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 400, camera);
        batch = new SpriteBatch();

        playerPosition = new Vector2(100, 100);

        loadTextures();
        setupGame();

        TextureRegion[][] tmp = TextureRegion.split(playerTexture, playerTexture.getWidth() / 4, playerTexture.getHeight());
        walkAnimation = new Animation<>(0.1f, tmp[0]);
        stateTime = 0f;
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

        // انواع tileها
        tileTextures.put(TileType.GRASS, new Texture("Crafting/Grass_Starter.png"));
        tileTextures.put(TileType.EMPTY, new Texture("grass.png"));
        tileTextures.put(TileType.TILLED, new Texture("bar.png"));
        tileTextures.put(TileType.STONE, new Texture("Rock/Stone_Index32.png"));
        tileTextures.put(TileType.PLANT, new Texture("Crops/Eggplant.png"));
        tileTextures.put(TileType.LAKE, new Texture("Flooring/water.png"));
        tileTextures.put(TileType.WALL, new Texture("Flooring/Flooring_52.png"));
        tileTextures.put(TileType.TREE, new Texture("Resource/Hardwood.png"));
        tileTextures.put(TileType.HEN, new Texture("Animals/Dinosaur.png"));
        tileTextures.put(TileType.COW, new Texture("Animals/Dinosaur.png"));

        // شخصیت بازیکن
        playerTexture = new Texture("abigel.png");

        // اشیاء و شخصیت‌های خاص
        barnTexture = new Texture("Barn_Interior.png");
        npcAbigailTexture = new Texture("abigel.png");
        shopTexture = new Texture("Blacksmith_Interior.png");
    }

    private void setupGame() {
        try {
            Game newGame = new Game();
            String[] farmNames = new String[] {"1", "2", "3", "4"};
            Map newMap = new Map(farmNames);
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

        if (!moving) {
            stateTime = 0; // Stop animation when idle
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

        // 1. کشیدن کل مپ به صورت grass (EMPTY)
        Texture defaultTexture = tileTextures.get(TileType.EMPTY);
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                if (x >= 0 && y >= 0 && x < tileMap[0].length && y < tileMap.length) {
                    batch.draw(defaultTexture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // 2. رسم tileهای خاص روی grass
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                if (x >= 0 && y >= 0 && x < tileMap[0].length && y < tileMap.length) {
                    Tile tile = tileMap[y][x];
                    if (tile.type != TileType.EMPTY) {
                        Texture tileTex = getTextureForTileType(tile.type);
                        batch.draw(tileTex, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }

        // 3. ساختمان‌ها و NPCها
        batch.draw(barnTexture, barnPos.x, barnPos.y, 120, 100);
        batch.draw(npcAbigailTexture, npcPos.x, npcPos.y, 32, 48);
        batch.draw(shopTexture, shopPos.x, shopPos.y, 100, 80);

        Animation<TextureRegion> currentAnimation = playerAnimations[moveDirection];
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, playerPosition.x, playerPosition.y, TILE_SIZE, TILE_SIZE * 2);

        batch.end();
    }

    @Override public void resize(int width, int height) { viewport.update(width, height); }
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        for (Entry<TileType, Texture> entry : tileTextures.entrySet()) {
            entry.getValue().dispose();
        }
        playerAtlas.dispose();

        barnTexture.dispose();
        npcAbigailTexture.dispose();
        shopTexture.dispose();
    }
}
