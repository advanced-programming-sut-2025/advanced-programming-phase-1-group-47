package models.enums;

public enum TileType {
    EMPTY("⬜"),
    TREE("🌲"),
    STONE("🔘"),
    FORAGING("🍄"),
    LAKE("🌊"),
    GREENHOUSE("🏡"),
    COTTAGE("🏘️"),
    STORE("🏬"),
    PERSON("🧑‍🌾"),
    QUARRY("⛏️"),
    ;

    private final String sticker;

    TileType(String sticker) {
        this.sticker = sticker;
    }

    public String getSticker() {
        return sticker;
    }
}
