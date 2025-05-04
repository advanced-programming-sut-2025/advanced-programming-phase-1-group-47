package views.menu;

import controllers.LoginMenuController;
import models.Result;
import models.enums.commands.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends AppMenu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void check (Scanner scanner) {
        System.out.println("You are now in login menu"); // It will be deleted.

        String input = scanner.nextLine();

            Matcher matcher;

            if ((matcher = LoginMenuCommands.SignUp.getMatcher(input)) != null) {
                System.out.println(controller.SignUp(matcher.group("username"), matcher.group("password"), matcher.group("passwordConfirm"), matcher.group("nickname"), matcher.group("email"), matcher.group("gender"), scanner).toString());
            } else if ((matcher = LoginMenuCommands.LogIn.getMatcher(input)) != null) {
                System.out.println(controller.LogIn(matcher.group("username"), matcher.group("password"), scanner).toString());
            } else if ((matcher = LoginMenuCommands.LogInStayLoggedIn.getMatcher(input)) != null) {
                System.out.println(controller.LogInStayLoggedIn(matcher.group("username"), matcher.group("password")).toString());
            } else if ((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null) {
                System.out.println(controller.ForgetPassword(matcher.group("username"), scanner).toString());
            } else if ((matcher = LoginMenuCommands.ShowMenu.getMatcher(input)) != null) {
                System.out.println(controller.ShowCurrentMenu().toString());
            } else {
                System.out.println("invalid command");
            }



    }
}
