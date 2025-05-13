package views.menu;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

import controllers.shopMenuController;
import models.*;
import models.Shops.*;
import models.enums.Menu;
import models.enums.TileType;
import models.enums.commands.ShopMenuCommands;

public class ShopMenu extends AppMenu{
    shopMenuController controller = new shopMenuController();
    String StoreName;
    Shop store = new Shop();
    public void ShopMenu(){
        StoreName = controller.whatIsTileType().toString();
        String StoreName = controller.whatIsTileType().toString();
        if (StoreName.equalsIgnoreCase("BLACKSMITH")){
            store = App.currentGame.BlacksmithStore;
        } else if (StoreName.equalsIgnoreCase("PIERRESSTORE")){
//            store = App.currentGame.PierreStore;
        } else if (StoreName.equalsIgnoreCase("JOJAMART")){
            store = new JojaMart().jojaBuilder();
        } else if (StoreName.equalsIgnoreCase("CARPENTER")){
            store = App.currentGame.CarpenterStore;
        } else if (StoreName.equalsIgnoreCase("FISHSHOP")){
            store = App.currentGame.FishShopStore;
        } else if (StoreName.equalsIgnoreCase("MARNIESRANCH")){
            store = App.currentGame.MarniesRanchStore;
        } else if (StoreName.equalsIgnoreCase("STARDROPSALOON")){
            store = App.currentGame.TheSaloonStore;
        }
    }
    @Override
    public void check(Scanner scanner) {
        super.check(scanner);
        String input = scanner.nextLine();
        ShopMenu();
        Matcher matcher;
        if ((matcher = ShopMenuCommands.BACK.getMatcher(input)) != null){
            App.currentMenu = Menu.GameMenu;
        }
        else if ((matcher = ShopMenuCommands.SHOW_ALL_PRODUCTS.getMatcher(input)) != null){
            System.out.println(controller.showAllProducts(store).getData());
        }
        else if ((matcher = ShopMenuCommands.SHOW_AVAILABLE_PRODUCTS.getMatcher(input)) != null){
            System.out.println(controller.showAvailableProducts(store).getData());
        }
        else if ((matcher = ShopMenuCommands.PURCHASE_PRODUCT.getMatcher(input)) != null){
            System.out.println(controller.buy(store, matcher).getData());
        }

    }
}
