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

    ABIGEL("👩‍🦰"),
    SEBASTIAN("👨‍🦱"),
    HARVEY("🧑‍⚕️"),
    LEAH("👩‍🎨"),
    ROBIN("👩‍🔧"),
    NPCHOUSE("🏰"),
    PERSON("🧑‍🌾"),
    THUNDERED("🌩️"),
    // فروشگاه ها
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

    // کشاورزی
    TILLED("░"),
    SEED("🌱"),
    PLANT("🌿"),
    GROWN_PLANT("🥬");

    private final String sticker;

    TileType(String sticker) {
        this.sticker = sticker;
    }

    public String getSticker() {
        return sticker;
    }
}
