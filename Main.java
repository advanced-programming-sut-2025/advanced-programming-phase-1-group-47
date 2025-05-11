import models.App;
import models.Time;
import models.enums.Menu;
import views.AppView;
public class Main {
    public static void main(String[] args){
        App.setCurrentMenu(Menu.LoginMenu);
        (new AppView()).run();
    }
}