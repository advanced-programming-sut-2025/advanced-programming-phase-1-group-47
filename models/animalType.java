package models;

public enum animalType {
    wild("");

    String name;
    animalType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
