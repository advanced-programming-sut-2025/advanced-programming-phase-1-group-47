package models.enums;

public enum TileType {
    // موارد قبلی
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

    ABIGEL("👩‍🦰"),
    SEBASTIAN("👨‍🦱"),
    HARVEY("🧑‍⚕️"),
    LEAH("👩‍🎨"),
    ROBIN("👩‍🔧"),
    NPCHOUSE("🏰"),
    PERSON("🧑‍🌾"),
    QUARRY("⛏️"),

    BLACKSMITH("⚒️"),
    JOJAMART("🛒"),
    PIERRESSTORE("🏪"),
    CARPENTER("🧰"),
    FISHSHOP("🎣"),
    MARNIESRANCH("🐴"),
    STARDROPSALOON("🍺");

    private final String sticker;

    TileType(String sticker) {
        this.sticker = sticker;
    }

    public String getSticker() {
        return sticker;
    }
}