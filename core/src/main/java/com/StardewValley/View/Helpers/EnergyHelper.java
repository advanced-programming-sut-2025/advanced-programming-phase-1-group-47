package com.StardewValley.View.Helpers;

import com.StardewValley.model.App;
import com.StardewValley.model.Energy;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnergyHelper {
    private Texture energyBarTexture;
    private Pixmap energyBarPixmap;
    private OrthographicCamera camera = App.camera;

    public EnergyHelper() {
        initEnergyBar();
    }

    public void initEnergyBar() {
        updateEnergyBar();
    }

    public Texture updateEnergyBar() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (player == null || player.getEnergy() == null) {
            return null;
        }

        float energyPercent;
        try {
            energyPercent = (float) player.getEnergy().getCurrentEnergy() / player.getEnergy().getEnergyCap();
            energyPercent = Math.max(0, Math.min(1, energyPercent));
        } catch (Exception e) {
            energyPercent = 1.0f;
            Gdx.app.error("EnergyHelper", "Error calculating energy percent", e);
        }

        // Get the empty energy bar texture
        Texture emptyBar = GameAssetManager.ENERGY_BAR_EMPTY;
        int width = emptyBar.getWidth();
        int height = emptyBar.getHeight();
        int fillHeight = (int)(height * energyPercent);

        // Dispose previous resources
        if (energyBarPixmap != null) {
            energyBarPixmap.dispose();
        }
        if (energyBarTexture != null) {
            energyBarTexture.dispose();
        }

        // Create new Pixmap from the empty bar texture
        TextureRegion emptyRegion = new TextureRegion(emptyBar);
        Pixmap emptyPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        emptyBar.getTextureData().prepare();
        emptyPixmap.drawPixmap(emptyBar.getTextureData().consumePixmap(),
                0, 0, width, height,
                0, 0, width, height);

// Define padding inside the frame
        int paddingLeft = 6;
        int paddingRight = 0;
        int paddingTop = 6;
        int paddingBottom = 40;

        int fillWidth = width - paddingLeft - paddingRight - 7;
        int fillMaxHeight = height - paddingTop - paddingBottom;
        fillHeight = Math.round(fillMaxHeight * energyPercent);
        int fillY = paddingBottom + (fillMaxHeight - fillHeight); // Start from bottom going up

// Create fill pixmap
        Pixmap fillPixmap = new Pixmap(fillWidth, fillMaxHeight, Pixmap.Format.RGBA8888);
        fillPixmap.setColor(Color.GREEN);
        fillPixmap.fillRectangle(0, fillMaxHeight - fillHeight, fillWidth, fillHeight); // draw from bottom up

// Combine
        energyBarPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        energyBarPixmap.drawPixmap(emptyPixmap, 0, 0);
        energyBarPixmap.drawPixmap(fillPixmap, paddingLeft, paddingBottom);

// Clean up
        emptyPixmap.dispose();
        fillPixmap.dispose();

        // Create new Texture
        energyBarTexture = new Texture(energyBarPixmap);

        // Clean up
        emptyPixmap.dispose();
        fillPixmap.dispose();

        return energyBarTexture;
    }

    public void dispose() {
        if (energyBarTexture != null) energyBarTexture.dispose();
        if (energyBarPixmap != null) energyBarPixmap.dispose();
    }
}