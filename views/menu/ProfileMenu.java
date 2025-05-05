package views.menu;

import controllers.ProfileMenuController;
import models.enums.commands.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends AppMenu {
    private ProfileMenuController controller = new ProfileMenuController();

    @Override
    public void check(Scanner scanner) {
        System.out.println("You are now in profile menu"); // It will be deleted.

        String input = scanner.nextLine();
        Matcher matcher;

        if((matcher = ProfileMenuCommands.ChangeUsername.getMatcher(input)) != null) {
            System.out.println(controller.ChangeUsername(matcher.group("username")).toString());
        } else if((matcher = ProfileMenuCommands.ChangeNickname.getMatcher(input)) != null) {
            System.out.println(controller.ChangeNickname(matcher.group("nickname")).toString());
        } else if((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null) {
            System.out.println(controller.ChangePassword(matcher.group("oldPassword"), matcher.group("newPassword")).toString());
        } else if((matcher = ProfileMenuCommands.ChangeEmail.getMatcher(input)) != null) {
            System.out.println(controller.ChangeEmail(matcher.group("email")).toString());
        } else if((matcher = ProfileMenuCommands.UserInfo.getMatcher(input)) != null) {
            System.out.println(controller.UserInfo().toString());
        } else if((matcher = ProfileMenuCommands.ShowMenu.getMatcher(input)) != null) {
            System.out.println(controller.ShowCurrentMenu().toString());
        } else if((matcher = ProfileMenuCommands.EnterMenu.getMatcher(input)) != null) {
            controller.EnterMenu(matcher.group("menuName"));
        } else {
            System.out.println("invalid command");
        }
    }
}
