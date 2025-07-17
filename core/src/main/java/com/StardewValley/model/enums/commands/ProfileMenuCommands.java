package com.StardewValley.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {

    ChangeUsername("^\\s*change\\s+username\\s+-u\\s+(?<username>\\S+)\\s*$"),

    ChangeNickname("^\\s*change\\s+nickname\\s+-n\\s+(?<nickname>\\S+)\\s*$"),

    ChangePassword("^\\s*change\\s+password\\s+-o\\s+(?<oldPassword>\\S+)\\s+-p\\s+(?<newPassword>\\S+)\\s*$"),

    ChangeEmail("^\\s*change\\s+email\\s+-e\\s+(?<email>\\S+)\\s*$"),

    ShowMenu("show current menu"),

    EnterMenu("^\\s*menu\\s+enter\\s+(?<menuName>\\S+)\\s*$"),

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
