package views.menu;

import controllers.TradeMenuController;
import models.App;
import models.enums.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu extends AppMenu {
    TradeMenuController controller = new TradeMenuController();
    @Override
    public void check(Scanner scanner) {
        super.check(scanner);
        String input = scanner.nextLine();
        Matcher matcher;
        if (input.equals("back")) {
            App.currentMenu = Menu.GameMenu;
        }
        if ((matcher = models.enums.commands.GameMenu.tradelist.getMatcher(input)) != null) {
//            System.out.println(controller.tr);
        }else if ((matcher = models.enums.commands.GameMenu.traderesponseaccept.getMatcher(input)) != null) {
//            System.out.println(controller.trade());
        } else if ((matcher = models.enums.commands.GameMenu.traderesponsereject.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.tradehistory.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.tradewithmoney.getMatcher(input)) != null) {
            System.out.println(controller.trade(matcher.group("username"), matcher.group("type"), matcher.group("item"), matcher.group("amount"), matcher.group("price")).getData());
        } else if ((matcher = models.enums.commands.GameMenu.tradewithitem.getMatcher(input)) != null) {
            System.out.println(controller.trade(matcher.group("username"), matcher.group("type"), matcher.group("item"), matcher.group("amount"),matcher.group("targetItem"),matcher.group("targetAmount")).getData());
        }
        else
            System.out.println("invalid Command");
    }
}
