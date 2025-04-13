package models;

import models.enums.Weather;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private final Map map;
    private Time time;
    private Date date;
    private Weather weather;

    public Game (Map map , ArrayList<Player> players) {
        this.map = map;
        this.players = players;
    }
}
