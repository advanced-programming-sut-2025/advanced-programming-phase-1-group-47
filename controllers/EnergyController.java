package controllers;

import models.Energy;
import models.Player;
import models.Result;

public class EnergyController {
    public Result<String> PassOut() {
        return null;
    }
    public Result energyHandler(int energy, Player player) {
        player.setEnergy(energy);
        return null;
    }
    public void passOut(int energy, Player player){
//        if(energy < 0){
//             new Energy(player.getEnergy(),0);
//        }
//        return null;
    }
}
