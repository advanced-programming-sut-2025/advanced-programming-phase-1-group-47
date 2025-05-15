package views.menu;

import controllers.GameMenuController;
import models.*;
import models.enums.TileType;
import models.enums.commands.GameMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuView extends AppMenu {
    private final GameMenuController controller = new GameMenuController();
    @Override
    public void check(Scanner scanner) {
        System.out.println("You are now in Game menu");
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = models.enums.commands.GameMenu.newgame.getMatcher(input)) != null) {
            System.out.println(controller.handleNewGame(matcher,scanner).getData());
        } else if ((matcher = models.enums.commands.GameMenu.loadgame.getMatcher(input)) != null) {
            // handleLoadGame(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.exitgame.getMatcher(input)) != null) {
            // handleExitGame(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.deletecurrentgame.getMatcher(input)) != null) {
            // handleDeleteCurrentGame(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.nextturn.getMatcher(input)) != null) {
            App.currentGame.currentPlayer = App.getCurrentGame().getPlayers().get((App.currentGame.turn + 1)%(App.currentGame.players.size()));
            App.currentGame.turn = (App.currentGame.turn + 1)%(App.currentGame.players.size());
        } else if ((matcher = models.enums.commands.GameMenu.showtime.getMatcher(input)) != null) {
            System.out.println(controller.showTime().getData());
        } else if ((matcher = models.enums.commands.GameMenu.showdate.getMatcher(input)) != null) {
            System.out.println(controller.showDate().getData());
        } else if ((matcher = models.enums.commands.GameMenu.showdatetime.getMatcher(input)) != null) {
            System.out.println(controller.showDate().getData());
        } else if ((matcher = models.enums.commands.GameMenu.showdayofweek.getMatcher(input)) != null) {
            System.out.println(controller.showDayWeek().getData());
        } else if ((matcher = models.enums.commands.GameMenu.cheateadvancetime.getMatcher(input)) != null) {
            System.out.println(controller.cheateAdvanceTime(matcher).getData());
        } else if ((matcher = models.enums.commands.GameMenu.cheatadvencedate.getMatcher(input)) != null) {
            System.out.println(controller.cheateAdvanceDate(matcher).getData());
        } else if ((matcher = models.enums.commands.GameMenu.showseason.getMatcher(input)) != null) {
            System.out.println(controller.showSeason().getData());
        } else if ((matcher = models.enums.commands.GameMenu.cheatthor.getMatcher(input)) != null) {
            // handleCheatThor(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.showweather.getMatcher(input)) != null) {
            System.out.println(controller.showWeather().getData());
        } else if ((matcher = models.enums.commands.GameMenu.weatherforecast.getMatcher(input)) != null) {
            System.out.println(controller.showWeatherForecast().getData());
        } else if ((matcher = models.enums.commands.GameMenu.cheatweatherset.getMatcher(input)) != null) {
            System.out.println(controller.changeWeather(matcher).getData());
        } else if ((matcher = models.enums.commands.GameMenu.greenhousebuild.getMatcher(input)) != null) {
            System.out.println(controller.BuildGreenHouse().getData());
        } else if ((matcher = models.enums.commands.GameMenu.walk.getMatcher(input)) != null) {
            controller.walk(matcher,scanner);
        } else if ((matcher = models.enums.commands.GameMenu.printmap.getMatcher(input)) != null) {
            controller.printMap();
        } else if ((matcher = models.enums.commands.GameMenu.showenergy.getMatcher(input)) != null) {
            System.out.println(controller.showEnergy().getData());
        } else if ((matcher = models.enums.commands.GameMenu.cheatenergyset.getMatcher(input)) != null) {
            System.out.println(controller.setEnergy(matcher).getData());
        } else if ((matcher = models.enums.commands.GameMenu.cheatenergyunlimited.getMatcher(input)) != null) {
            controller.setEnergyCapacity();
        } else if ((matcher = models.enums.commands.GameMenu.showinventory.getMatcher(input)) != null) {
            controller.showInventory();
        } else if ((matcher = models.enums.commands.GameMenu.inventorytrashtotal.getMatcher(input)) != null) {
            System.out.println(controller.InventoryTrashTotal(matcher).getData());
        } else if ((matcher = models.enums.commands.GameMenu.inventorytrash.getMatcher(input)) != null) {
            System.out.println(controller.InventoryTrash(matcher).getData());
        } else if ((matcher = models.enums.commands.GameMenu.equiptool.getMatcher(input)) != null) {
            System.out.println(controller.EquipTool(matcher).getData());
        } else if ((matcher = models.enums.commands.GameMenu.currenttool.getMatcher(input)) != null) {
            System.out.println(controller.showCurrentTool().getData());
        } else if ((matcher = models.enums.commands.GameMenu.availabletool.getMatcher(input)) != null) {
            System.out.println(controller.showAvailableTools().getData());
        } else if ((matcher = models.enums.commands.GameMenu.upgradetool.getMatcher(input)) != null) {
            // handleUpgradeTool(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.tooluse.getMatcher(input)) != null) {
            // handleToolUse(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.craftinfo.getMatcher(input)) != null) {
            // handleCraftInfo(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.plant.getMatcher(input)) != null) {
            System.out.println(controller.plantPlant(matcher.group("seed"), matcher.group("direction")).getData());
        } else if ((matcher = models.enums.commands.GameMenu.showplant.getMatcher(input)) != null) {
        } else if ((matcher = models.enums.commands.GameMenu.fertilize.getMatcher(input)) != null) {
            // handleFertilize(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.water.getMatcher(input)) != null) {
            // handleWater(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.craftingrecipes.getMatcher(input)) != null) {
            // handleCraftingRecipes(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.craftingcraft.getMatcher(input)) != null) {
            // handleCraftingCraft(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.placeitem.getMatcher(input)) != null) {
            // handlePlaceItem(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cheatadditem.getMatcher(input)) != null) {
            System.out.println(controller.cheatItem(matcher).getData());
        } else if ((matcher = models.enums.commands.GameMenu.cookingrefrigeratorput.getMatcher(input)) != null) {
            // handleCookingRefrigeratorPut(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cookingrefrigeratorpick.getMatcher(input)) != null) {
            // handleCookingRefrigeratorPick(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cookingreciepe.getMatcher(input)) != null) {
            // handleCookingRecipe(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cookingprepare.getMatcher(input)) != null) {
            // handleCookingPrepare(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.friendships.getMatcher(input)) != null) {
            System.out.println(controller.showFriendships().getData());
        } else if ((matcher = models.enums.commands.GameMenu.talk.getMatcher(input)) != null) {
            System.out.println(controller.talkToPlayer(matcher.group("username"), matcher.group("message")).getData());
        } else if ((matcher = models.enums.commands.GameMenu.talkhistory.getMatcher(input)) != null) {
            System.out.println(controller.showTalkHistory(matcher.group("username")).getData());
        } else if ((matcher = models.enums.commands.GameMenu.gift.getMatcher(input)) != null) {
            System.out.println(controller.giveGiftToPlayer(matcher.group("username"), matcher.group("item"), matcher.group("amount")).getData());
        } else if ((matcher = models.enums.commands.GameMenu.giftlist.getMatcher(input)) != null) {
            System.out.println(controller.listGifts().getData());
        } else if ((matcher = models.enums.commands.GameMenu.giftrate.getMatcher(input)) != null) {
            System.out.println(controller.rateGift(matcher.group("giftNumber"), matcher.group("rate")).getData());
        } else if ((matcher = models.enums.commands.GameMenu.gifthistory.getMatcher(input)) != null) {
            System.out.println(controller.listGiftHistory(matcher.group("username")).getData());
        } else if ((matcher = models.enums.commands.GameMenu.hug.getMatcher(input)) != null) {
            System.out.println(controller.hugPlayer(matcher.group("username")).getData());
        } else if ((matcher = models.enums.commands.GameMenu.flower.getMatcher(input)) != null) {
            System.out.println(controller.giveFlower(matcher.group("username")));
        } else if ((matcher = models.enums.commands.GameMenu.askmarriage.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.respondmarriageaccept.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.respondmarriagereject.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.starttrade.getMatcher(input)) != null) {
//            System.out.println(controller.);
        } else if ((matcher = models.enums.commands.GameMenu.trade.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.tradelist.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.traderesponseaccept.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.traderesponsereject.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.tradehistory.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        }
        else if ((matcher = models.enums.commands.GameMenu.build.getMatcher(input)) != null) {
            // handleBuild(matcher);
        }else if ((matcher = models.enums.commands.GameMenu.meetNPC.getMatcher(input)) != null) {
            System.out.println(controller.TalkToNPC(matcher.group("npcName")).getData());
        }else if ((matcher = GameMenu.giftNPC.getMatcher(input)) != null) {
            System.out.println(controller.GiveGiftToNPC(matcher.group("npcName"),matcher.group("item")).getData());
        }else if ((matcher = GameMenu.friendshipNPClist.getMatcher(input)) != null) {
            System.out.println(controller.showNpcs().getData());
        }
        else if ((matcher = GameMenu.Guide.getMatcher(input)) != null) {
            System.out.println("=== 🏪 فروشگاه‌ها و مکان‌های عمومی ===");
            printPlace("Blacksmith", "BLACKSMITH", 60, 110, 65, 115, 64, 112);
            printPlace("Marnie's Ranch", "MARNIESRANCH", 70, 90, 75, 95, 74, 92);
            printPlace("Carpenter Shop", "CARPENTER", 80, 30, 85, 35, 84, 32);
            printPlace("Fish Shop", "FISHSHOP", 60, 20, 65, 25, 64, 22);
            printPlace("Joja Mart", "JOJAMART", 120, 60, 125, 65, 124, 62);
            printPlace("Stardrop Saloon", "STARDROPSALOON", 25, 70, 30, 75, 29, 72);
            printPlace("Pierre's Store", "PIERRESSTORE", 15, 50, 20, 55, 19, 52);

            System.out.println("\n=== 🏠 خانه‌های شخصیت‌های NPC ===");
            printNpc("Abigel", "ABIGEL", 52, 56, 58, 62, 58, 59);
            printNpc("Leah", "LEAH", 54, 70, 60, 76, 60, 73);
            printNpc("Robin", "ROBIN", 100, 55, 106, 61, 106, 58);
            printNpc("Sebastian", "SEBASTIAN", 70, 45, 76, 51, 76, 48);
            printNpc("Harvey", "HARVEY", 80, 57, 86, 63, 86, 60);
            int  k = 0 ;
            for(Player i : App.getCurrentGame().getPlayers()) {
                System.out.println(i.getUsername() + " - " + (App.getCurrentGame().map.farms[(k)].personPoint.x + App.farmStart[k].x) + "," + (App.farmStart[k].y + App.getCurrentGame().map.farms[k++].personPoint.y));
            }
        }
        else if ((matcher = GameMenu.showPoint.getMatcher(input)) != null) {
            System.out.println("Player turn :" + App.currentGame.turn);
            System.out.println("Player Point : (" +
                    (App.currentGame.map.farms[(App.currentGame.turn) % 4].personPoint.x +
                            App.farmStart[(App.currentGame.turn) % 4].x) + "," +
                    (App.currentGame.map.farms[(App.currentGame.turn) % 4].personPoint.y +
                            App.farmStart[(App.currentGame.turn) % 4].y) + ")");
            System.out.println(App.currentGame.currentPlayer.getUsername());
        }
        else {
            System.out.println("Invalid command");
        }
        if (input.equals("@")) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            System.out.println(App.currentGame.map.tiles[x][y].type.getSticker());
        }
    }

    private static void printNpc(String name, String type, int x1, int y1, int x2, int y2, int npcX, int npcY) {
        System.out.printf("شخصیت: %-10s | نوع: %-10s | خانه: (%d,%d) تا (%d,%d) | موقعیت NPC: (%d,%d)%n",
                name, type, x1, y1, x2, y2, npcX, npcY);
    }
    private static void printPlace(String name, String type, int x1, int y1, int x2, int y2, int doorX, int doorY) {
        System.out.printf("نام: %-20s | نوع: %-15s | محدوده: (%d,%d) تا (%d,%d) | در: (%d,%d)%n",
                name, type, x1, y1, x2, y2, doorX, doorY);
    }
}
