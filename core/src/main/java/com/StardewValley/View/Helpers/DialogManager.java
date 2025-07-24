package com.StardewValley.View.Helpers;

import com.StardewValley.model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;

public class DialogManager {

    private Stage stage;
    private Dialog mapDialog;
    private boolean mapDialogOpen = false;
    private Texture mapTexture;

    public DialogManager() {
        stage = new Stage();
        mapTexture = new Texture(Gdx.files.internal("map.png"));
    }

    public void showMapDialog() {
        if (mapDialogOpen) return;

        mapDialog = new Dialog("Map", GameAssetManager.getGameAssetManager().getSkin()) {
            protected void result(Object object) {
                mapDialogOpen = false;
            }
        };

        Image mapImage = new Image(mapTexture);
        mapDialog.getContentTable().add(mapImage).size(600, 400);
        mapDialog.button("Close (N)", true);
        mapDialog.show(stage);

        mapDialogOpen = true;
    }

    public void closeMapDialog() {
        if (mapDialogOpen) {
            mapDialog.hide();
            mapDialogOpen = false;
        }
    }

    public boolean isMapDialogOpen() {
        return mapDialogOpen;
    }

    public void render() {
        if (mapDialogOpen) {
            stage.act();
            stage.draw();
        }
    }

    public void dispose() {
        mapTexture.dispose();
        stage.dispose();
    }
}

