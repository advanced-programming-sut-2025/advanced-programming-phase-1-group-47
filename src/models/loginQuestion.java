package models;
public class loginQuestion {
    private String answer;
    private String question;

    public loginQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    public String get_question() {
        return question;
    }
    public String get_answer() {
        return answer;
    }
    public void set_question(String question){
        this.question = question;
    }
    public void set_answer(String answer){
        this.answer = answer;
    }
}
