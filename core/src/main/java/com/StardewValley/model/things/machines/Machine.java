package com.StardewValley.model.things.machines;

import com.StardewValley.model.Point;
import com.StardewValley.model.things.Item;

import java.util.ArrayList;
public class Machine extends Item {
    private Point point;
    private ArrayList<Operation> operations;
    protected Operation currentOperation;

    public Machine(String name, int itemID, int value, int parentItemID, int amount,ArrayList<Operation> operations) {
        super(name, itemID, value, parentItemID, amount);
        point = new Point(0, 0);
        this.operations = operations;
        currentOperation = new Operation(0, 0,new Item("null", 0, 0,0, 1),new Item("null", 0, 0,0, 1), false);
    }
    public Machine(Machine machine , int amount ,Point point){
        super(machine.getName(),machine.getItemID(),machine.getValue(),machine.getParentItemID(),amount);
        this.point = point;
        this.operations = machine.operations;
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

    public void setCurrentOperation(Operation currentOperation) {
        this.currentOperation = currentOperation;
    }

    public Operation getCurrentOperation() {
        return currentOperation;
    }
}