package models.enums;

public enum TileType {
    EMPTY("⬜"),
    TREE("🌲"),
    STONE("🔘"),
    FORAGING("🍄"),
    LAKE("🌊"),
    GREENHOUSE("🏡"),
    GREENHOUSEREPAIRED("@@"),
    COTTAGE("🏘️"),
    DOOR("🚪"),
    WALL("🧱"),
    FARMWALL("🌾"),
    QUARRY("⛏️"),
    GRASS("🌿"),

    ABIGEL("👩‍🦰"),
    SEBASTIAN("👨‍🦱"),
    HARVEY("🧑‍⚕️"),
    LEAH("👩‍🎨"),
    ROBIN("👩‍🔧"),
    NPCHOUSE("🏰"),
    PERSON("🧑‍🌾"),
    THUNDERED("🌩️"),

    // فروشگاه‌ها
    BLACKSMITH("⚒️"),
    JOJAMART("🛒"),
    PIERRESSTORE("🏪"),
    CARPENTER("🧰"),
    FISHSHOP("🎣"),
    MARNIESRANCH("🐴"),
    STARDROPSALOON("🍺"),

    // حیوانات
    COW("🐄"),
    DINOSAUR("🦖"),
    DUCK("🦆"),
    GOAT("🐐"),
    HEN("🐔"),
    PIG("🐖"),
    RABBIT("🐇"),
    SHEEP("🐑"),

    MACHINE("M"),

    // کشاورزی
    TILLED("░░"),
    SEED("🌱🌱"),
    PLANT("🪴🪴"),
    GROWN_PLANT("🥬"),

    // سازه‌ها و امکانات
    BARN("🛖"),
    BIG_BARN("🏚️"),
    DELUXE_BARN("🏠"),
    COOP("🐓"),
    BIG_COOP("🏡"),
    DELUXE_COOP("🏤"),
    WELL("🚰"),
    SHIPPING_BIN("📤"),

    CAGE("🪤"),
    COAL("🪨");

    private final String sticker;

    TileType(String sticker) {
        this.sticker = sticker;
    }

    public String getSticker() {
        return sticker;
    }
}
