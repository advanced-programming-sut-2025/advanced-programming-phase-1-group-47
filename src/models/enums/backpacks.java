package models.enums;

public enum backpacks {
    Normal(12),
    Big(24),
    Deluxe(2147483647);
    private final int capacity;

    private backpacks(int capacity) {
        this.capacity = capacity;
    }

    


}
