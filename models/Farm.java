package models;

import java.util.ArrayList;
import java.util.HashMap;

import models.buildings.Cottage;
import models.buildings.Lake;
import models.buildings.Quarry;
import models.buildings.greenHouse;
import models.enums.TileType;

public class Farm {
    int farmId;
    Ground ground;
    ArrayList<Lake> lakes = new ArrayList<>();
    Cottage cottage;
    greenHouse greenHouses;
    ArrayList<Quarry> quarries = new ArrayList<>();
    private ArrayList<Plant> plants = new ArrayList<>();
    public Point personPoint;
    public ArrayList<Point> FarmDors = new ArrayList<>();
    public TileType lastTileType = TileType.COTTAGE;
    public HashMap<Point,Plant> plantMap = new HashMap<>();
    public HashMap<Point,Building> BuildingMap = new HashMap<>();
    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }
    public HashMap<Point, Plant> getPlantMap() {
        return plantMap;
    }
    public void setPlantMap(Plant plant, Point point) {
        this.plantMap.put(point, plant);
    }
    public Plant getPlant(Point point) {
        return plantMap.get(point);
    }
    public Farm(int farmId, Ground ground, Cottage cottage, greenHouse greenHouses,
                ArrayList<Quarry> quarries, ArrayList<Lake> lakes, Point personPoint) {
        this.farmId = farmId;
        this.ground = ground;
        this.cottage = cottage;
        this.greenHouses = greenHouses;
        this.quarries = quarries;
        this.lakes = lakes;
        this.personPoint = personPoint;
    }

    public void setPlants(Plant plant) {
        this.plants.add(plant);
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public void setQuarries(ArrayList<Quarry> quarries) {
        this.quarries = quarries;
    }

    public void setGreenHouses(greenHouse greenHouses) {
        this.greenHouses = greenHouses;
    }

    public void setLastTileType(TileType lastTileType) {
        this.lastTileType = lastTileType;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public void setLakes(ArrayList<Lake> lakes) {
        this.lakes = lakes;
    }

    public void setCottage(Cottage cottage) {
        this.cottage = cottage;
    }

    public greenHouse getGreenHouses() {
        return greenHouses;
    }

    public void setPersonPoint(Point personPoint) {
        this.personPoint = personPoint;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public ArrayList<Quarry> getQuarries() {
        return quarries;
    }

    public Cottage getCottage() {
        return cottage;
    }

    public int getFarmId() {
        return farmId;
    }

    public TileType getLastTileType() {
        return lastTileType;
    }

    public Point getPersonPoint() {
        return personPoint;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }
    public Ground getGround() {
        return ground;
    }
}