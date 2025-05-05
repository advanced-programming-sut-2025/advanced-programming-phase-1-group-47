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
    public Game (Map map , ArrayList<Player> players) {
        this.map = map;
        this.players = players;
        npcs.add(Abigail.getInstance().abigailBuilder());
        npcs.add(Sebastion.getInstance().sebastionBuilder());
        npcs.add(Harvey.getInstance().harveyBuilder());
        npcs.add(Leah.getInstance().leahBuilder());
        npcs.add(Robin.getInstance().robinBuilder());
        time = new Time();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
