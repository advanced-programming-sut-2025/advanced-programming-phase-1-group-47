package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
        signup("^\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+(?<password_confirm>\\S+)\\s+-n\\s+(?<nickname>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*$"),
        question("^\\s*pick\\s+question\\s+-q\\s+(?<question_number>\\S+)\\s+-a\\s+(?<answer>\\S+)\\s+-c\\s+(?<answer_confirm>\\S+)\\s*$"),
        login("^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s$"),
        loginstayloggedin("^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+-stay-logged-in\\s*$"),
        logout("\\s*user\\s*logout\\s*"),
        forgetpassword("^\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*$"),
        answer("^\\s*answer\\s+-a\\s+(?<answer>\\S+)\\s*$");
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
