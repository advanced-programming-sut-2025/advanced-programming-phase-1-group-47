package views.menu;


import controllers.Barn;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BarnMenu extends AppMenu {
        Barn controller = new Barn();
    @Override
    public void check(Scanner scanner) {
        System.out.println("You are now in Cottage menu");
        String input = scanner.nextLine();
        Matcher matcher;


    }
}
