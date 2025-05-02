package models.enums;

public enum SecurityQuestion {

    QUESTION1("What was your childhood nickname?"),
    QUESTION2("What is the name of your first pet?"),
    QUESTION3("What was the make of your first car?"),
    QUESTION4("What elementary school did you attend?"),
    QUESTION5("In what city were you born?");

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public static SecurityQuestion fromQuestion(String text) {
        for (SecurityQuestion q : SecurityQuestion.values()) {
            if (q.getQuestion().equalsIgnoreCase(text)) {
                return q;
            }
        }
        throw new IllegalArgumentException("Unknown question: " + text);
    }
}
