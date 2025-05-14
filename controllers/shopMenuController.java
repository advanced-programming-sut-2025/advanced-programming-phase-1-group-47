package controllers;

import models.*;
import models.Shops.Blacksmith;
import models.enums.ShopType;
import models.enums.TileType;
import models.things.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class shopMenuController {
    public Result<String> showAvailableProducts(Shop store) {
        StringBuilder result = new StringBuilder();

        for (Item i : getSeasonalStock(store)) {
            if (i.getAmount() == 0) continue;
            result.append(i.getName()).append(" - ").append(i.getValue()).append("$\n");
        }

        result.append("Permanent stock:\n");
        for (Item i : store.getPermaStock()) {
            if (i.getAmount() == 0) continue;
            result.append(i.getName()).append(" - ").append(i.getValue()).append("$\n");
        }

        return new Result<>(true, result.toString());
    }
    public Result<String> showAllProducts(Shop store) {
        StringBuilder result = new StringBuilder();

        appendItemList(result, store.getSpringStock(), "Spring");
        appendItemList(result, store.getSummerStock(), "Summer");
        appendItemList(result, store.getFallStock(), "Fall");
        appendItemList(result, store.getWinterStock(), "Winter");
        appendItemList(result, store.getPermaStock(), "Permanent");

        if (result.length() == 0) {
            return new Result<>(true, "No products available in the shop.");
        }

        return new Result<>(true, result.toString());
    }

    private void appendItemList(StringBuilder result, List<Item> items, String label) {
        if (items == null || items.isEmpty()) return;
        result.append(label).append(" stock:\n");
        boolean hasItem = false;
        for (Item i : items) {
            if (i != null && i.getAmount() > 0) {
                result.append(i.getName()).append(" - ").append(i.getValue()).append("$\n");
                hasItem = true;
            }
        }

        if (!hasItem) {
            result.append("No items available.\n");
        }
    }

    public Result<String> buy(Shop store, Matcher matcher) {
        int amount;
        store = returnStoreToApp(store);
        StringBuilder result = new StringBuilder();
        String productName = matcher.group("product");
        Player player = App.currentGame.getPlayers().get(((App.currentGame.turn - 1) < 0)? 4 + (App.currentGame.turn-1)%4 : (App.currentGame.turn)%4);
        System.out.println(player.getNickname());
        try{
            amount = Integer.parseInt(matcher.group("count"));
        }
        catch (NumberFormatException e){
            if (matcher.group("count") == null)
                amount = 1;
            else
                return new Result<>(false, "Invalid amount.");
        }
        for (Item i : getSeasonalStock(store)) {
            if (i.getName().equals(productName)) {
                if (amount > i.getAmount()) {
                    return new Result<>(false, "the Store doesnt have this amount \nAmount : " + i.getAmount());
                }
                i.reduceAmount(amount);
                player.setInvetory(i,amount);
                return new Result<>(true, ((amount > 1)?amount:1) + " number  of product " + productName + " has been purchased");
            }
        }
        for (Item i : store.getPermaStock()) {
            if (i.getName().equals(productName)) {
                if (amount > i.getAmount()) {
                    return new Result<>(false, "the Store doesnt have this amount \nAmount : " + i.getAmount());
                }
                i.reduceAmount(amount);
                player.setInvetory(i,amount);
                return new Result<>(true, amount + " number  of product " + productName + " has been purchased");
            }
        }
        return new Result<>(false, "no such product: " + productName);
    }
    public Shop returnStoreToApp(Shop store) {
        ShopType type = store.getType();
        if (type.equals(ShopType.FishShop)) {
            return App.currentGame.FishShopStore;
        } else if (type.equals(ShopType.Marnies)) {
            return App.currentGame.MarniesRanchStore;
        } else if (type.equals(ShopType.JojaMart)) {
            return App.currentGame.JojaMartStore;
        } else if (type.equals(ShopType.Carpenters)) {
            return App.currentGame.CarpenterStore;
        } else if (type.equals(ShopType.BlackSmith)) {
            return App.currentGame.BlacksmithStore;
        } else if (type.equals(ShopType.TheSaloon)) {
            return App.currentGame.TheSaloonStore;
        }
        else
            return null;
    }

    public ArrayList<Item> getSeasonalStock(Shop store) {
        String season = App.currentGame.time.getSeason().toString();
        switch (season) {
            case "SPRING":
                return store.getSpringStock();
            case "SUMMER":
                return store.getSummerStock();
            case "FALL":
                return store.getFallStock();
            case "WINTER":
                return store.getWinterStock();
            default:
                return new ArrayList<>(); // اگر فصل ناشناخته بود، لیست خالی برمی‌گرده
        }
    }

    public TileType whatIsTileType(){
        if (App.currentGame == null)
            return null;
        int turn = App.currentGame.turn;
        Point playerPont = App.currentGame.map.farms[turn].personPoint;
        if (App.currentGame.map.farms[turn].lastTileType == TileType.DOOR) {
            Map map = App.currentGame.map;
            Point farmOffset = App.farmStart[turn];
            Point relPerson = map.farms[turn].personPoint;
            Point absPerson = new Point(farmOffset.x + relPerson.x, farmOffset.y + relPerson.y);

            int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

            TileType[] types = new TileType[8];
            int[] counts = new int[8];
            int uniqueCount = 0;

            for (int i = 0; i < 8; i++) {
                int nx = absPerson.x + dx[i];
                int ny = absPerson.y + dy[i];

                if (nx >= 0 && nx < map.tiles.length && ny >= 0 && ny < map.tiles[0].length) {
                    Tile neighbor = map.tiles[nx][ny];
                    if (neighbor != null && neighbor.type != null) {
                        boolean found = false;
                        for (int j = 0; j < uniqueCount; j++) {
                            if (types[j] == neighbor.type) {
                                counts[j]++;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            types[uniqueCount] = neighbor.type;
                            counts[uniqueCount] = 1;
                            uniqueCount++;
                        }
                    }
                }
            }
            if (uniqueCount == 0) {
                System.out.println("No valid surrounding tiles found.");
            } else {
                int maxIndex = 0;
                for (int i = 1; i < uniqueCount; i++) {
                    if (counts[i] > counts[maxIndex]) {
                        maxIndex = i;
                    }
                }
                if (counts[maxIndex] == 5 ) {
                    return types[maxIndex];
                }
//                System.out.println("Most common TileType around player: " +  + " (" + counts[maxIndex] + " times)");
            }
        }
        return null;
    }

    public ShopType getShop() {
        if(whatIsTileType().equals(TileType.BLACKSMITH)) {
            return ShopType.BlackSmith;
        } else if(whatIsTileType().equals(TileType.JOJAMART)) {
            return ShopType.JojaMart;
        } else if(whatIsTileType().equals(TileType.PIERRESSTORE)) {
            return ShopType.Pierres;
        } else if(whatIsTileType().equals(TileType.CARPENTER)) {
            return ShopType.Carpenters;
        } else if(whatIsTileType().equals(TileType.FISHSHOP)) {
            return ShopType.FishShop;
        } else if(whatIsTileType().equals(TileType.MARNIESRANCH)) {
            return ShopType.Marnies;
        } else if(whatIsTileType().equals(TileType.STARDROPSALOON)) {
            return ShopType.TheSaloon;
        }
        return null;
    }
}
