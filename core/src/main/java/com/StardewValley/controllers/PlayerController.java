package com.StardewValley.controllers;

import com.StardewValley.model.enums.TileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PlayerController {

    private Vector2 position;
    private float speed = 350f;
    private float stateTime;
    private TextureAtlas playerAtlas;
    private Animation<TextureRegion>[] animations;
    private int currentDirection = 2; // 0=down,1=right,2=up,3=left

    public PlayerController() {
        position = new Vector2(100, 100);
        stateTime = 0f;
        playerAtlas = new TextureAtlas(Gdx.files.internal("game/character/sprites_player.atlas"));

        animations = new Animation[4];
        for (int dir = 0; dir < 4; dir++) {
            Array<TextureRegion> frames = new Array<>();
            for (int frame = 0; frame < 4; frame++) {
                frames.add(playerAtlas.findRegion("player_" + (13 - dir) + "_" + frame));
            }
            animations[dir] = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        }
    }

    public void handleInput(float delta) {
        boolean moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * delta;
            currentDirection = 3;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed * delta;
            currentDirection = 1;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * delta;
            currentDirection = 2;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * delta;
            currentDirection = 0;
            moving = true;
        }

        if (!moving) {
            stateTime = 0f;
        }
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = animations[currentDirection].getKeyFrame(stateTime);
        batch.draw(frame, position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose() {
        playerAtlas.dispose();
    }
}
