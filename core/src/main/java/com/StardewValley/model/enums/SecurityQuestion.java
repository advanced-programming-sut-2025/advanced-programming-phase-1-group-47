package com.StardewValley.model.enums;

public enum SecurityQuestion {
    CHILDHOOD_NICKNAME("What was your childhood nickname?"),
    FIRST_PET("What is the name of your first pet?"),
    FIRST_CAR("What was the make of your first car?"),
    ELEMENTARY_SCHOOL("What elementary school did you attend?"),
    BIRTH_CITY("In what city were you born?");

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public static SecurityQuestion getByIndex(int index) {
        SecurityQuestion[] questions = SecurityQuestion.values();
        if (index < 0 || index >= questions.length) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        return questions[index];
    }

}
