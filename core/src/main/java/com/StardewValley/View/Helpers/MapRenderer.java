package com.StardewValley.View.Helpers;

import com.StardewValley.model.*;
import com.StardewValley.model.enums.TileType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.HashMap;

public class MapRenderer {

    private Tile[][] tileMap;
    private HashMap<TileType, Texture> tileTextures;
    private static final int TILE_SIZE = 30;

    public MapRenderer() {
        try {
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

        loadTileTextures();
    }

    private void loadTileTextures() {
        tileTextures = new HashMap<>();
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
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        int startX = (int) (camera.position.x - camera.viewportWidth / 2) / TILE_SIZE - 1;
        int startY = (int) (camera.position.y - camera.viewportHeight / 2) / TILE_SIZE - 1;
        int endX = (int) (camera.position.x + camera.viewportWidth / 2) / TILE_SIZE + 1;
        int endY = (int) (camera.position.y + camera.viewportHeight / 2) / TILE_SIZE + 1;

        for (int y = startY; y <= endY; y++) {
            if (y < 0 || y >= tileMap.length) continue;
            for (int x = startX; x <= endX; x++) {
                if (x < 0 || x >= tileMap[0].length) continue;

                Tile tile = tileMap[y][x];
                Texture tex = tileTextures.get(tile.type);
                if (tex == null) tex = tileTextures.get(TileType.EMPTY);

                batch.draw(tex, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public void dispose() {
        // دقت کن که Textureها احتمالا در جای دیگه مدیریت میشن پس اینجا لازم نیست Dispose کنیم
    }
}
