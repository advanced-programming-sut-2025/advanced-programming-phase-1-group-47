package com.StardewValley.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    public static Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Daghestan.mp3"));
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    public static final Texture GRASS = new Texture("Crafting/Grass_Starter.png");
    public static final Texture EMPTY =new Texture("grass.png");
    public static final Texture TILLED = new Texture("Flooring/Flooring_62.png");
    public static final Texture PLANT = new Texture("Crops/Eggplant.png");
    public static final Texture WATER = new Texture("Flooring/water.png");
    public static final Texture STONE = new Texture("Rock/Stone_Index32.png");
    public static final Texture WALL = new Texture("Flooring/Flooring_52.png");
    public static final Texture WOOD = new Texture("Trees/Wood.png");
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
    public static Texture WHITE_PIXEL;
    public static final TextureRegion[][] RAIN = TextureRegion.split(new Texture("Weather/Rain.png"), 41, 130);
    public static final TextureRegion[][] STORM = TextureRegion.split(new Texture("Weather/Storm.png"), 192, 384);
    public static final Texture LIGHTENING = new Texture("Weather/lightening.png");
    public static final Texture SNOW = new Texture("Weather/Snow.png");
    public static final Texture JOJOCOLA = new Texture("Concessions/Joja_Cola.png");

    public static void load() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        WHITE_PIXEL = new Texture(pixmap);
        pixmap.dispose();

    }
    public static final Texture DIALOG = new Texture("dialog.png");
    public static final BitmapFont MAIN_FONT = new BitmapFont();
    // Farming
    public static final Texture GREEN_SQUARE = new Texture("Night/Green.png");
    public static final Texture YELLOW_SQUARE = new Texture("Night/Yellow.png");
    public static final Texture ENERGY_BAR_EMPTY = new Texture("energyBarEmpty.jpg");

    public static final Texture CARROT = new Texture("Crops/Carrot.png");
    public static final Texture CARROT_STAGE_1 = new Texture("Crops/Carrot_Stage_1.png");

    public static final Texture CACTUS_FRUIT = new Texture("Crops/Cactus_Fruit.png");
    public static final Texture CACTUS_SEEDS = new Texture("Crops/Cactus_Seeds.png");
    public static final Texture CACTUS_STAGE_1 = new Texture("Crops/Cactus_Stage_1.png");

    // Crops
    public static final Texture RICE = new Texture("Crops/Rice.png");
    public static final Texture ANCIENT_SEED = new Texture("Crops/Ancient_Seed.png");
    public static final Texture GRASS_STARTER = new Texture("Crafting/Blue_Grass_Starter.png");
    public static final Texture SUGAR = new Texture("Crops/Sugar.png");
    public static final Texture WHITE_FLOUR = new Texture("Crops/Cauliflower_Seeds.png");

    // Spring Seeds
    public static final Texture PARSNIP_SEEDS = new Texture("Crops/Parsnip_Seeds.png");
    public static final Texture BEAN_STARTER = new Texture("Crops/Bean_Starter.png");
    public static final Texture CAULIFLOWER_SEEDS = new Texture("Crops/Cauliflower_Seeds.png");
    public static final Texture POTATO_SEEDS = new Texture("Crops/Potato_Seeds.png");
    public static final Texture STRAWBERRY_SEEDS = new Texture("Crops/Strawberry_Seeds.png");
    public static final Texture TULIP_BULB = new Texture("Crops/Tulip_Bulb.png");
    public static final Texture KALE_SEEDS = new Texture("Crops/Kale_Seeds.png");
    public static final Texture COFFEE_BEANS = new Texture("Crops/Coffee_Bean.png");
    public static final Texture CARROT_SEEDS = new Texture("Crops/Carrot_Seeds.png");
    public static final Texture RHUBARB_SEEDS = new Texture("Crops/Rhubarb_Seeds.png");
    public static final Texture JAZZ_SEEDS = new Texture("Crops/Jazz_Seeds.png");

    // Summer Seeds
    public static final Texture TOMATO_SEEDS = new Texture("Crops/Tomato_Seeds.png");
    public static final Texture PEPPER_SEEDS = new Texture("Crops/Pepper_Seeds.png");
    public static final Texture WHEAT_SEEDS = new Texture("Crops/Wheat.png");
    public static final Texture SUMMER_SQUASH_SEEDS = new Texture("Crops/Summer_Squash_Seeds.png");
    public static final Texture RADISH_SEEDS = new Texture("Crops/Radish_Seeds.png");
    public static final Texture MELON_SEEDS = new Texture("Crops/Melon_Seeds.png");
    public static final Texture HOPS_STARTER = new Texture("Crops/Hops_Starter.png");
    public static final Texture POPPY_SEEDS = new Texture("Crops/Poppy_Seeds.png");
    public static final Texture SPANGLE_SEEDS = new Texture("Crops/Spangle_Seeds.png");
    public static final Texture STARFRUIT_SEEDS = new Texture("Crops/Starfruit_Seeds.png");
    public static final Texture SUNFLOWER_SEEDS = new Texture("Crops/Sunflower_Seeds.png");

    // Fall Seeds
    public static final Texture CORN_SEEDS = new Texture("Crops/Corn_Seeds.png");
    public static final Texture EGGPLANT_SEEDS = new Texture("Crops/Eggplant_Seeds.png");
    public static final Texture PUMPKIN_SEEDS = new Texture("Crops/Pumpkin_Seeds.png");
    public static final Texture BROCCOLI_SEEDS = new Texture("Crops/Broccoli_Seeds.png");
    public static final Texture AMARANTH_SEEDS = new Texture("Crops/Amaranth_Seeds.png");
    public static final Texture GRAPE_STARTER = new Texture("Crops/Grape_Starter.png");
    public static final Texture BEET_SEEDS = new Texture("Crops/Beet_Seeds.png");
    public static final Texture YAM_SEEDS = new Texture("Crops/Yam_Seeds.png");
    public static final Texture BOK_CHOY_SEEDS = new Texture("Crops/Bok_Choy_Seeds.png");
    public static final Texture CRANBERRY_SEEDS = new Texture("Crops/Cranberry_Seeds.png");
    public static final Texture FAIRY_SEEDS = new Texture("Crops/Fairy_Seeds.png");
    public static final Texture RARE_SEED = new Texture("Crops/Rare_Seed.png");
    public static final Texture HAY = new Texture("Foraging/Sap.png");
    // Winter Seeds
    public static final Texture POWDERMELON_SEEDS = new Texture("Crops/Powdermelon_Seeds.png");

    private final List<Texture> loadedTextures = new ArrayList<>();
        // --- Barn Textures ---
        public static final Texture QUARTZ = new Texture("Mineral/Quartz.png");
    public static final Texture EARTH_CRYSTAL = new Texture("Mineral/Earth_Crystal.png");
    public static final Texture FROZEN_TEAR = new Texture("Mineral/Frozen_Tear.png");
    public static final Texture FIRE_QUARTZ = new Texture("Mineral/Fire_Quartz.png");
    public static final Texture EMERALD = new Texture("Mineral/Soapstone.png");
    public static final Texture AQUAMARINE = new Texture("Mineral/Lunarite.png");
    public static final Texture RUBY = new Texture("Mineral/Dolomite.png");
    public static final Texture AMETHYST = new Texture("Mineral/Star_Shards.png");
    public static final Texture TOPAZ = new Texture("Mineral/Mudstone.png");
    public static final Texture JADE = new Texture("Mineral/Jasper.png");
    public static final Texture DIAMOND = new Texture("Mineral/Obsidian.png");
    public static final Texture PRISMATIC_SHARD = new Texture("Mineral/Thunder_Egg.png");
    public static final Texture COPPER_ORE = new Texture("Mineral/Pyrite.png");
    public static final Texture IRON_ORE = new Texture("Mineral/Nekoite.png");
    public static final Texture GOLD_ORE = new Texture("Mineral/Marble.png");
    public static final Texture IRIDIUM_ORE = new Texture("Mineral/Lemon_Stone.png");
    public static final Texture COAL = new Texture("Mineral/Bixite.png");

    // Foraging Crop Textures
    public static final Texture COMMON_MUSHROOM = new Texture("Foraging/Common Mushroom.png");
    public static final Texture DAFFODIL = new Texture("Foraging/Daffodil.png");
    public static final Texture DANDELION = new Texture("Foraging/Dandelion.png");
    public static final Texture LEEK = new Texture("Foraging/Leek.png");
    public static final Texture MOREL = new Texture("Foraging/Morel.png");
    public static final Texture SALMONBERRY = new Texture("Foraging/Salmonberry.png");
    public static final Texture SPRING_ONION = new Texture("Foraging/Spring Onion.png");
    public static final Texture WILD_HORSERADISH = new Texture("Foraging/Wild Horseradish.png");

    public static final Texture FIDDLEHEAD_FERN = new Texture("Foraging/Fiddlehead Fern.png");
    public static final Texture GRAPE = new Texture("Foraging/Grape.png");
    public static final Texture RED_MUSHROOM = new Texture("Foraging/Red Mushroom.png");
    public static final Texture SPICE_BERRY = new Texture("Foraging/Spice Berry.png");
    public static final Texture SWEET_PEA = new Texture("Foraging/Sweet Pea.png");

    public static final Texture BLACKBERRY = new Texture("Foraging/Blackberry.png");
    public static final Texture CHANTERELLE = new Texture("Foraging/Chanterelle.png");
    public static final Texture HAZELNUT = new Texture("Foraging/Hazelnut.png");
    public static final Texture PURPLE_MUSHROOM = new Texture("Foraging/Purple Mushroom.png");
    public static final Texture WILD_PLUM = new Texture("Foraging/Wild Plum.png");

    public static final Texture CROCUS = new Texture("Foraging/Crocus.png");
    public static final Texture CRYSTAL_FRUIT = new Texture("Foraging/Crystal Fruit.png");
    public static final Texture HOLLY = new Texture("Foraging/Holly.png");
    public static final Texture SNOW_YAM = new Texture("Foraging/Snow Yam.png");
    public static final Texture WINTER_ROOT = new Texture("Foraging/Winter Root.png");


    public static Texture BARN_OUT_TEXTURE = new Texture("Farm_Buildings/Barn.png");
    public static Texture BARN_IN_TEXTURE = new Texture("Farm_Buildings/Barn_Interior.png");

    public static Texture BIG_BARN_OUT_TEXTURE = new Texture("Farm_Buildings/Big_Barn.png");
    public static Texture BIG_BARN_IN_TEXTURE = new Texture("Farm_Buildings/Big_Barn_Interior.png");

    public static Texture DELUXE_BARN_OUT_TEXTURE = new Texture("Farm_Buildings/Deluxe_Barn.png");
    public static Texture DELUXE_BARN_IN_TEXTURE = new Texture("Farm_Buildings/Deluxe_Barn_Interior.png");

    public static Texture COOP_OUT_TEXTURE = new Texture("Farm_Buildings/Coop.png");
    public static Texture COOP_IN_TEXTURE = new Texture("Farm_Buildings/Big_Coop_Interior.png");

    public static Texture BIG_COOP_OUT_TEXTURE = new Texture("Farm_Buildings/Big_Coop.png");
    public static Texture BIG_COOP_IN_TEXTURE = new Texture("Farm_Buildings/Big_Coop_Interior.png");

    public static Texture DELUXE_COOP_OUT_TEXTURE = new Texture("Farm_Buildings/Deluxe_Coop.png");
    public static Texture DELUXE_COOP_IN_TEXTURE = new Texture("Farm_Buildings/Deluxe_Coop_Interior.png");

    public static Texture WELL_OUT_TEXTURE = new Texture("Farm_Buildings/Well.png");
    public static Texture WELL_IN_TEXTURE = new Texture("Farm_Buildings/Well.png");

    public static Texture SHIPPING_BIN_OUT_TEXTURE = new Texture("Farm_Buildings/Shipping_Bin_Anim.png");
    public static Texture SHIPPING_BIN_IN_TEXTURE = new Texture("Farm_Buildings/Shipping_Bin_Anim.png");

    public static Texture COW_TEXTURE = new Texture("Animals/White_Cow.png");
    public static Texture DINOSAUR_TEXTURE = new Texture("Animals/Dinosaur.png");
    public static Texture DUCK_TEXTURE = new Texture("Animals/Duck.png");
    public static Texture GOAT_TEXTURE = new Texture("Animals/Goat.png");
    public static Texture HEN_TEXTURE = new Texture("Animals/White_Chicken.png");
    public static Texture PIG_TEXTURE = new Texture("Animals/Pig.png");
    public static Texture RABBIT_TEXTURE = new Texture("Animals/Rabbit.png");
    public static Texture SHEEP_TEXTURE = new Texture("Animals/Sheep.png");

    public static Texture BEER = new Texture("Artisan_good/Beer.png");
    public static Texture SALAD = new Texture("Foraging/Salad.png");
    public static Texture BREAD = new Texture("Foraging/Bread.png");
    public static Texture SPAGHETTI = new Texture("Crops/Spaghetti.png");
    public static Texture PIZZA = new Texture("Crops/Pizza.png");
    public static Texture COFFEE = new Texture("Artisan_good/Coffee.png");

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
