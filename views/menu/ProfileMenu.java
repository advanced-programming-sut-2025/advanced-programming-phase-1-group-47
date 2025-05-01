package views.menu;

import controllers.ProfileMenuController;
import models.enums.commands.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends AppMenu {
    private ProfileMenuController controller = new ProfileMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        if((matcher = ProfileMenuCommands.ChangeUsername.getMatcher(input)) != null) {
            controller.ChangeUsername(matcher.group("username"));
        } else if((matcher = ProfileMenuCommands.ChangeNickname.getMatcher(input)) != null) {
            controller.ChangeNickname(matcher.group("nickname"));
        } else if((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null) {
            controller.ChangePassword(matcher.group("new_password"), matcher.group("old_password"));
        } else if((matcher = ProfileMenuCommands.ChangeEmail.getMatcher(input)) != null) {
            controller.ChangeEmail(matcher.group("email"));
        } else if((matcher = ProfileMenuCommands.UserInfo.getMatcher(input)) != null) {
            controller.UserInfo();
        } else {
            System.out.println("invalid command\n");
        }
    }
}
