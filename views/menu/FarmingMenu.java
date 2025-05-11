package views.menu;

import models.App;
import models.enums.Menu;
import models.enums.commands.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class FarmingMenu extends AppMenu{
    @Override
    public void check(Scanner scanner) {
        System.out.println("You are now in Green House menu");
        String input = scanner.nextLine();
        Matcher matcher;
        if (input.equals("back"))
            App.currentMenu = Menu.GameMenu;
    }
}
