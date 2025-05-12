package models;

import java.util.ArrayList;
import java.util.Arrays;
import models.enums.Weather;

public class Game {
    private final ArrayList<Player> players;
    public Map map;
    public Time time = new Time();
    public Weather weather;
    public Weather tomarrowsWeather;
    public ArrayList<NPC> npcs;
    public Player currentPlayer;
    public Point personPoint;
    public int turn = 0;
    private ArrayList<Shop> shops;
    private ArrayList<Plant> plants;
    public Game(Player... players) {
        this.players = new ArrayList<>(Arrays.asList(players));
//        this.npcs = new ArrayList<>();
//        npcs.add(Abigail.getInstance().abigailBuilder());
//        npcs.add(Sebastion.getInstance().sebastionBuilder());
//        npcs.add(Harvey.getInstance().harveyBuilder());
//        npcs.add(Leah.getInstance().leahBuilder());
//        npcs.add(Robin.getInstance().robinBuilder());
//        this.time = new Time();
//        if (!this.players.isEmpty()) {
//            this.currentPlayer = this.players.get(0);
//        }
        for(Player player : this.players)
            player.setupRelations();
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
