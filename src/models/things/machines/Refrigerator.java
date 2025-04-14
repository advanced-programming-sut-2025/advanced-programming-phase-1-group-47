package models.things.machines;

import java.util.ArrayList;
import models.things.Item;


public abstract class Refrigerator extends Machine {
    private int capacity;
    private ArrayList<Item> items;
    public Refrigerator(int ItemID, int value) {
        super.Machine(ItemID, value);
        //TODO Auto-generated constructor stub
    }
}
