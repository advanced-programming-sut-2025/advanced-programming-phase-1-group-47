package views;


import java.util.Scanner;
import models.App;

public class AppView {
    public static Scanner scanner = new Scanner(System.in);
    public static void runProgram() {
        String input = scanner.nextLine();
        App app = new App();
        app.getMenu().checkCommand(input);
    }
}
