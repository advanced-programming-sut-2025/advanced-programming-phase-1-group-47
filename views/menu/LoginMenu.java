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
        System.out.println("Im here");
        String input = scanner.nextLine();
        if (input.equals("exit")) {
            System.out.println("got it");
            System.out.println(new Result<>(false, "Hi").toString());
        }
        else{
            Matcher matcher;
            System.out.println("hi");

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
                System.out.println("invalid command\n");
            }
        }


    }
}
