package com.StardewValley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.StardewValley.model.NPCs.*;
import com.StardewValley.model.Shops.*;
import com.StardewValley.model.enums.Gender;
import com.StardewValley.model.enums.Weather;
import com.StardewValley.model.things.machines.Machine;
import com.badlogic.gdx.Gdx;

public class Game {
    public ArrayList<Player> players;
    public Map map;
    public Time time;

    public Weather weather = Weather.RAINY;
    public Weather tomarrowsWeather = Weather.STORMY;
    public ArrayList<NPC> npcs;
    public Player currentPlayer;
    private static final Random RAND = new Random();
    public Point personPoint;
    public Shop BlacksmithStore = new Blacksmith().blacksmithBulider();
    public Shop JojaMartStore = new JojaMart().jojaBuilder();
    public Shop CarpenterStore = new Carpenter().carpenterBuilder();
    public Shop FishShopStore = new FishShop().fishShopBulider();
    public Shop MarniesRanchStore = new MarniesRanch().MarnieRanchBuilder();
    public Shop TheSaloonStore = new TheSaloon().theSaloonBuilder();
    public Shop pierresStore = new pierres().pierresBuilder();
    public ArrayList<Shop> Shops = new ArrayList<>();
    public int turn = 0;
    private ArrayList<Plant> plants;
    private ArrayList<Machine> machines;

    public Game() {
        this.players = new ArrayList<>();
        User user1 = new User("ali", "123", "ali@gmail.com", "AliKing", Gender.Male, "pet", "cat");
        Player player1 = new Player(
                user1.getUsername(), user1.getPassword(), user1.getEmail(),
                user1.getNickname(), user1.getGender(),
                user1.getSecurityQuestion(), user1.getSecurityAnswer()
        );
        players.add(player1);
        User user2 = new User("sara", "321", "sara@gmail.com", "SaraQueen", Gender.Female, "city", "tehran");
        Player player2 = new Player(
                user2.getUsername(), user2.getPassword(), user2.getEmail(),
                user2.getNickname(), user2.getGender(),
                user2.getSecurityQuestion(), user2.getSecurityAnswer()
        );
        players.add(player2);
        App.loggedInUser = new User("parsa", "Parsasrsr", "srsr@gmail.com", "xx", Gender.Male, "d", "t");
        User u3 = App.loggedInUser;
        Player lastPlayer = new Player(
                u3.getUsername(), u3.getPassword(), u3.getEmail(),
                u3.getNickname(), u3.getGender(),
                u3.getSecurityQuestion(), u3.getSecurityAnswer()
        );
        players.add(lastPlayer);
        this.currentPlayer = lastPlayer;
        this.time = new Time();
        this.plants = new ArrayList<>();
        this.machines = new ArrayList<>();
    }
    public void loadShops(){
        Shops.add(BlacksmithStore);
        Shops.add(JojaMartStore);
        Shops.add(CarpenterStore);
        Shops.add(FishShopStore);
        Shops.add(MarniesRanchStore);
        Shops.add(TheSaloonStore);
        Shops.add(pierresStore);
    }
    public void nextDayWeather() {
        this.weather = tomarrowsWeather;
        this.tomarrowsWeather = setWeather();
    }
    public ArrayList<Shop> getShops() {
        return Shops;
    }
    public Weather setWeather() {
        int a = RAND.nextInt(1, 12);
        String season = time.getSeason().toString();

        switch (season) {
            case "SUMMER":
                if (a % 3 == 0) return Weather.RAINY;
                else if (a % 4 == 0) return Weather.STORMY;
                else return Weather.SUNNY;

            case "FALL":
                if (a % 3 == 0) return Weather.STORMY;
                else if (a % 4 == 0) return Weather.RAINY;
                else return Weather.SUNNY;

            case "WINTER":
                if (a % 3 == 0) return Weather.STORMY;
                else if (a % 4 == 0) return Weather.RAINY;
                else if (a % 5 == 0) return Weather.SUNNY;
                else return Weather.SNOWY;

            case "SPRING":
                if (a % 3 == 0) return Weather.STORMY;
                else if (a % 4 == 0) return Weather.SUNNY;
                else return Weather.RAINY;

            default:
                return Weather.SUNNY;
        }
    }

    public void setNpc() {
        this.npcs = new ArrayList<>();
        npcs.add(Abigail.getInstance().abigailBuilder());
        npcs.add(Sebastion.getInstance().sebastionBuilder());
        npcs.add(Harvey.getInstance().harveyBuilder());
        npcs.add(Leah.getInstance().leahBuilder());
        npcs.add(Robin.getInstance().robinBuilder());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlantInPlants(Plant plant) {
        plants.add(plant);
    }
    public void addMachineInMachines(Machine machine) {
        machines.add(machine);
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public Weather getWeather() {
        return weather;
    }

    public Time getTime() {
        return time;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setShops(ArrayList<Shop> shops) {
        this.Shops = shops;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }
}
