package models;

import java.util.ArrayList;
import models.things.producers.Producer;

public class Map {
    private int width;
    private int height;
    private Tile[][] tiles = new Tile[width][height];
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Producer> products = new ArrayList<>();
    

}
