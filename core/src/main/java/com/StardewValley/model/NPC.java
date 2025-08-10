package com.StardewValley.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.StardewValley.View.Helpers.DialogUtils;
import com.StardewValley.View.Helpers.InventoryDialog;
import com.StardewValley.controllers.GameMenuController;
import com.StardewValley.model.things.Item;
import com.StardewValley.model.things.relations.Quest;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static com.StardewValley.View.GameScreen.dialogStage;

public class NPC {
    private Map <Player, Integer> friendship;
    private Map <Player, Boolean> hasBeenTalkedTo;
    private Map <Player, Boolean> hasBeenGiftedTo;
    private String name;
    private String job;
    private Quest quest1;
    private Quest quest2;
    private Quest quest3;
    private ArrayList<Item> favorites;
    private ArrayList<String> responses;
    private ArrayList<Item> possibleGifts;
    private int TimeUntilQuest3;
    private Point coordinates;
    private boolean menuOpen  = false;
    private Texture NpcTexture;
    public NPC(String name , String job , Quest quest1 , Quest quest2 , Quest quest3 ,ArrayList<Item> favorites
    ,ArrayList<String> responses , ArrayList<Item> possibleGifts , int TimeUntilQuest3 ,Point coordinates,Texture NpcTexture) {
        this.name = name;
        this.job = job;
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;
        this.NpcTexture = NpcTexture;
        this.favorites = favorites;
        this.responses = responses;
        this.possibleGifts = possibleGifts;
        this.TimeUntilQuest3 = TimeUntilQuest3;
        this.coordinates = coordinates;
        friendship = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers()) {
            friendship.put(player, 0);
        }
        hasBeenGiftedTo  = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers())
            hasBeenGiftedTo.put(player, false);
        hasBeenTalkedTo  = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers())
            hasBeenTalkedTo.put(player, false);
    }

    public void addFriendship(int amount , Player player) {
        if(hasBeenTalkedTo.get(player) && amount==20)
           return;
        if(hasBeenGiftedTo.get(player) && (amount == 50 || amount == 200 ))
            return;
        int friendshipamount = friendship.get(player);
        friendshipamount+=amount;
        if(friendshipamount>799)
            friendshipamount=799;
        friendship.put(player, friendshipamount);
        hasBeenTalkedTo.put(player, true);
    }
    public void showMenu() {
        try {
            GameMenuController controller = new GameMenuController();
            Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
            int friendshipLevel = friendship.getOrDefault(currentPlayer, 0);
            String dialogMessage = "Hello, I'm " + name + "\n I'm a " + job + "!\nFriendship level: " + getFriendship().get(currentPlayer) + "\nWhat would you like to do?";
            String giftOption = hasBeenGiftedTo.getOrDefault(currentPlayer, false) ?
                    "You've already given a gift today!" :
                    "Give a gift to " + name;

            // Create gift button action
            Runnable giftAction = () -> {
                InventoryDialog.show(()->{
                    controller.GiveGiftToNPC(this,currentPlayer.getItemsFroAction());
                });
            };

            DialogUtils.openDialog(
                    GameAssetManager.getGameAssetManager().getSkin(),
                    dialogStage,
                    name,
                    dialogMessage,
                    "NPC/" + name.toLowerCase() + ".png",
                    700, 400,
                    (Gdx.graphics.getWidth() - 700) / 2f,
                    Gdx.graphics.getHeight() * 0.7f,
                    new DialogUtils.DialogButton(giftOption, "gift", giftAction),
                    new DialogUtils.DialogButton("Quest 1", "quest1", () -> {
                        String result = controller.FinishQuest(quest1.getQuestID(),this).getData();
                        DialogUtils.openDialog(
                                GameAssetManager.getGameAssetManager().getSkin(),
                                dialogStage,
                                "Quest 1 Result",
                                result,
                                null,
                                500, 300,
                                (Gdx.graphics.getWidth() - 500) / 2f,
                                Gdx.graphics.getHeight() * 0.5f,
                                new DialogUtils.DialogButton("OK", "ok", () -> {})
                        );
                    }),
                    new DialogUtils.DialogButton("Quest 2", "quest2", () -> {
                        String result = controller.FinishQuest(quest2.getQuestID(), this).getData();
                        DialogUtils.openDialog(
                                GameAssetManager.getGameAssetManager().getSkin(),
                                dialogStage,
                                "Quest 2 Result",
                                result,
                                null,
                                500, 300,
                                (Gdx.graphics.getWidth() - 500) / 2f,
                                Gdx.graphics.getHeight() * 0.5f,
                                new DialogUtils.DialogButton("OK", "ok", () -> {})
                        );
                    }),
                    new DialogUtils.DialogButton("Quest 3", "quest3", () -> {
                        String result = controller.FinishQuest(quest3.getQuestID(),this).getData();
                        DialogUtils.openDialog(
                                GameAssetManager.getGameAssetManager().getSkin(),
                                dialogStage,
                                "Quest 3 Result",
                                result,
                                null,
                                500, 300,
                                (Gdx.graphics.getWidth() - 500) / 2f,
                                Gdx.graphics.getHeight() * 0.5f,
                                new DialogUtils.DialogButton("OK", "ok", () -> {})
                        );
                    }),
                    new DialogUtils.DialogButton("Cancel", "cancel", () -> {})
            );

            menuOpen = true;
            Gdx.app.log("NPC Menu", "Showing menu for NPC: " + name);
        } catch (Exception e) {
            Gdx.app.error("NPC Menu", "Error showing NPC menu: " + e.getMessage());
        }
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public Quest getQuest1() {
        return quest1;
    }

    public void setQuest1(Quest quest1) {
        this.quest1 = quest1;
    }

    public Quest getQuest2() {
        return quest2;
    }

    public void setQuest2(Quest quest2) {
        this.quest2 = quest2;
    }

    public Quest getQuest3() {
        return quest3;
    }

    public void setQuest3(Quest quest3) {
        this.quest3 = quest3;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public Map<Player, Integer> getFriendship() {
        return friendship;
    }

    public void setHasBeenTalkedTo(Map<Player, Boolean> hasBeenTalkedTo) {
        this.hasBeenTalkedTo = hasBeenTalkedTo;
    }

    public void setHasBeenGiftedTo(Map<Player, Boolean> hasBeenGiftedTo) {
        this.hasBeenGiftedTo = hasBeenGiftedTo;
    }

    public Map<Player, Boolean> getHasBeenTalkedTo() {
        return hasBeenTalkedTo;
    }

    public Map<Player, Boolean> getHasBeenGiftedTo() {
        return hasBeenGiftedTo;
    }

    public void setPossibleGifts(ArrayList<Item> possibleGifts) {
        this.possibleGifts = possibleGifts;
    }

    public ArrayList<Item> getPossibleGifts() {
        return possibleGifts;
    }
    public String getName() {
        return name;
    }
}
