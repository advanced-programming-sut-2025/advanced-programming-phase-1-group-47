package models.enums;

public enum Gender {
    Female("female"),
    Male("male");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


}
