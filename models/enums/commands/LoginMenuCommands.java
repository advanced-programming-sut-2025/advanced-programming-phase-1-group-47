package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
        SignUp("^\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+(?<password_confirm>\\S+)\\s+-n\\s+(?<nickname>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*$"),
        Question("^\\s*pick\\s+question\\s+-q\\s+(?<question_number>\\S+)\\s+-a\\s+(?<answer>\\S+)\\s+-c\\s+(?<answer_confirm>\\S+)\\s*$"),
        LogIn("^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s$"),
        LogInStayLoggedIn("^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+-stay-logged-in\\s*$"),
        ForgetPassword("^\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*$"),
        ShowMenu("show current menu"),
        Answer("^\\s*answer\\s+-a\\s+(?<answer>\\S+)\\s*$");
                private final String command;

                LoginMenuCommands(String command) {
                        this.command = command;
                }

                public String getCommand() {
                        return command;
                }

                public Matcher getMatcher(String input) {
                        Matcher matcher = Pattern.compile(this.command).matcher(input);
                        if (matcher.matches()) {
                                return matcher;
                        }
                        return null;
                }
}
