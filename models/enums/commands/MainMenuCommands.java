package models.enums.commands;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
        entermenu("^\\s*menu\\s+enter\\s+(?<menu_name>\\S+)\\s*$"),
        exitmenu("^\\s*menu\\s+exit\\s*$"),
        showmenu("show current menu"),
        logout("user\\s*logout");
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
