package models.enums;

import java.util.Scanner;

import views.menu.*;

public enum Menu {
    LoginMenu(new LoginMenu()),
    MainMenu(new MainMenu()),
    ProfileMenu(new ProfileMenu()),
    GameMenu(new GameMenuView()),
    GreenHouseMenu(new FarmingMenu()),
    StoreMenu(new ShopMenu()),
    cottageMenu(new HouseMenu()),
    TraderMenu(new TradeMenu()),
    ExitMenu(new ExitMenu());
    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }
    public void checkCommand(Scanner scanner) {
        menu.check(scanner);
    }
    
}
