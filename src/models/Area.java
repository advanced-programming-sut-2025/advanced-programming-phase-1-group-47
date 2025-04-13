package models;

public class Area {
    private Point startLocation;
    private Point endLocation;
    public int getArea(){
        int width = Math.abs(startLocation.x - endLocation.x);
        int height = Math.abs(startLocation.y - endLocation.y);
        return width * height;
    }
}
