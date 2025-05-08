package models;

import java.util.ArrayList;
import java.util.Arrays;
import models.NPCs.*;
import models.enums.Weather;

public class Game {
    private final ArrayList<Player> players;
    public Map map;
    public Time time;
    public Weather weather;
    public Weather tomarrowsWeather;
    public ArrayList<NPC> npcs;
    public Player currentPlayer;
    public Point personPoint;
    public Game(Player... players) {
        this.players = new ArrayList<>(Arrays.asList(players));
//        this.npcs = new ArrayList<>();
//        npcs.add(Abigail.getInstance().abigailBuilder());
//        npcs.add(Sebastion.getInstance().sebastionBuilder());
//        npcs.add(Harvey.getInstance().harveyBuilder());
//        npcs.add(Leah.getInstance().leahBuilder());
//        npcs.add(Robin.getInstance().robinBuilder());
//        this.time = new Time();
        if (!this.players.isEmpty()) {
            this.currentPlayer = this.players.get(0);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
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
}
