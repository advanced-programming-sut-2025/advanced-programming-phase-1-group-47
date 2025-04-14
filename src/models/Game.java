package models;

import java.util.ArrayList;
import models.enums.Weather;

public class Game {
    private final ArrayList<Player> players;
    private  Map map;
    private Time time;
    private Date date;
    private Weather weather;
    private Weather tomarrowsWeather;

    public Game (Map map , ArrayList<Player> players) {
        this.map = map;
        this.players = players;
    }
}
