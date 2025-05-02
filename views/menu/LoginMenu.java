package views.menu;

import controllers.LoginMenuController;
import models.enums.commands.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends AppMenu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void check (Scanner scanner) {

        String input = scanner.nextLine();
        Matcher matcher;

        if((matcher = LoginMenuCommands.SignUp.getMatcher(input)) != null) {
            controller.SignUp(matcher.group("username"), matcher.group("password"), matcher.group("password_confirm"), matcher.group("nickname"), matcher.group("email"), matcher.group("gender"), scanner);
        } else if ((matcher = LoginMenuCommands.LogIn.getMatcher(input)) != null) {
            controller.LogIn(matcher.group("username"), matcher.group("password"), scanner);
        } else if ((matcher = LoginMenuCommands.LogInStayLoggedIn.getMatcher(input)) != null) {
            controller.LogInStayLoggedIn(matcher.group("username"), matcher.group("password"));
        } else if ((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null) {
            controller.ForgetPassword(matcher.group("username"), scanner);
        } else if ((matcher = LoginMenuCommands.ShowMenu.getMatcher(input)) != null) {
            controller.ShowCurrentMenu();
        } else {
            System.out.println("invalid command\n");
        }


    }
}
