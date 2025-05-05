package views.menu;

import controllers.MainMenuController;
import models.enums.commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends AppMenu {
    final MainMenuController controller = new MainMenuController();

    @Override
    public void check(Scanner scanner) {
        System.out.println("You are now in main menu"); // It will be deleted.

        String input = scanner.nextLine();
        Matcher matcher;

        if((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            controller.EnterMenu(matcher.group("menuName"));
        } else if ((matcher = MainMenuCommands.ExitMenu.getMatcher(input)) != null) {
            controller.ExitMenu();
        } else if ((matcher = MainMenuCommands.ShowMenu.getMatcher(input)) != null) {
            controller.ShowCurrentMenu();
        } else if ((matcher = MainMenuCommands.LogOut.getMatcher(input)) != null) {
            controller.LogOut();
        } else {
            System.out.println("invalid command\n");
        }


    }
}