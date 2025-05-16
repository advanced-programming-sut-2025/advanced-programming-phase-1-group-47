package views.menu;

import controllers.TradeMenuController;
import java.util.Scanner;
import java.util.regex.Matcher;
import models.App;

public class TradeMenu extends AppMenu {
    TradeMenuController controller = new TradeMenuController();
    @Override
    public void check(Scanner scanner) {
        super.check(scanner);
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = models.enums.commands.GameMenu.tradelist.getMatcher(input)) != null) {
            System.out.println(controller.tradeList());
        } else if ((matcher = models.enums.commands.GameMenu.traderesponseaccept.getMatcher(input)) != null) {
            System.out.println(controller.tradeResponse("accept", matcher.group("id")));
        } else if ((matcher = models.enums.commands.GameMenu.traderesponsereject.getMatcher(input)) != null) {
            System.out.println(controller.tradeResponse("reject", matcher.group("id")));
        } else if ((matcher = models.enums.commands.GameMenu.tradehistory.getMatcher(input)) != null) {
            // handleEatFood(matcher);
        } else if ((matcher = models.enums.commands.GameMenu.tradewithmoney.getMatcher(input)) != null) {
            System.out.println(controller.trade(matcher.group("username"), matcher.group("type"), matcher.group("item"), matcher.group("amount"), matcher.group("price")));
        } else if ((matcher = models.enums.commands.GameMenu.tradewithitem.getMatcher(input)) != null) {
            System.out.println(controller.trade(matcher.group("username"), matcher.group("type"), matcher.group("item"), matcher.group("amount"),matcher.group("targetItem"),matcher.group("targetAmount")));
        }else if ((matcher = models.enums.commands.GameMenu.Back.getMatcher(input)) != null) {
            App.setCurrentMenu(models.enums.Menu.GameMenu);
        } else
            System.out.println("invalid command!");
    }
}