package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import models.*;
import models.enums.Menu;
import models.enums.ShopType;
import models.enums.TileType;
import models.things.Item;
import models.things.tools.*;

public class shopMenuController {

    public Result<String> showAvailableProducts(Shop store) {
        if (!(App.currentGame.time.getHourOfDay() >= store.getStartingHour() &&  App.currentGame.time.getHourOfDay() <= store.getStoppingHour()))
            return new Result<>(false, "the Store is closed!");
        StringBuilder result = new StringBuilder();
        List<Item> seasonal = getSeasonalStock(store);
        if (seasonal != null) {
            for (Item i : seasonal) {
                if (i != null && i.getAmount() > 0) {
                    result.append(i.getName()).append(" - ").append(i.getValue()).append("$\n");
                }
            }
        }

        List<Item> perma = store.getPermaStock();
        if (perma != null && !perma.isEmpty()) {
            result.append("Permanent stock:\n");
            for (Item i : perma) {
                if (i != null && i.getAmount() > 0) {
                    result.append(i.getName()).append(" - ").append(i.getValue()).append("$\n");
                }
            }
        }

        return new Result<>(true, result.toString());
    }
    public Result<String> buyAnimals(Shop store, Matcher matcher) {
//        if (!(App.currentGame.time.getHourOfDay() >= store.getStartingHour() &&  App.currentGame.time.getHourOfDay() <= store.getStoppingHour()))
//            return new Result<>(false, "the Store is closed!");
        store = returnStoreToApp(store);
        String animal = matcher.group("animal").replace(" ", "").toLowerCase();
        String animalName = matcher.group("name");
        Player player = App.currentGame.getPlayers().get(((App.currentGame.turn - 1) < 0)? 4 + (App.currentGame.turn-1)%4 : (App.currentGame.turn)%4);
        for (Item i : store.getPermaStock()) {
            if (i.getName().equals(animal)) {
                i.reduceAmount(1);
                player.animalHashMap.put(animalName,(Animal)i);
                player.addMoney(-1  * 1 * i.getAmount());
                Random random = new Random();
                Point point = new Point(App.farmStart[App.currentGame.turn].x + random.nextInt(49)
                        , App.farmStart[App.currentGame.turn].y + random.nextInt(39));
                try{
                    App.currentGame.map.tiles[point.x][point.y].type = TileType.valueOf(animal.toUpperCase());
                }
                catch(Exception e){
                    return new Result<>(false, "Invalid animal name!");
                }
                return new Result<>(true, "the animal " + i.getName() + " has been PLaced in " + point.x + ", " + point.y + " !");
            }
        }
        return new Result<>(false, "no such product: " + animal);
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

    public Result<String> upgradeTool(Shop store, String toolName) {
        if (!store.getType().equals(ShopType.BlackSmith)) {
            return new Result<>(false, "You should be at blacksmith for this offer.");
        }
        if (!(App.currentGame.time.getHourOfDay() >= store.getStartingHour() &&  App.currentGame.time.getHourOfDay() <= store.getStoppingHour()))
            return new Result<>(false, "the Store is closed!");
        Player player = App.currentGame.currentPlayer;
        List<Item> items = player.getInvetory().getItems();
        if (items == null) return new Result<>(false, "Inventory is empty.");

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item == null) continue;
            int id = item.getItemID();

            Type current = null;
            Type next = null;

            if (id == 52 || id == 56 || id == 59 || id == 60 || id == 61) {
                try {
                    current = Type.valueOf(item.getName().split("-")[0].toUpperCase());
                } catch (Exception e) {
                    continue;  // Skip invalid tool name
                }

                next = current.ordinal() < Type.values().length - 1
                        ? Type.values()[current.ordinal() + 1]
                        : null;

                if (next == null) {
                    return new Result<>(false, "Already at max level.");
                }

                Item upgraded = switch (id) {
                    case 52 -> new Axe(next);
                    case 56 -> new Pickaxe(next);
                    case 59 -> {
                        player.trashCanLevel = Math.min(player.trashCanLevel + 1, 4);
                        yield null;
                    }
                    case 60 -> new WateringCan(next);
                    case 61 -> new Hoe(next);
                    default -> null;
                };

                if (upgraded != null) {
                    items.set(i, upgraded);
                    return new Result<>(true, toolName + " got upgraded to " + next.getName());
                } else if (id == 59) {
                    return new Result<>(true, "Trash can upgraded to level " + player.trashCanLevel);
                }
            }
        }

        return new Result<>(false, "Tool not found.");
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
        if (!(App.currentGame.time.getHourOfDay() >= store.getStartingHour() &&  App.currentGame.time.getHourOfDay() <= store.getStoppingHour()))
            return new Result<>(false, "the Store is closed!");
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
        for (Item i : store.getPermaStock()) {
            if (i.getName().equals(productName)) {
                if (amount > i.getAmount()) {
                    return new Result<>(false, "the Store doesnt have this amount \nAmount : " + i.getAmount());
                }
                i.reduceAmount(amount);
                player.getInvetory().addItem(new Item(i, amount));
                player.addMoney(-1  * amount * i.getAmount());
                return new Result<>(true, amount + " number  of product " + productName + " has been purchased");
            }
        }
        for (Item i : getSeasonalStock(store)) {
            if (i.getName().equals(productName)) {
                if (amount > i.getAmount()) {
                    return new Result<>(false, "the Store doesnt have this amount \nAmount : " + i.getAmount());
                }
                i.reduceAmount(amount);
                player.getInvetory().addItem(new Item(i, amount));
                player.addMoney(-1  * amount * i.getAmount());
                return new Result<>(true, ((amount > 1)?amount:1) + " number  of product " + productName + " has been purchased");
            }
        }
        return new Result<>(false, "no such product: " + productName);
    }

    public Shop returnStoreToApp(Shop store) {
        if (store == null) return null;
        ShopType type = store.getType();

        return switch (type) {
            case FishShop -> App.currentGame.FishShopStore;
            case Marnies -> App.currentGame.MarniesRanchStore;
            case JojaMart -> App.currentGame.JojaMartStore;
            case Carpenters -> App.currentGame.CarpenterStore;
            case BlackSmith -> App.currentGame.BlacksmithStore;
            case TheSaloon -> App.currentGame.TheSaloonStore;
            default -> null;
        };
    }

    public ArrayList<Item> getSeasonalStock(Shop store) {
        if (store == null || App.currentGame == null || App.currentGame.time == null) return null;

        String season = App.currentGame.time.getSeason().toString();
        return switch (season) {
            case "SPRING" -> store.getSpringStock();
            case "SUMMER" -> store.getSummerStock();
            case "FALL" -> store.getFallStock();
            case "WINTER" -> store.getWinterStock();
            default -> null;
        };
    }

    public TileType whatIsTileType() {
        if (App.currentGame == null) return null;

        int turn = App.currentGame.turn;
        if (App.currentGame.map == null || App.currentGame.map.farms == null) return null;

        Point playerPoint = App.currentGame.map.farms[turn].personPoint;
        if (App.currentGame.map.farms[turn].lastTileType != TileType.DOOR) return null;

        Map map = App.currentGame.map;
        Point farmOffset = App.farmStart[turn];
        Point relPerson = playerPoint;
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

        if (uniqueCount == 0) return null;

        int maxIndex = 0;
        for (int i = 1; i < uniqueCount; i++) {
            if (counts[i] > counts[maxIndex]) {
                maxIndex = i;
            }
        }

        return (counts[maxIndex] >= 5) ? types[maxIndex] : null;
    }

    public ShopType getShop() {
        TileType type = whatIsTileType();
        if (type == null) return null;

        return switch (type) {
            case BLACKSMITH -> ShopType.BlackSmith;
            case JOJAMART -> ShopType.JojaMart;
            case PIERRESSTORE -> ShopType.Pierres;
            case CARPENTER -> ShopType.Carpenters;
            case FISHSHOP -> ShopType.FishShop;
            case MARNIESRANCH -> ShopType.Marnies;
            case STARDROPSALOON -> ShopType.TheSaloon;
            default -> null;
        };
    }
}
