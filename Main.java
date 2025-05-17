import models.App;
import models.enums.Menu;
import views.AppView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
    public static void main(String[] args){
//        String filePath = "output.txt";
//        String content = "Saving this to a specific location.";
//        try {
//            FileWriter writer = new FileWriter(filePath);
//            writer.write(content);
//            writer.close();
//            System.out.println("File written to: " + filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        App.setCurrentMenu(Menu.LoginMenu);
        (new AppView()).run();
    }
}