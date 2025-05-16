package models.things.machines;

import java.util.ArrayList;
import models.Point;
import models.things.Item;
public class Machine extends Item {
    private Point point;
    private ArrayList<Operation> operations;
    private Operation currentOperation;

    public Machine(String name, int itemID, int value, int parentItemID, int amount,ArrayList<Operation> operations) {
        super(name, itemID, value, parentItemID, amount);
        point = new Point(0, 0);
        this.operations = operations;
    }
    public Item getOutput() {
        return currentOperation.getOutput();
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }
    public void addOperation(Operation operation) {
        operations.add(operation);
    }
}