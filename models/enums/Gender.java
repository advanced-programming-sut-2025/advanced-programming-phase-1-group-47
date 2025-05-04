package models.enums;

public enum Gender {
    Female("female"),
    Male("male");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String valueOf() {
        return label;
    }

    public static Gender getGenderEnum(String gender) {
        if (gender == null) return null; // Handle null input

        switch (gender.toLowerCase()) {
            case "male":
                return Gender.Male;
            case "female":
                return Gender.Female;
            default:
                throw new IllegalArgumentException("Invalid gender type: " + gender);
        }
    }



}
