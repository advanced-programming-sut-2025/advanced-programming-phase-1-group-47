package com.StardewValley.model;

public class Result<Type> {
    public boolean success;
    public Type data;

    public Result(boolean success, Type data) {
        this.success = success;
        this.data = data;
    }

    public Type getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
