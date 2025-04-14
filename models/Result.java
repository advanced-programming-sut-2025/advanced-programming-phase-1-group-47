package models;

public class Result<Type> {
    public boolean success;
    public Type data;

    public Result(boolean success, Type data) {
        this.success = success;
        this.data = data;
    }
        
    @Override
    public String toString() {
        return data.toString();
    }
}
