package models;
public class Tree extends Plant {
    private final int foodCycle;

    public Tree(Point point, Map map, String name, int foodCycle) {
        super(point, map, name); 
        this.foodCycle = foodCycle; 
    }

    public int getFoodCycle() {
        return foodCycle;
    }
}
