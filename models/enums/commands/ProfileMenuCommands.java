package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    ChangeUsername("^\\s*change\\s+username\\s+-u\\s+(?<username>\\S+)\\s*$"),
    ChangeNickname("^\\s*change\\s+nickname\\s+-u\\s+(?<nickname>\\S+)\\s*$"),
    ChangePassword("^\\s*change\\s+password\\s+-p\\s+(?<new_password>\\S+)\\s+-o\\s+(?<old_password>\\S+)\\s*$"),
    ChangeEmail("^\\s*change\\s+email\\s+-u\\s+(?<email>\\S+)\\s*$"),
    UserInfo("\\s*user\\s*info\\s*");

    private final String command;

    ProfileMenuCommands(String command) {
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
