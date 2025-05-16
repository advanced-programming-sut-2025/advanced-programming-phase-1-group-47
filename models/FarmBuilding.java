package models;

public enum FarmBuilding {
    BARN(1001, 3500, 4, 7, 4, true, false),
    BIG_BARN(1002, 12000, 8, 7, 4, true, false),
    DELUXE_BARN(1003, 25000, 12, 7, 4, true, false),
    COOP(1004, 4000, 4, 6, 3, false, true),
    BIG_COOP(1005, 10000, 8, 6, 3, false, true),
    DELUXE_COOP(1006, 20000, 12, 6, 3, false, true),
    WELL(1007, 1000, 10, 3, 3, false, false),
    SHIPPING_BIN(1008, 0, 20, 1, 1, false, false),
    COTTAGE(1009, 0, -1, 6, 6, false, false),
    GREENHOUSE(1010, 0, -1, 8, 7, false, false),
    QUARRY(1012, 0, -1, 10, 10, false, false);

    private final int id;
    private final int price;
    private final Integer capacity;
    private final Integer height;
    private final Integer width;
    private final Boolean isBarn;
    private final Boolean isCoop;

    FarmBuilding(int id, int price, Integer capacity, Integer height, Integer width, Boolean isBarn, Boolean isCoop) {
        this.id = id;
        this.price = price;
        this.capacity = capacity;
        this.height = height;
        this.width = width;
        this.isBarn = isBarn;
        this.isCoop = isCoop;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public Boolean getIsBarn() {
        return isBarn;
    }

    public Boolean getIsCoop() {
        return isCoop;
    }
}
