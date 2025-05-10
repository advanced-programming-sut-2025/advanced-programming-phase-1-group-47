package models.enums.commands;

import java.util.regex.Matcher;
import static java.util.regex.Pattern.compile;

public enum GameMenu {
    newgame("^\\s*game\\s+new\\s+-u\\s+(?<player1>\\S+)(\\s+(?<player2>\\S+))?(\\s+(?<player3>\\S+))?\\s*$"),
    gamemap("^\\s*game\\s+map\\s+(?<mapNumber>\\S+)\\s*$"),
    loadgame("^\\s*load\\s+game\\s*$"),
    exitgame("^\\s*exit\\s+game\\s*$"),
    deletecurrentgame("^\\s*terminate\\s+game\\s*$"),
    nextturn("^\\s*next\\s+turn\\s*$"),
    showtime("^\\s*time\\s*$"),
    showdate("^\\s*date\\s*$"),
    showdatetime("^\\s*datetime\\s*$"),
    showdayofweek("^\\s*day\\s+of\\s+the\\s+week\\s*$"),
    cheateadvancetime("^\\s*cheat\\s+advance\\s+time\\s+(?<time>\\d+)h\\s*$"),
    cheatadvencedate("^\\s*cheat\\s+advance\\s+day\\s+(?<day>\\d+)d\\s*$"),
    showseason("^\\s*season\\s*$"),
    cheatthor("^\\s*cheat\\s*Thor\\s*-l\\s*(?<x>\\d+),(?<y>\\d+)\\s*$"),
    showweather("^\\s*weather\\s*$"),
    weatherforecast("^\\s*weather\\s+forecast\\s*$"),
    cheatweatherset("^\\s*cheat\\s+weather\\s+set\\s+(?<Type>\\S+)\\s*$"),
    greenhousebuild("^\\s*greenhouse\\s+build\\s*$"),
    walk("^\\s*walk\\s+-l\\s*(?<x>\\d+),(?<y>\\d+)\\s*$"),
    printmap("^\\s*print\\s*map\\s+-l\\s*(?<x>\\d+),(?<y>\\d+)\\s+-s\\s*(?<size>\\d+)\\s*$"),
    mapreadinghelper("^\\s*help\\s+reading\\s+map\\s*$"),
    showenergy("^\\s*energy\\s+show\\s*$"),
    cheatenergyset("^\\s*energy\\s+set\\s+-v\\s+(?<value>\\d+)\\s*$"),
    cheatenergyunlimited("^\\s*energy\\s+unlimited\\s*$"),
    showinventory("^\\s*inventory\\s+show\\s*$"),
    inventorytrashtotal("^\\s*inventory\\s+trash\\s+-i\\s+(?<itemName>\\S+)\\s+-n\\s+(?<number>\\d+)\\s*$"),
    inventorytrash("^\\s*inventory\\s+trash\\s+-i\\s+(?<itemName>\\S+)\\s*$"),
    equiptool("^\\s*tools\\s+equip\\s+(?<toolName>\\S+)\\s*$"),
    currenttool("^\\s*tools\\s+show\\s+current\\s*$"),
    availabletool("^\\s*tools\\s+show\\s+available\\s*$"),
    upgradetool("^\\s*tools\\s+upgrade\\s+(?<toolName>\\S+)\\s*$"),
    tooluse("^\\s*tools\\s+use\\s+-d\\s+(?<direction>\\S+)\\s*$"),
    craftinfo("^\\s*craftinfo\\s+-n\\s+(?<craftName>\\S+)\\s*$"),
    plant("^\\s*plant\\s+-s\\s+(?<seed>\\S+)\\s+-d\\s+(?<direction>\\S+)\\s*$"),
    showplant("^\\s*showplant\\s+-l\\s+(?<x>\\d+),(?<y>\\d+)\\s*$"),
    fertilize("^\\s*fertilize\\s+-f\\s+(?<fertilizer>\\S+)\\s+-d\\s+(?<direction>\\S+)\\s*$"),
    water("^\\s*howmuch\\s+water\\s*$"),
    craftingrecipes("^\\s*crafting\\s+show\\s+recipes\\s*$"),
    craftingcraft("^\\s*crafting\\s+craft\\s+(?<itemName>\\S+)\\s*$"),
    placeitem("^\\s*place\\s+item\\s+-n\\s+(?<itemName>\\S+)\\s+-d\\s+(?<direction>\\S+)\\s*$"),
    cheatadditem("^\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*$"),
    cookingrefrigeratorput("^\\s*cooking\\s+refrigerator\\s+put\\s+(?<item>\\S+)\\s*$"),
    cookingrefrigeratorpick("^\\s*cooking\\s+refrigerator\\s+pick\\s+(?<item>\\S+)\\s*$"),
    cookingreciepe("^\\s*cooking\\s+show\\s+recipes\\s*$"),
    cookingprepare("^\\s*cooking\\s+prepare\\s+(?<recipeName>\\S+)\\s*$"),
    eatfood("^\\s*eat\\s+(?<foodName>\\S+)\\s*$"),
    build("^\\s*build\\s+-a\\s+(?<buildingNme>\\S+)\\s+-l\\s+(?<x>\\d+),(?<y>\\d+)\\s*$"),
    buyanimal("^\\s*buy\\s+animal\\s+-a\\s+(?<animal>\\S+)\\s+-n\\s+(?<name>\\S+)\\s*$"),
    pet("^\\s*pet\\s+-n\\s+(?<name>\\S+)\\s*$"),
    cheatsetanimalfriendship("^\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<animalName>\\S+)\\s+-c\\s+(?<amount>\\d+)\\s*$"),
    showanimals("^\\s*animals\\s*$"),
    shepherdanimals("^\\s*shepherd\\s+animals\\s+-n\\s+(?<animalName>\\S+)\\s+-l\\s+(?<x>\\d+),(?<y>\\d+)\\s*$"),
    feedhay("^\\s*feed\\s+hay\\s+-n\\s+(?<animalName>\\S+)\\s*$"),
    produces("^\\s*produces\\s*$"),
    collectproduce("^\\s*collect\\s+produce\\s+-n\\s+(?<name>\\S+)\\s*$"),
    sellanimal("^\\s*sell\\s+animal\\s+-n\\s+(?<name>\\S+)\\s*$"),
    fishing("^\\s*fishing\\s+-p\\s+(?<fishingPole>\\S+)\\s*$"),
    artisanuse("^\\s*artisan\\s+use\\s+(?<artisanName>\\S+)\\s+(?<itemOneName>\\S+)\\s*$"),
    artisanget("^\\s*artisan\\s+get\\s+(?<artisanName>\\S+)\\s*$"),
    showallproducts("^\\s*show\\s+all\\s+products\\s*$"),
    showavailableproducts("^\\s*show\\s+all\\s+available\\s+products\\s*$"),
    purchaseproduct("^\\s*purchase\\s+(?<productName>\\S+)\\s+-n\\s+(?<count>\\d+)\\s*$"),
    purchaseoneproduct("^\\s*purchase\\s+(?<productName>\\S+)\\s*$"),
    cheatadddollars("^\\s*cheat\\s+add\\s+(?<count>\\d+)\\s+dollars\\s*$"),
    sellproduct("^\\s*sell\\s+(?<productName>\\S+)\\s+-n\\s+(?<count>\\d+)\\s*$"),
    friendships("^\\s*friendships\\s*$"),
    talk("^\\s*talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>\\S+)\\s*$"),
    talkhistory("^\\s*talk\\s+history\\s+-u\\s+(?<username>\\S+)\\s*$"),
    gift("^\\s*gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*$"),
    giftlist("^\\s*gift\\s+list\\s*$"),
    giftrate("^\\s*gift\\s+rate\\s+-i\\s+(?<giftNumber>\\d+)\\s+-r\\s+(?<rate>\\d+)\\s*$"),
    gifthistory("^\\s*gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*$"),
    hug("^\\s*hug\\s+-u\\s+(?<username>\\S+)\\s*$"),
    flower("^\\s*flower\\s+-u\\s+(?<username>\\S+)\\s*$"),
    askmarriage("^\\s*ask\\s+marriage\\s+-u\\s+(?<username>\\S+)\\s+-r\\s+(?<ring>\\S+)\\s*$"),
    respondmarriageaccept("^\\s*respond\\s+-accept\\s+-u\\s+(?<username>\\S+)\\s*$"),
    respondmarriagereject("^\\s*respond\\s+-reject\\s+-u\\s+(?<username>\\S+)\\s*$"),
    starttrade("^\\s*start\\s+trade\\s*$"),
    trade("^\\s*trade\\s*$"),
    tradelist("^\\s*trade\\s+list\\s*$"),
    traderesponseaccept("^\\s*trade\\s+response\\s+-accept\\s+-i\\s+(?<id>\\d+)\\s*$"),
    traderesponsereject("^\\s*trade\\s+response\\s+-reject\\s+-i\\s+(?<id>\\d+)\\s*$"),
    tradehistory("^\\s*trade\\s+history\\s*$"),
    meetNPC("^\\s*meet\\s+NPC\\s+(?<npcName>\\S+)\\s*$"),
    giftNPC("^\\s*gift\\s+NPC\\s+(?<npcName>\\S+)\\s+-i\\s+(?<item>\\S+)\\s*$"),
    friendshipNPClist("^\\s*friendship\\s+NPC\\s+list\\s*$"),
    questslist("^\\s*quests\\s+list\\s*$"),
    questsfinish("^\\s*quests\\s+finish\\s+-i\\s+(?<index>\\d+)\\s*$");

    private final String command;

    GameMenu(String command) {
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
