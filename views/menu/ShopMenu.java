package views.menu;

import java.util.Scanner;

import controllers.shopMenuController;
import models.App;
import models.Map;
import models.Point;
import models.Tile;
import models.enums.Menu;
import models.enums.TileType;

public class ShopMenu extends AppMenu{
    shopMenuController controller = new shopMenuController();
    @Override
    public void check(Scanner scanner) {
        super.check(scanner);
        String input = scanner.nextLine();
        if(input.equals("w")) {
            System.out.println(controller.whatIsTileType());
        }
        else if(input.equals("back")) {
            App.currentMenu = Menu.GameMenu;
        }
    }
}
