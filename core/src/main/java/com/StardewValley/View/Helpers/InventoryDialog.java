package com.StardewValley.View.Helpers;

import com.StardewValley.View.GameScreen;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.ItemSelection;
import com.StardewValley.model.Player;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import java.util.ArrayList;
import java.util.List;

import static com.StardewValley.View.GameScreen.dialogStage;
import static com.StardewValley.View.GameScreen.isOutOfRealGame;

public class InventoryDialog {
    public static void show(Runnable action) {
        try {
            isOutOfRealGame = true;
            Skin skin = GameAssetManager.getGameAssetManager().getSkin();
            Dialog inventoryDialog = new Dialog("Your Inventory", skin) {
                @Override
                protected void result(Object obj) {
                    if ("ok".equals(obj)) {
                        try {
//                            action.run();
                        }
                        catch (Exception e) {
                            Gdx.app.log("InventoryDialog", e.getMessage());
                        }
                    }
                    isOutOfRealGame = false;
                }
            };

            // Set up font and style for labels
            BitmapFont font = new BitmapFont();
            font.getData().setScale(2f); // Larger font for better visibility
            Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

            // Add instruction label
            inventoryDialog.getContentTable().add(new Label("Your items: (Check items to select)", labelStyle)).pad(10).row();

            // Table for item images, quantities, checkboxes, and input fields
            Table itemTable = new Table();
            itemTable.defaults().pad(5).size(50, 50); // Smaller size for images

            // List to store text fields and checkboxes for processing
            List<TextField> quantityFields = new ArrayList<>();
            List<CheckBox> checkBoxes = new ArrayList<>();

            // Check if inventory is empty
            if (App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().isEmpty()) {
                inventoryDialog.getContentTable().add(new Label("Your inventory is empty!", labelStyle)).pad(10).row();
            } else {
                // Loop through inventory items
                for (Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
                    // Create image for item
                    Image itemImage = new Image(item.getImage());
                    itemImage.setScaling(Scaling.fit);

                    // Create checkbox for selection
                    CheckBox checkBox = new CheckBox("", skin);
                    checkBoxes.add(checkBox);

                    // Create quantity input field
                    TextField quantityField = new TextField("1", skin);
                    quantityField.setMessageText("Qty");
                    quantityFields.add(quantityField);

                    // Add image, checkbox, name, quantity, and input field to table
                    itemTable.add(itemImage).size(50, 50);
                    itemTable.add(checkBox).padLeft(5);
                    itemTable.add(new Label(item.getName(), labelStyle)).padLeft(5).width(200);
                    itemTable.add(new Label("(x" + item.getAmount() + ")", labelStyle)).padBottom(60);
                    itemTable.add(quantityField).width(100).height(60).pad(5).padRight(50); // Larger input field
                    itemTable.row();
                }

                // Wrap item table in a ScrollPane
                ScrollPane scrollPane = new ScrollPane(itemTable, skin);
                scrollPane.setFadeScrollBars(false);
                scrollPane.setScrollbarsVisible(true);
                inventoryDialog.getContentTable().add(scrollPane).maxHeight(300).pad(10).row();
            }

            // Add OK and Cancel buttons
            inventoryDialog.button("OK", "ok").pad(10).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Player player = App.getCurrentGame().getCurrentPlayer();
                    List<ItemSelection> selectedItems = new ArrayList<>();
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        if (checkBoxes.get(i).isChecked()) {
                            Item item = App.getCurrentGame().getCurrentPlayer().getInvetory().getItems().get(i);
                            try {
                                int quantity = Integer.parseInt(quantityFields.get(i).getText());
                                if (quantity <= 0 || quantity > item.getAmount()) {
                                    DialogUtils.openDialog(
                                            skin,
                                            dialogStage,
                                            "Invalid Quantity",
                                            "Please enter a valid quantity (1 to " + item.getAmount() + ") for " + item.getName() + ".",
                                            null,
                                            500, 300,
                                            (Gdx.graphics.getWidth() - 500) / 2f,
                                            Gdx.graphics.getHeight() * 0.5f,
                                            new DialogUtils.DialogButton("OK", "ok", () -> {})
                                    );
                                    return;
                                }
                                selectedItems.add(new ItemSelection(item, quantity));
                            } catch (NumberFormatException e) {
                                DialogUtils.openDialog(
                                        skin,
                                        dialogStage,
                                        "Invalid Input",
                                        "Please enter a valid number for " + item.getName() + ".",
                                        null,
                                        500, 300,
                                        (Gdx.graphics.getWidth() - 500) / 2f,
                                        Gdx.graphics.getHeight() * 0.5f,
                                        new DialogUtils.DialogButton("OK", "ok", () -> {})
                                );
                                return;
                            }
                        }
                    }
                    player.setSelectedItems(selectedItems);
                }
            });

            inventoryDialog.button("Cancel", "cancel").pad(10).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
//                    inventoryDialog.hide();
                }
            });

            // Set dialog size and position
            inventoryDialog.setSize(1200, 800); // Larger dialog
            inventoryDialog.setPosition((Gdx.graphics.getWidth() - 1200) / 2f, Gdx.graphics.getHeight() * 0.5f);
            inventoryDialog.show(dialogStage);

            Gdx.app.log("InventoryDialog", "Opened inventory dialog");
        } catch (Exception e) {
            Gdx.app.error("InventoryDialog", "Error opening inventory dialog: " + e.getMessage());
        }
    }
}