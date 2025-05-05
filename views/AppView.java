package views;


import java.util.Scanner;
import models.App;
import models.enums.Menu;

public class AppView {
    public static Scanner scanner = new Scanner(System.in);
    public void run() {
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }

}
