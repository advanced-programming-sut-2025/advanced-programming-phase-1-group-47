package models.enums;

public enum BackpackType {
    Normal(12),
    Big(24),
    Deluxe(2147483647);
    private final int capacity;

    private BackpackType(int capacity) {
        this.capacity = capacity;
    }

    


}
