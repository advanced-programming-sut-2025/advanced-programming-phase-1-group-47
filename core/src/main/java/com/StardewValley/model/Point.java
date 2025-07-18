package com.StardewValley.model;

public class Point {
    public int x;
    public int y;
    public Point(int x , int y){
        this.x = x;
        this.y = y;   
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void lowerX() {
        x-=1;
    }
    public void lowerY() {
        y-=1;
    }
    public void addX() {
        x+=1;
    }
    public void addY() {
        y+=1;
    }
    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
