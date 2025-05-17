package models.enums;

public enum FishType {
    SALMON(1050, "Salmon", 75, Season.FALL, false),
    SARDINE(1051, "Sardine", 40, Season.FALL, false),
    SHAD(1052, "Shad", 60, Season.FALL, false),
    BLUE_DISCUS(1053, "Blue Discus", 120, Season.FALL, false),
    MIDNIGHT_CARP(1054, "Midnight Carp", 150, Season.WINTER, false),
    SQUID(1055, "Squid", 80, Season.WINTER, false),
    TUNA(1056, "Tuna", 100, Season.WINTER, false),
    PERCH(1057, "Perch", 55, Season.WINTER, false),
    FLOUNDER(1058, "Flounder", 100, Season.SPRING, false),
    LIONFISH(1059, "Lionfish", 100, Season.SPRING, false),
    HERRING(1060, "Herring", 30, Season.SPRING, false),
    GHOSTFISH(1061, "Ghostfish", 45, Season.SPRING, false),
    TILAPIA(1062, "Tilapia", 75, Season.SUMMER, false),
    DORADO(1063, "Dorado", 100, Season.SUMMER, false),
    SUNFISH(1064, "Sunfish", 30, Season.SUMMER, false),
    RAINBOW_TROUT(1065, "Rainbow Trout", 65, Season.SUMMER, false),
    LEGEND(1066, "Legend", 5000, Season.SPRING, true),
    GLACIERFISH(1067, "Glacierfish", 1000, Season.WINTER, true),
    ANGLER(1068, "Angler", 900, Season.FALL, true),
    CRIMSONFISH(1069, "Crimsonfish", 1500, Season.SUMMER, true);

    private final int id;
    private final String name;
    private final int price;
    private final Season season;
    private final boolean isLegendary;

    FishType(int id, String name, int price, Season season, boolean isLegendary) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.season = season;
        this.isLegendary = isLegendary;
    }

    public int getId() {
        return id;
    }

    public int getSellPrice() {
        return price;
    }

    public int getEnergy() {
        return 0;
    }

    public String getName() {
        return name.toLowerCase();
    }

    public Integer getContainingEnergy() {
        return getEnergy();
    }

    public Season getSeason() {
        return season;
    }

    public boolean isLegendary() {
        return isLegendary;
    }
}
