package models.things;

public abstract class Item {
    private String name;
    private int ItemID;
    private int value;
    private int amount;

    public void Item(int ItemID , int value) {
        this.ItemID = ItemID;
        this.value = value;
    }
    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}
