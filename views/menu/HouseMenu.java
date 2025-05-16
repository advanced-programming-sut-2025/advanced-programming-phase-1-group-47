package views.menu;

import controllers.HouseMenuController;
import models.App;
import models.enums.Menu;
import models.enums.commands.GameMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HouseMenu extends AppMenu {
    HouseMenuController controller = new HouseMenuController();
    @Override
    public void check(Scanner scanner) {
        super.check(scanner);
        System.out.println("You are now in Cottage menu");
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = models.enums.commands.GameMenu.cookingrefrigeratorput.getMatcher(input)) != null) {
            // handleCookingRefrigeratorPut(matcher);
        }
        else if ((matcher = models.enums.commands.GameMenu.cookingrefrigeratorpick.getMatcher(input)) != null) {
            // handleCookingRefrigeratorPick(matcher);
        }
        else if ((matcher = models.enums.commands.GameMenu.cookingreciepe.getMatcher(input)) != null) {
            // handleCookingRecipe(matcher);
        }
        else if ((matcher = models.enums.commands.GameMenu.cookingprepare.getMatcher(input)) != null) {
            // handleCookingPrepare(matcher);
        }
        else if ((matcher = GameMenu.Back.getMatcher(input)) != null) {
            App.currentMenu = Menu.GameMenu;
        }
        else
            System.out.println("Invalid Command");
    }

    }
}
