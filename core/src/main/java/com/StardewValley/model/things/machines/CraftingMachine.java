package com.StardewValley.model.things.machines;

import com.StardewValley.model.App;
import com.StardewValley.model.things.Item;

public class CraftingMachine {

    public String MakeCherryBomb () {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 4 copper ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 392) {
                if(item.getAmount() >= 4) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }

        //reduce 4 copper ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 392) {
                item.setAmount(item.getAmount() - 4);
            }
        }

        //reduce 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a cherry bomb
        Item cherryBomb = new Item("CHERRY BOMB", 5000,50,1,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Cherry bomb is crafted successfully!";
    }

    public String MakeBomb() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 4 iron ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 393) {
                if(item.getAmount() >= 4) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }

        //reduce 4 iron ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 393) {
                item.setAmount(item.getAmount() - 4);
            }
        }

        //reduce 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a  bomb
        Item cherryBomb = new Item("BOMB", 5001,50,1,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Bomb is crafted successfully!";
    }

    public String MakeMegaBomb() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 4 gold ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 394) {
                if(item.getAmount() >= 4) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }

        //reduce 4 gold ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 394) {
                item.setAmount(item.getAmount() - 4);
            }
        }

        //reduce 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a  mega bomb
        Item cherryBomb = new Item("MEGA BOMB", 5002,50,1,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Mega bomb is crafted successfully!";
    }

    public String MakeSprinkler(){
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 1 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }


        //reduce 4 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //reduce 4 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a  sprinkler
        Item cherryBomb = new Item("Sprinkler",5003,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Sprinkler is crafted successfully!";
    }

    public String MakeQualitySprinkler(){
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 1 gold bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 44) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }


        //reduce 4 gold bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 44) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //reduce 4 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a  quality sprinkler
        Item cherryBomb = new Item("Quality Sprinkler",5004,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Quality Sprinkler is crafted successfully!";
    }

    public String MakeIridiumSprinkler(){
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 1 gold bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 44) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 iridium bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 21) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }


        //reduce 4 gold bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 44) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //reduce 4 iridium bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 21) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making an  iridium sprinkler
        Item cherryBomb = new Item("Iridium Sprinkler",40,2,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Iridium Sprinkler is crafted successfully!";
    }

    public String MakingCharcoalKlin() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 20 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 20) {
                    n++;
                    break;
                }
            }
        }

        //searching for 2 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                if(item.getAmount() >= 2) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }

        //reduce 20 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 20);
            }
        }

        //reduce 2 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                item.setAmount(item.getAmount() - 2);
            }
        }

        //making a  charcoal klin
        Item cherryBomb = new Item("Charcoal Klin",5005,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Charcoal klin is crafted successfully!";
    }

    public String MakeFurnace() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 20 copper ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 392) {
                if(item.getAmount() >= 20) {
                    n++;
                    break;
                }
            }
        }

        //searching for 25 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                if(item.getAmount() >= 25) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }

        //reduce 20 copper ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 392) {
                item.setAmount(item.getAmount() - 20);
            }
        }

        //reduce 25 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                item.setAmount(item.getAmount() - 25);
            }
        }

        //making a  furnace
        Item cherryBomb = new Item("Furnace",5006,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Furnace is crafted successfully!";

    }

    public String MakeScarecrow() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 50) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        //searching for 20 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                if(item.getAmount() >= 20) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 50);
            }
        }

        //reduce 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //reduce 20 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                item.setAmount(item.getAmount() - 20);
            }
        }

        //making a  Scarecrow
        Item cherryBomb = new Item("Scarecrow",5007,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Scarecrow is crafted successfully!";

    }

    public String MakeDeluxeScarecrow() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 50) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        //searching for 20 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                if(item.getAmount() >= 20) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 iridium ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 395) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 4) {
            return "Not enough ingredient!";
        }

        //reduce 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 50);
            }
        }

        //reduce 1 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //reduce 20 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                item.setAmount(item.getAmount() - 20);
            }
        }

        //reduce 1 iridium ore
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 395) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a  deluxe Scarecrow
        Item cherryBomb = new Item("Deluxe Scarecrow",5008,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Deluxe Scarecrow is crafted successfully!";

    }

    public String MakeBeeHouse() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 50) {
                    n++;
                    break;
                }
            }
        }

        //searching for 8 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                if(item.getAmount() >= 8) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 50);
            }
        }

        //reduce 8 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                item.setAmount(item.getAmount() - 8);
            }
        }

        //reduce 1 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a  bee house
        Item cherryBomb = new Item("BeeHouse",43,2,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Bee house is crafted successfully!";

    }

    public String MakeCheesePress() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 45 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 45) {
                    n++;
                    break;
                }
            }
        }

        //searching for 45 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                if(item.getAmount() >= 45) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 45 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 45);
            }
        }

        //reduce 45 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                item.setAmount(item.getAmount() - 45);
            }
        }

        //reduce 1 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a  cheese press
        Item cherryBomb = new Item("Cheese Press",5009,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Cheese press is crafted successfully!";

    }

    public String MakeKeg() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 30 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 30) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 iron bat
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 30 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 30);
            }
        }

        //reduce 1 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //reduce 1 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a  keg
        Item cherryBomb = new Item("Keg",5010,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Keg is crafted successfully!";

    }

    public String MakeLoom() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 60 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 60) {
                    n++;
                    break;
                }
            }
        }

        //searching for 30 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                if(item.getAmount() >= 30) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }

        //reduce 60 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 60);
            }
        }

        //reduce 30 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                item.setAmount(item.getAmount() - 30);
            }
        }

        //making a  loom
        Item cherryBomb = new Item("Loom",5011,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Loom is crafted successfully!";

    }

    public String MakeMayonnaiseMachine() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 15 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 15) {
                    n++;
                    break;
                }
            }
        }

        //searching for 15 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                if(item.getAmount() >= 15) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 15 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 15);
            }
        }

        //reduce 15 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                item.setAmount(item.getAmount() - 15);
            }
        }

        //reduce 1 copper bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6000) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a Mayonnaise Machine
        Item cherryBomb = new Item("Mayonnaise Machine",5012,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Mayonnaise Machine is crafted successfully!";

    }

    public String MakeOilMaker() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 100 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 100) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 gold bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 44) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 100 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 100);
            }
        }

        //reduce 1 gold bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 44) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //reduce 1 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a oil maker
        Item cherryBomb = new Item("Oil Maker",5013,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Oil maker Machine is crafted successfully!";
    }

    public String MakePreservesJar() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 50) {
                    n++;
                    break;
                }
            }
        }

        //searching for 40 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                if(item.getAmount() >= 40) {
                    n++;
                    break;
                }
            }
        }

        //searching for 8 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                if(item.getAmount() >= 8) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 50);
            }
        }

        //reduce 40 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                item.setAmount(item.getAmount() - 40);
            }
        }

        //reduce 8 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                item.setAmount(item.getAmount() - 8);
            }
        }

        //making a Preserves Jar
        Item cherryBomb = new Item(";Preserves Jar",5014,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Preserves Jar Machine is crafted successfully!";

    }

    public String MakeDehydrator() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 30 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 30) {
                    n++;
                    break;
                }
            }
        }

        //searching for 20 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                if(item.getAmount() >= 20) {
                    n++;
                    break;
                }
            }
        }

        //searching for 30 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                if(item.getAmount() >= 30) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 30 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 30);
            }
        }

        //reduce 20 stone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 2) {
                item.setAmount(item.getAmount() - 20);
            }
        }

        //reduce 30 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                item.setAmount(item.getAmount() - 30);
            }
        }

        //making a Dehydrator
        Item cherryBomb = new Item("Dehydrator",5015,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Dehydrator Machine is crafted successfully!";

    }

    public String MakeGrassStarter() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 1 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        //searching for 1 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                if(item.getAmount() >= 1) {
                    n++;
                    break;
                }
            }
        }

        if(n < 2) {
            return "Not enough ingredient!";
        }

        //reduce 1 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //reduce 1 fiber
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6001) {
                item.setAmount(item.getAmount() - 1);
            }
        }

        //making a grass starter
        Item cherryBomb = new Item("Grass Starter",25,20,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Grass starter Machine is crafted successfully!";

    }

    public String MakeFishSmoker() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                if(item.getAmount() >= 50) {
                    n++;
                    break;
                }
            }
        }

        //searching for 3 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                if(item.getAmount() >= 3) {
                    n++;
                    break;
                }
            }
        }

        //searching for 10 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                if(item.getAmount() >= 10) {
                    n++;
                    break;
                }
            }
        }

        if(n < 3) {
            return "Not enough ingredient!";
        }

        //reduce 50 wood
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 36) {
                item.setAmount(item.getAmount() - 50);
            }
        }

        //reduce 3 iron bar
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 37) {
                item.setAmount(item.getAmount() - 3);
            }
        }

        //reduce 10 coal
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 396) {
                item.setAmount(item.getAmount() - 10);
            }
        }

        //making a fish smoker
        Item cherryBomb = new Item("Fish smoker",5016,0,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Fish smoker Machine is crafted successfully!";

    }

    public String MakingMysticTreeSeed() {
        //checking for capacity
        int capacity = App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getCapacity();
        int items = 0;
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            items += item.getAmount();
        }

        if(capacity <= items) {
            return "Not enough space in inventory!";
        }

        int n = 0;

        //searching for 5 acorn
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6002) {
                if(item.getAmount() >= 5) {
                    n++;
                    break;
                }
            }
        }

        //searching for 5 maple seed
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6003) {
                if(item.getAmount() >= 5) {
                    n++;
                    break;
                }
            }
        }

        //searching for 5 pine cone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6004) {
                if(item.getAmount() >= 5) {
                    n++;
                    break;
                }
            }
        }

        //searching for 5 Sahogany Seed
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6005) {
                if(item.getAmount() >= 5) {
                    n++;
                    break;
                }
            }
        }

        if(n < 4) {
            return "Not enough ingredient!";
        }

        //reduce 5 acorn
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6002) {
                item.setAmount(item.getAmount() - 5);
            }
        }

        //reduce 5 maple seed
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6003) {
                item.setAmount(item.getAmount() - 5);
            }
        }

        //reduce 5 pine cone
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6004) {
                item.setAmount(item.getAmount() - 15);
            }
        }

        //reduce 5 Sahogany Seed
        for(Item item : App.getLoggedInUser().getGame().getCurrentPlayer().getInvetory().getItems()) {
            if(item.getItemID() == 6005) {
                item.setAmount(item.getAmount() - 5);
            }
        }

        //making a Mystic Tree Seed
        Item cherryBomb = new Item("Mystic Tree Seed",5017,100,0,1);
        App.getCurrentGame().getCurrentPlayer().getInvetory().addItem(cherryBomb);

        //item has crafted
        return "Mystic Tree Seed Machine is crafted successfully!";
    }





}
