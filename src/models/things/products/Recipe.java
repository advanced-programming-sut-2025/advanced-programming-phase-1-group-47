package models.things.products;
import java.util.*;
import models.things.Item;

public abstract class Recipe extends Item {
    private boolean isCooking;
    private ArrayList<Item> items;
}
