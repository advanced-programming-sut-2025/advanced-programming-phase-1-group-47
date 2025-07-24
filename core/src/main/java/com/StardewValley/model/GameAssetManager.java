package com.StardewValley.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    public static Texture GRASS = new Texture("Crafting/Grass_Starter.png");
    public static Texture EMPTY =new Texture("grass.png");
    public static Texture TILLED = new Texture("Rock/Stone_Index32.png");
    public static Texture PLANT = new Texture("Crops/Eggplant.png");
    public static Texture WATER = new Texture("Flooring/water.png");
    public static Texture STONE = new Texture("Rock/Stone_Index32.png");
    public static Texture WALL = new Texture("Flooring/Flooring_52.png");
    public static Texture TREE = new Texture("Trees/AppleTreeLightning.png");
    public static Texture ABIGEL = new Texture("NPC/abigel.png");
    public static Texture SEBASTIAN = new Texture("NPC/sebastian.png");
    public static Texture HARVEY = new Texture("NPC/harvey.png");
    public static Texture LEAH = new Texture("NPC/leah.png");
    public static Texture ROBIN = new Texture("NPC/robin.png");
    public static Texture NPCHOUSE = new Texture("Rustic_Cabin_Stage_3.png");
    public static Texture JOJOMARTOUT = new Texture("Shops/Jojamart.png");
    public static Texture JOJOMARTIN = new Texture("Shops/Jojamartin.png");
    public static Texture BLACKSMITHOUT = new Texture("Shops/BlacksmithOut.png");
    public static Texture BLACKSMITHIN = new Texture("Shops/BlacksmithIn.png");
    public static Texture CARPENTEROUT = new Texture("Shops/CarpenterOut.png");
    public static Texture CARPENTERIN = new Texture("Shops/CarpenterIn.png");
    public static Texture FISHSHOPOUT = new Texture("Shops/FishShopOut.png");
    public static Texture FISHSHOPOT = new Texture("Shops/FishShopIn.png");
    public static Texture MARNIESRANCHEOUT = new Texture("Shops/MarniesRancheOut.png");
    public static Texture MARNIESRANCHIN = new Texture("Shops/MarniesRanchIn.png");
    public static Texture PIERRESOut = new Texture("Shops/PierresOut.png");
    public static Texture PIERRESIN = new Texture("Shops/PierresIn.png");
    public static Texture SALOONOUT = new Texture("Shops/SaloonOut.png");
    public static Texture SALOONIN = new Texture("Shops/SaloonIn.png");
    public static Texture MILKPILL = new Texture("Tools/Milkpail/Milk_Pail.png");
    public static Texture BROKEN_GREENHOUSE = new Texture("Greenhouse/Broken_Greenhouse.png");
    public static Texture GREENHOUSE = new Texture("Greenhouse/greenhouse.png");
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
