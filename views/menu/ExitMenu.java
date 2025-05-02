package views.menu;

import java.util.Scanner;

public class ExitMenu extends AppMenu{
    @Override
    public void check(Scanner scanner) {
        System.exit(0);
    }
}
