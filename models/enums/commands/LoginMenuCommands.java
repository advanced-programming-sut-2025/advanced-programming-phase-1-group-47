package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public enum LoginMenuCommands {

        SignUp("^\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+(?<passwordConfirm>\\S+)\\s+-n\\s+(?<nickname>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*$"),
        Question("^\\s*pick\\s+question\\s+-q\\s+(?<questionNumber>\\S+)\\s+-a\\s+(?<answer>\\S+)\\s+-c\\s+(?<answerConfirm>\\S+)\\s*$"),
        LogIn("^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s*$"),
        LogInStayLoggedIn("^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+-stay-logged-in\\s*$"),
        ForgetPassword("^\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*$"),
        ShowMenu("show current menu"),
        goMenu("menu\\s+enter\\s+(?<menu>\\S+)\\s*$"),
        Answer("^\\s*answer\\s+-a\\s+(?<answer>\\S+)\\s*$");

        private final String command;

        LoginMenuCommands(String command) {
                this.command = command;
        }

        public String getCommand() {
                return command;
        }

        public Matcher getMatcher(String input) {
                Matcher matcher = compile(this.command).matcher(input);
                if (matcher.matches()) {
                        return matcher;
                }
                return null;
        }

}
