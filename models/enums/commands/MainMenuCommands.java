package models.enums.commands;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
        EnterMenu("^\\s*menu\\s+enter\\s+(?<menuName>\\S+)\\s*$"),
        ExitMenu("^\\s*menu\\s+exit\\s*$"),
        ShowMenu("show current menu"),
        LogOut("user\\s*logout");
        private final String command;

        MainMenuCommands(String command) {
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
