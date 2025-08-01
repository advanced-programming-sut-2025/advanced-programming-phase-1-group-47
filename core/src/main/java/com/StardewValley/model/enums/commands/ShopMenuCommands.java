package com.StardewValley.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    SHOW_ALL_PRODUCTS("^\\s*show\\s+all\\s+products\\s*$"),
    SHOW_AVAILABLE_PRODUCTS("^\\s*show\\s+all\\s+available\\s+products\\s*$"),
    BUYANIMAL("^\\s*buy\\s+animal\\s+-a(?<animal>.+?)\\s+-n\\s+(?<name>.+?)\\s*$"),
    petAnimal("^\\S*pet\\s+-n\\s+(?<name>.+?)\\s*$"),
    PURCHASE_PRODUCT("^\\s*purchase\\s+(?<product>.+?)(?:\\s+-n\\s+(?<count>\\d+))?$"),

    BACK("\\s*back\\s*")
    ;

    private final Pattern pattern;

    ShopMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(String.valueOf(this.pattern)).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
