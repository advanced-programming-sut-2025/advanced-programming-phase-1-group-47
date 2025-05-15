package controllers;

import java.util.ArrayList;
import models.App;
import models.Player;
import models.Result;
import models.Trade;
import models.things.Item;

public class TradeMenuController {
    public Result<String> trade(String username, String type, String itemName, String amount, String price) {
        if (!price.matches("\\d+") || !amount.matches("\\d+"))
            return new Result<>(false, "Amount or price format invalid!");

        if (!type.equalsIgnoreCase("offer") && !type.equalsIgnoreCase("request"))
            return new Result<>(false, "type invalid!");

        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Item nullItem = new Item("null item", 0, 0, 0, 1);

        for (Player player : App.getCurrentGame().getPlayers()) {
            if (!player.getUsername().equalsIgnoreCase(username))
                continue;

            if (type.equalsIgnoreCase("offer")) {
                for (Item item : currentPlayer.getInvetory().getItems()) {
                    if (!item.getName().equalsIgnoreCase(itemName))
                        continue;

                    if (item.getAmount() < Integer.parseInt(amount))
                        return new Result<>(false, "You don't have enough of that item!");

                    item.reduceAmount(Integer.parseInt(amount));

                    if (item.getAmount() == 0)
                        currentPlayer.getInvetory().removeItem(item);

                    player.addTradeToPendingTrades(
                        currentPlayer,
                        new Trade(
                            App.getTradeIdCounter(),
                            new Item(item, Integer.parseInt(amount)),
                            nullItem,
                            0,
                            Integer.parseInt(price)
                        )
                    );

                    App.addTradeIdCounter();
                    player.addNotifToNotifications("You have a new Trade offer!!!!");
                    return new Result<>(true, "Offer Sent!");
                }
                return new Result<>(false, "You don't have that item!");
            }

            if (type.equalsIgnoreCase("request")) {
                if (currentPlayer.getMoney() < Integer.parseInt(price))
                    return new Result<>(false, "You don't have enough money...");

                for (Item item : player.getInvetory().getItems()) {
                    if (!item.getName().equalsIgnoreCase(itemName))
                        continue;

                    if (item.getAmount() < Integer.parseInt(amount))
                        return new Result<>(false, "Player does not have enough of that Item!");

                    item.reduceAmount(Integer.parseInt(amount));

                    if (item.getAmount() == 0)
                        player.getInvetory().removeItem(item);

                    player.addTradeToPendingTrades(
                        currentPlayer,
                        new Trade(
                            App.getTradeIdCounter(),
                            nullItem,
                            new Item(item, Integer.parseInt(amount)),
                            Integer.parseInt(price),
                            0
                        )
                    );

                    App.addTradeIdCounter();
                    player.addNotifToNotifications("You have a new Trade request!!!!");
                    return new Result<>(true, "Request Sent!");
                }

                return new Result<>(false, "Player does not have the item or that item is not real!");
            }
        }

        return new Result<>(false, "No player found with that Username!");
    }

    public Result<String> trade(String username, String type, String itemName, String amount, String targetItemName, String targetAmount) {
        if (!amount.matches("\\d+") || !targetAmount.matches("\\d+"))
            return new Result<>(false, "Amount or target Amount format invalid!");

        if (!type.equalsIgnoreCase("offer") && !type.equalsIgnoreCase("request"))
            return new Result<>(false, "type invalid!");

        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        int intAmount = Integer.parseInt(amount);
        int intTargetAmount = Integer.parseInt(targetAmount);

        for (Player player : App.getCurrentGame().getPlayers()) {
            if (!player.getUsername().equalsIgnoreCase(username))
                continue;

            if (type.equalsIgnoreCase("offer")) {
                for (Item item : currentPlayer.getInvetory().getItems()) {
                    if (!item.getName().equalsIgnoreCase(itemName))
                        continue;

                    for (Item targetItem : player.getInvetory().getItems()) {
                        if (!targetItem.getName().equalsIgnoreCase(targetItemName))
                            continue;

                        if (item.getAmount() < intAmount)
                            return new Result<>(false, "You don't have enough of that item!");

                        if (targetItem.getAmount() < intTargetAmount)
                            return new Result<>(false, "Player doesn't have enough of that item!");

                        item.reduceAmount(intAmount);
                        targetItem.reduceAmount(intTargetAmount);

                        if (item.getAmount() == 0)
                            currentPlayer.getInvetory().removeItem(item);

                        if (targetItem.getAmount() == 0)
                            player.getInvetory().removeItem(targetItem);

                        player.addTradeToPendingTrades(
                            currentPlayer,
                            new Trade(
                                App.getTradeIdCounter(),
                                new Item(item, intAmount),
                                new Item(targetItem, intTargetAmount),
                                0, 0
                            )
                        );

                        App.addTradeIdCounter();
                        player.addNotifToNotifications("You have a new Trade offer!!!!");
                        return new Result<>(true, "Offer Sent!");
                    }
                }
                return new Result<>(false, "You don't have that item! // Player doesn't have that item!");
            }

            if (type.equalsIgnoreCase("request")) {
                for (Item item : player.getInvetory().getItems()) {
                    if (!item.getName().equalsIgnoreCase(itemName))
                        continue;

                    for (Item targetItem : currentPlayer.getInvetory().getItems()) {
                        if (!targetItem.getName().equalsIgnoreCase(targetItemName))
                            continue;

                        if (targetItem.getAmount() < intTargetAmount)
                            return new Result<>(false, "You don't have enough of that item!");

                        if (item.getAmount() < intAmount)
                            return new Result<>(false, "Player doesn't have enough of that item!");

                        item.reduceAmount(intAmount);
                        targetItem.reduceAmount(intTargetAmount);

                        if (item.getAmount() == 0)
                            player.getInvetory().removeItem(item);

                        if (targetItem.getAmount() == 0)
                            currentPlayer.getInvetory().removeItem(targetItem);

                        player.addTradeToPendingTrades(
                            currentPlayer,
                            new Trade(
                                App.getTradeIdCounter(),
                                new Item(targetItem, intTargetAmount),
                                new Item(item, intAmount),
                                0, 0
                            )
                        );

                        App.addTradeIdCounter();
                        player.addNotifToNotifications("You have a new Trade request!!!!");
                        return new Result<>(true, "Request Sent!");
                    }
                }
                return new Result<>(false, "Player doesn't have that item! // You don't have that item!");
            }
        }

        return new Result<>(false, "No player found with that Username!");
    }

    public Result<String> tradeResponse(String message, String tradeId) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();

        if (!tradeId.matches("\\d+"))
            return new Result<>(false, "Trade ID format not valid!");

        if (!message.equalsIgnoreCase("accept") && !message.equalsIgnoreCase("reject"))
            return new Result<>(false, "Response format invalid");

        for (Player player : App.getCurrentGame().getPlayers()) {
            ArrayList<Trade> tradeList = currentPlayer.getPendingTrades().get(player);
            if (tradeList == null) continue;

            for (Trade trade : tradeList) {
                if (trade.getId() == Integer.parseInt(tradeId)) {
                    if (message.equalsIgnoreCase("accept")) {
                        player.getInvetory().addItem(trade.getTargetItem());
                        player.addMoney(trade.getTargetPrice());
                        currentPlayer.getInvetory().addItem(trade.getItem());
                        currentPlayer.addMoney(trade.getPrice());
                        player.addFriendshipXP(50, currentPlayer);
                        currentPlayer.addFriendshipXP(50, player);
                        tradeList.remove(trade);
                        return new Result<>(true, "Trade ACCEPTED!");
                    }

                    if (message.equalsIgnoreCase("reject")) {
                        currentPlayer.getInvetory().addItem(trade.getTargetItem());
                        currentPlayer.addMoney(trade.getTargetPrice());
                        player.getInvetory().addItem(trade.getItem());
                        player.addMoney(trade.getPrice());
                        player.reduceFriendshipXP(30, currentPlayer);
                        currentPlayer.reduceFriendshipXP(30, player);
                        tradeList.remove(trade);
                        return new Result<>(true, "Trade REJECTED");
                    }
                }
            }
        }

        return new Result<>(false, "Trade ID invalid");
    }
}
