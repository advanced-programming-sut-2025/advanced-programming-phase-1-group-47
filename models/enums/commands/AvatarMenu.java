package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AvatarMenu {
    changeusername("^\\s*change\\s+username\\s+-u\\s+(?<username>\\S+)\\s*$"),
    changenickname("^\\s*change\\s+nickname\\s+-u\\s+(?<nickname>\\S+)\\s*$"),
    changepassword("^\\s*change\\s+password\\s+-p\\s+(?<new_password>\\S+)\\s+-o\\s+(?<old_password>\\S+)\\s*$"),
    changeemail("^\\s*change\\s+email\\s+-u\\s+(?<email>\\S+)\\s*$"),
    userinfo("\\s*user\\s*info\\s*");
    private final String command;

    AvatarMenu(String command) {
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
