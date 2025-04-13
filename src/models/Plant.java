package models;
public class Plant {
    private Point point;
    private String name;
    private int growStage;
    private Map map;

    public Plant(Point point, Map map, String name) {
        this.point = point;
        this.map = map;
        this.name = name;
        this.growStage = 0;
    }
}
