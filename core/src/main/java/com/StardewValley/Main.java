package com.StardewValley;

import com.StardewValley.View.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.StardewValley.controllers.InitPageController;
import com.StardewValley.DataBase.DataBaseInit;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.View.InitPageView;

public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private ShaderProgram grayscaleShader;

    @Override
    public void create() {

        main = this;
        batch = new SpriteBatch();
        
        // Initialize database
        try {
            DataBaseInit.init();
        } catch (Exception e) {
            Gdx.app.error("Main", "Failed to initialize database: " + e.toString());
        }


        try{
            setScreen(new InitPageView(new InitPageController(), GameAssetManager.getGameAssetManager().getSkin()));
        }

        catch (Exception e){
            Gdx.app.error("Main", "Failed to load main menu \n" + e.toString());
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
            batch = null;
        }
        if (grayscaleShader != null) {
            grayscaleShader.dispose();
            grayscaleShader = null;
        }
    }

    public static Main getMain() {
        return main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public ShaderProgram getGrayscaleShader() {
        return grayscaleShader;
    }

}
