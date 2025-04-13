package models.enums;

import views.menu.AppMenu;
import views.menu.AvatarMenu;
import views.menu.GameMenu;
import views.menu.LoginMenu;
import views.menu.MainMenu;
import views.menu.ProfileMenu;
import views.menu.RegisterMenu;

public enum Menu {
    LoginMenu(new LoginMenu()),
    RegisterMenu(new RegisterMenu()),
    MainMenu(new MainMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    GameMenu(new GameMenu())
    ;

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }
    public void checkCommand(String input) {
        menu.check(input);
    }
    
}
