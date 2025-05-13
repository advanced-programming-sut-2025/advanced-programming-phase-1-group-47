package controllers;

import models.App;
import models.Player;
import models.Result;

public class TradeMenuController {
    public Result<String> trade(String username , String type , String item , String amount ,String price , String targetItem , String targetAmount){
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(player.getUsername().equalsIgnoreCase(username)) {
                if (!type.equals("offer") && !type.equals("request"))
                    return new Result<>(false, "type invalid!");
                
            }
        }
        return new Result<>(false, "No player found with that Username!");
    }
    public Result<String> AcceptOrDenyTrade(String messege , String tradeId) {
        return null;
    }
}
