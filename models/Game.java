package models;

import java.util.ArrayList;
import models.NPCs.*;
import models.enums.Weather;

public class Game {
    private final ArrayList<Player> players;
    private  Map map;
    private Time time;
    private Weather weather;
    private Weather tomarrowsWeather;
    private ArrayList<NPC> npcs;
    private Player currentPlayer;
    public Game (Map map , ArrayList<Player> players , Player creator) {
        this.map = map;
        this.players = players;
        npcs.add(Abigail.getInstance().abigailBuilder());
        npcs.add(Sebastion.getInstance().sebastionBuilder());
        npcs.add(Harvey.getInstance().harveyBuilder());
        npcs.add(Leah.getInstance().leahBuilder());
        npcs.add(Robin.getInstance().robinBuilder());
        this.currentPlayer = creator;
        time = new Time();
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
