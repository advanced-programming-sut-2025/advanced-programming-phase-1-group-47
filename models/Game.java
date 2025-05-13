package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import models.NPCs.*;
import models.Shops.*;
import models.enums.Weather;

public class Game {
    private final ArrayList<Player> players;
    public Map map;
    public Time time = new Time();
    public Weather weather;
    public Weather tomarrowsWeather;
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

    public int turn = 0;
    private ArrayList<Shop> shops;
    private ArrayList<Plant> plants;
    public Game(Player... players) {
        this.players = new ArrayList<>(Arrays.asList(players));
        User u3 = App.getLoggedInUser();
        Player p4 = new Player(u3.getUsername(), u3.getPassword(), u3.getEmail(), u3.getNickname(),u3.getGender(),u3.getSecurityQuestion(),u3.getSecurityAnswer());
        this.players.add(p4);
        currentPlayer = p4;
        this.time = new Time();
        this.weather = setWeather();
    }
    public void nextDay(){
        this.weather = setWeather();
    }

    public models.enums.Weather setWeather(){
        int a = RAND.nextInt(1,12);
        String season = time.getSeason().toString();
        if(season.equals("SUMMER")){
            if (a%3 == 0)
                return Weather.RAINY;
            else if (a%4 == 0)
                return Weather.STORMY;
            else
                return Weather.SUNNY;
        }
        else if(season.equals("FALL")){
            if (a%3 == 0)
                return Weather.STORMY;
            else if (a%4 == 0)
                return Weather.RAINY;
            else
                return Weather.SUNNY;
        }
        else if(season.equals("WINTER")){
            if (a%3 == 0)
                return Weather.STORMY;
            else if (a%4 == 0)
                return Weather.RAINY;
            else if (a%5 == 0)
                return Weather.SUNNY;
            else
                return Weather.SNOWY;
        }
        else if(season.equals("SPRING")){
            if (a%3 == 0)
                return Weather.STORMY;
            else if (a%4 == 0)
                return Weather.SUNNY;
            else
                return Weather.RAINY;
        }
        else
            return Weather.SUNNY;
    }
    public void setNpc(){
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
        this.shops = shops;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }
}
