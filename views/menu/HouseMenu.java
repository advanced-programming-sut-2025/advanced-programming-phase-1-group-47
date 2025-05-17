package views.menu;

import controllers.HouseMenuController;
import java.util.Scanner;
import java.util.regex.Matcher;
import models.App;
import models.enums.Menu;
import models.enums.commands.GameMenu;

public class HouseMenu extends AppMenu {
    HouseMenuController controller = new HouseMenuController();
    @Override
    public void check(Scanner scanner) {
        super.check(scanner);
        System.out.println("You are now in Cottage menu");
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = models.enums.commands.GameMenu.cookingrefrigeratorput.getMatcher(input)) != null) {
            System.out.println(controller.putToFridge(matcher.group("item")).getData());
        }
        else if ((matcher = models.enums.commands.GameMenu.cookingrefrigeratorpick.getMatcher(input)) != null) {
            System.out.println(controller.pickFromFridge(matcher.group("item")).getData());
        }
        else if ((matcher = models.enums.commands.GameMenu.cookingreciepe.getMatcher(input)) != null) {
            System.out.println(controller.showRecipes().getData());
        }
        else if ((matcher = models.enums.commands.GameMenu.cookingprepare.getMatcher(input)) != null) {
            System.out.println(controller.makeRecipe(matcher.group("recipeName")).getData());
        }
        else if ((matcher = GameMenu.Back.getMatcher(input)) != null) {
            App.currentMenu = Menu.GameMenu;
        }
        else
            System.out.println("Invalid Command");
    }

}