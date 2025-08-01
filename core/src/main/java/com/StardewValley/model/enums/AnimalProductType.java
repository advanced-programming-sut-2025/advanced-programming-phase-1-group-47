package com.StardewValley.model.enums;


public enum AnimalProductType {
    HEN_EGG("hen egg",50, 901),
    HEN_BIG_EGG("big hen egg",95,902),
    DUCK_EGG("duck egg",95,903),
    DUCK_FEATHER("duck feather",250,904),
    RABBIT_WOOL("rabbit wool",340,904),
    RABBIT_LEG("rabbit leg",565,905),
    DINOSAUR_EGG("dinosaur egg",350,906),
    MILK("milk",125, 907),
    BIG_MILK("big milk",190, 908),
    GOAT_MILK("goat milk",225, 909),
    BIG_GOAT_MILK("big goat milk",345, 910),
    SHEEP_WOOL("sheep wool",340,911),
    TRUFFLE("truffle",625,912)
    ;

    private final String name;
    private final Integer price;
    private final Integer id;
    AnimalProductType(String name,Integer price,Integer id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public int getSellPrice() {
        return price;
    }

    public int getEnergy() {
        return 0;
    }

    public String getName() {
        return this.name.toLowerCase();
    }

    public Integer getContainingEnergy() {return getEnergy();}
}
