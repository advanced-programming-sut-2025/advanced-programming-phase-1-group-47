package models.enums;

public enum TileType {
    EMPTY("⬜"),
    TREE("🌲"),
    STONE("🔘"),
    FORAGING("🍄"),
    LAKE("🌊"),
    GREENHOUSE("🏡"),
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
    SHEEP("🐑")
    ;

    private final String sticker;

    TileType(String sticker) {
        this.sticker = sticker;
    }

    public String getSticker() {
        return sticker;
    }
}
