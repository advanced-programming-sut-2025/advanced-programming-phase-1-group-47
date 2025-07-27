package com.StardewValley.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    public static final Texture GRASS = new Texture("Crafting/Grass_Starter.png");
    public static final Texture EMPTY =new Texture("grass.png");
    public static final Texture TILLED = new Texture("Flooring/Flooring_62.png");
    public static final Texture PLANT = new Texture("Crops/Eggplant.png");
    public static final Texture WATER = new Texture("Flooring/water.png");
    public static final Texture STONE = new Texture("Rock/Stone_Index32.png");
    public static final Texture WALL = new Texture("Flooring/Flooring_52.png");
    public static final Texture TREE = new Texture("Trees/Oak_Stage_3.png");
    public static final Texture ABIGEL = new Texture("NPC/abigel.png");
    public static final Texture SEBASTIAN = new Texture("NPC/sebastian.png");
    public static final Texture HARVEY = new Texture("NPC/harvey.png");
    public static final Texture LEAH = new Texture("NPC/leah.png");
    public static final Texture ROBIN = new Texture("NPC/robin.png");
    public static final Texture NPCHOUSE = new Texture("Rustic_Cabin_Stage_3.png");
    public static final Texture JOJOMARTOUT = new Texture("Shops/Jojamart.png");
    public static final Texture JOJOMARTIN = new Texture("Shops/Jojamartin.png");
    public static final Texture BLACKSMITHOUT = new Texture("Shops/BlacksmithOut.png");
    public static final Texture BLACKSMITHIN = new Texture("Shops/BlacksmithIn.png");
    public static final Texture CARPENTEROUT = new Texture("Shops/CarpenterOut.png");
    public static final Texture CARPENTERIN = new Texture("Shops/CarpenterIn.png");
    public static final Texture FISHSHOPOUT = new Texture("Shops/FishShopOut.png");
    public static final Texture FISHSHOPOT = new Texture("Shops/FishShopIn.png");
    public static final Texture MARNIESRANCHEOUT = new Texture("Shops/MarniesRancheOut.png");
    public static final Texture MARNIESRANCHIN = new Texture("Shops/MarniesRanchIn.png");
    public static final Texture PIERRESOut = new Texture("Shops/PierresOut.png");
    public static final Texture PIERRESIN = new Texture("Shops/PierresIn.png");
    public static final Texture SALOONOUT = new Texture("Shops/SaloonOut.png");
    public static final Texture SALOONIN = new Texture("Shops/SaloonIn.png");
    public static final Texture MILKPILL = new Texture("Tools/Milkpail/Milk_Pail.png");
    public static final Texture BROKEN_GREENHOUSE = new Texture("Greenhouse/Broken_Greenhouse.png");
    public static final Texture GREENHOUSE = new Texture("Greenhouse/greenhouse.png");
    public static final Texture COTTAGEIn = new Texture("Cottage/CottageIn.png");
    public static final Texture COTTAGEOut = new Texture("Cottage/CottageOut.png");
    public static final Texture CLOCK = new Texture("Clock/Clock.png");
    public static final Texture CLOCK_ALL = new Texture("Clock/Clock_All.png");
    public static final TextureRegion CLOCK_MAIN = new TextureRegion(CLOCK_ALL, 0, 0, 72, 59);
    public static final TextureRegion CLOCK_ARROW = new TextureRegion(CLOCK_ALL, 72, 0, 8, 18);
    public static final TextureRegion[] ClOCK_MANNERS = new TextureRegion[12];
    static {
        for (int i = 0; i < 12; i++) {
            ClOCK_MANNERS[i] = new TextureRegion(CLOCK_ALL, 80 + i % 4 * 13, i / 4 * 9, 13, 9);
        }
    }
    public static final BitmapFont MAIN_FONT = new BitmapFont();
    // Farming

    public static final Texture CARROT = new Texture("Crops/Carrot.png");
    public static final Texture CARROT_SEEDS = new Texture("Crops/Carrot_Seeds.png");
    public static final Texture CARROT_STAGE_1 = new Texture("Crops/Carrot_Stage_1.png");

    public static final Texture CACTUS_FRUIT = new Texture("Crops/Cactus_Fruit.png");
    public static final Texture CACTUS_SEEDS = new Texture("Crops/Cactus_Seeds.png");
    public static final Texture CACTUS_STAGE_1 = new Texture("Crops/Cactus_Stage_1.png");


    // End Farming
    // ذخیره تکسچرها برای آزادسازی حافظه
    private final List<Texture> loadedTextures = new ArrayList<>();
    private GameAssetManager() {
    }
    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public void dispose() {
        for (Texture tex : loadedTextures) {
            tex.dispose();
        }

        skin.dispose();
    }
}
