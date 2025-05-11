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
//        if (App.currentGame != null) {
//
//        }
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
            App.currentGame.turn = (App.currentGame.turn + 1) % 4;
        } else if ((matcher = models.enums.commands.GameMenu.showtime.getMatcher(input)) != null) {
            System.out.println(controller.showTime().getData());
        } else if ((matcher = models.enums.commands.GameMenu.showdate.getMatcher(input)) != null) {
            System.out.println(controller.showDate().getData());
        } else if ((matcher = models.enums.commands.GameMenu.showdatetime.getMatcher(input)) != null) {
            System.out.println(controller.showDatetime().getData());
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
            // handleShowWeather(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.weatherforecast.getMatcher(input)) != null) {
            // handleWeatherForecast(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cheatweatherset.getMatcher(input)) != null) {
            // handleCheatWeatherSet(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.greenhousebuild.getMatcher(input)) != null) {
            // handleGreenhouseBuild(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.walk.getMatcher(input)) != null) {
             controller.walk(matcher,scanner);
        } else if ((matcher = models.enums.commands.GameMenu.printmap.getMatcher(input)) != null) {
            controller.printMap();
        } else if ((matcher = models.enums.commands.GameMenu.mapreadinghelper.getMatcher(input)) != null) {
            // handleMapReadingHelper(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.showenergy.getMatcher(input)) != null) {
            // handleShowEnergy(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cheatenergyset.getMatcher(input)) != null) {
            // handleCheatEnergySet(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cheatenergyunlimited.getMatcher(input)) != null) {
            // handleCheatEnergyUnlimited(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.showinventory.getMatcher(input)) != null) {
            // handleShowInventory(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.inventorytrashtotal.getMatcher(input)) != null) {
            // handleInventoryTrashTotal(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.inventorytrash.getMatcher(input)) != null) {
            // handleInventoryTrash(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.equiptool.getMatcher(input)) != null) {
            // handleEquipTool(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.currenttool.getMatcher(input)) != null) {
            // handleCurrentTool(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.availabletool.getMatcher(input)) != null) {
            // handleAvailableTool(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.upgradetool.getMatcher(input)) != null) {
            // handleUpgradeTool(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.tooluse.getMatcher(input)) != null) {
            // handleToolUse(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.craftinfo.getMatcher(input)) != null) {
            // handleCraftInfo(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.plant.getMatcher(input)) != null) {
            // handlePlant(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.showplant.getMatcher(input)) != null) {
            // handleShowPlant(matcher);
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
            // handleCheatAddItem(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cookingrefrigeratorput.getMatcher(input)) != null) {
            // handleCookingRefrigeratorPut(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cookingrefrigeratorpick.getMatcher(input)) != null) {
            // handleCookingRefrigeratorPick(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cookingreciepe.getMatcher(input)) != null) {
            // handleCookingRecipe(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.cookingprepare.getMatcher(input)) != null) {
            // handleCookingPrepare(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.eatfood.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.build.getMatcher(input)) != null) {
            // handleBuild(matcher);
        } else {
            System.out.println("Invalid command");
        }
    }
}
