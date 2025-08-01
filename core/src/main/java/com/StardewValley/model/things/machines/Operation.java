package com.StardewValley.model.things.machines;

import com.StardewValley.model.things.Item;

public class Operation {
    int id;
    int readyTime;
    int currentTime;
    Item input;
    Item output;
    boolean isReadyTomarrow;

    public Operation(int id ,int readyTime , Item input , Item output , boolean isReadyTomarrow){
        this.id = id;
        this.readyTime = readyTime;
        this.currentTime = 0;
        this.input = input;
        this.output = output;
        this.isReadyTomarrow = isReadyTomarrow;
    }
    public Operation(Operation operation ,Item input) {
        this.id = operation.getId();
        this.readyTime = operation.getReadyTime();
        this.currentTime = 0;
        this.input = input;
        this.output = operation.getOutput();
        this.isReadyTomarrow = operation.isReadyTomarrow;
    }

    public void increaseCurrentTime(int amount) {
        currentTime+=amount;
    }
    
    public int getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(int readyTime) {
        this.readyTime = readyTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public Item getInput() {
        return input;
    }

    public void setInput(Item input) {
        this.input = input;
    }

    public Item getOutput() {
        return output;
    }

    public void setOutput(Item output) {
        this.output = output;
    }

    public boolean isIsReadyTomarrow() {
        return isReadyTomarrow;
    }

    public void setIsReadyTomarrow(boolean isReadyTomarrow) {
        this.isReadyTomarrow = isReadyTomarrow;
    }

    public int getId() {
        return id;
    }
}
