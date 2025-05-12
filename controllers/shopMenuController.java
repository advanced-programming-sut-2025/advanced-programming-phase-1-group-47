package controllers;

import models.*;
import models.enums.ShopType;
import models.enums.TileType;
import models.things.Item;

public class shopMenuController {
    public TileType whatIsTileType(){
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

    public void showAllProducts() {
//        ShopType shopType = getShop();
//
//        Shop shop;
    }

    public void showAvailableProducts() {

    }

    public Result<String> purchase(String productName, String stringAmount) {
//        if(!stringAmount.matches("^\\d$")) {
//            return new Result<>(false, "Invalid form of amount!");
//        }
//
//        int amount =Integer.parseInt(stringAmount);


        return null;
    }
}
