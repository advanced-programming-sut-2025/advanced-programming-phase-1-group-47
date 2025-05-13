package models.enums;

import java.util.List;

public enum AnimalType {
    COW(
            "cow", 1500,
            List.of(AnimalProductType.BIG_MILK, AnimalProductType.MILK),
            1, true
    ),
    DINOSAUR(
            "dinosaur", 1400,
            List.of(AnimalProductType.DINOSAUR_EGG),
            7, false
    ),
    DUCK(
            "duck", 1200,
            List.of(AnimalProductType.DUCK_EGG, AnimalProductType.DUCK_FEATHER),
            2, false
    ),
    GOAT(
            "goat", 4000,
            List.of(AnimalProductType.BIG_GOAT_MILK, AnimalProductType.GOAT_MILK),
            2, true
    ),
    HEN(
            "hen", 800,
            List.of(AnimalProductType.HEN_EGG, AnimalProductType.HEN_BIG_EGG),
            1, false
    ),
    PIG(
            "pig", 16000,
            List.of(AnimalProductType.TRUFFLE),
            0, true
    ),
    RABBIT(
            "rabbit", 8000,
            List.of(AnimalProductType.RABBIT_LEG, AnimalProductType.RABBIT_WOOL),
            4, false
    ),
    SHEEP(
            "sheep", 8000,
            List.of(AnimalProductType.SHEEP_WOOL),
            3, true
    );

    private final String name;
    private final int price;
    private final List<AnimalProductType> products;
    private final int productionInterval;
    private final boolean isBarnAnimal;

    AnimalType(String name, int price, List<AnimalProductType> products, int productionInterval, boolean isBarnAnimal) {
        this.name = name;
        this.price = price;
        this.products = products;
        this.productionInterval = productionInterval;
        this.isBarnAnimal = isBarnAnimal;
    }

    public String getName() {
        return name.toLowerCase();
    }

    public int getPrice() {
        return price;
    }

    public int getSellPrice() {
        return price;
    }

    public List<AnimalProductType> getProductList() {
        return products;
    }

    public int getProductPeriod() {
        return productionInterval;
    }

    public boolean isBarnAnimal() {
        return isBarnAnimal;
    }

    public int getEnergy() {
        return 0;  // Placeholder - not used currently
    }

    public int getContainingEnergy() {
        return 0;  // Placeholder - not used currently
    }
}
