package com.StardewValley.View.Helpers;

import com.StardewValley.model.App;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

import java.util.ArrayList;

public class Toolbar {

    private static final int TOOLBAR_SIZE = 12;
    private int selectedToolIndex = 0;
    private ArrayList<Texture> toolTextures;
    private Texture emptySlotTexture;
    private Stack[] toolbarSlots;
    private Stage stage;

    public Toolbar() {
        stage = new Stage();
        toolTextures = new ArrayList<>();
        emptySlotTexture = new Texture("bar.png");

        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            toolTextures.add(null);
        }
        loadPlayerTools();

        toolbarSlots = new Stack[TOOLBAR_SIZE];
        setupToolbarUI();
    }

    private void loadPlayerTools() {
        for (com.StardewValley.model.things.Item item : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if (item.getImage() != null) {
                toolTextures.add(item.getImage());
            }
        }
        while (toolTextures.size() < TOOLBAR_SIZE) {
            toolTextures.add(null);
        }
    }

    private void setupToolbarUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().left();

        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Stack stack = new Stack();
            Texture tex = toolTextures.get(i) == null ? emptySlotTexture : toolTextures.get(i);
            Image img = new Image(tex);
            Container<Image> container = new Container<>(img);
            container.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
            stack.add(container);
            toolbarSlots[i] = stack;
            table.add(stack).size(50, 50).pad(2);
        }

        stage.addActor(table);
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            int originalIndex = selectedToolIndex;
            do {
                selectedToolIndex = (selectedToolIndex + 1) % TOOLBAR_SIZE;
            } while ((toolTextures.get(selectedToolIndex) == null) && selectedToolIndex != originalIndex);
            updateHighlight();
        }

        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1 + i)) {
                if (toolTextures.get(i) != null) {
                    selectedToolIndex = i;
                    updateHighlight();
                }
            }
        }
    }

    private void updateHighlight() {
        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Container<?> cont = (Container<?>) toolbarSlots[i].getChild(0);
            cont.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
        }
    }

    public void render(SpriteBatch batch) {
        stage.draw();
    }

    public void dispose() {
        emptySlotTexture.dispose();
        stage.dispose();
    }
}

