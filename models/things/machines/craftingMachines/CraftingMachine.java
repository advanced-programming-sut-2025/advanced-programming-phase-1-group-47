package models.things.machines.craftingMachines;

import models.things.machines.Machine;

public class CraftingMachine extends Machine {
    public CraftingMachine(String name, int itemID, int value, int parentItemID, int amount) {
        super(name, itemID, value, parentItemID, amount);
    }
}
