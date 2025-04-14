package models;
import models.Point;

public class Tree extends Plant {
    private final int foodCycle;
    private boolean itBecomeCool = false;
    public Tree(Point point, Map map, String name, int foodCycle) {
        super(point, map, name); 
        this.foodCycle = foodCycle; 
    }

    public void setItBecomeCool(boolean itBecomeCool) {
        this.itBecomeCool = itBecomeCool;
    }

    public int getFoodCycle() {
        return foodCycle;
    }
}
