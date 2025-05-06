package models.things;

public class Item {
    private String name;
    private int ItemID;
    private int ParentItemID; //for wine/pickled vegtables/fruit jam /fish /plant /i hate NPCs
    private int value;
    private int amount;

    public Item(String name ,int ItemID , int value , int ParentItemID , int amount) {
        this.name = name;
        this.ItemID = ItemID;
        this.value = value;
        this.ParentItemID = ParentItemID;
        this.amount = amount;
    }
    public void addAmount(int add) {
        amount+=add;
    }
    public void reduceAmount(int minus) {
        amount+=minus;
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

    public String getName() {
        return name;
    }

    public int getParentItemID() {
        return ParentItemID;
    }

    public boolean questEquals(Item item) {
        if (item.getItemID() != this.ItemID)
            return false;
        if (item.getAmount() > this.amount)
            return false;
        else
            return true;

    }

    public int getAmount() {
        return amount;
    }
}  
