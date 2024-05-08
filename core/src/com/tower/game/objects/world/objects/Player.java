package com.tower.game.objects.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.tower.game.objects.entites.AnimatorFactory;
import com.tower.game.objects.entites.Entity;
import com.tower.game.objects.objectBody.ObjectBody;
import com.tower.game.objects.world.WorldObject;
import com.tower.game.utils.DebugUtils;
import com.tower.game.world.enums.WorldDirection;

public class Player extends WorldObject {

    private final ObjectBody playerBody;

    public Player(Vector2 position, WorldDirection direction){
        DebugUtils.debugMessage("spawned at : " + position.x + ", " + position.y);
        this.playerBody = new ObjectBody(position, direction, AnimatorFactory.getAnimatorForPlayer());
    }
    @Override
    public void update(float cameraOffsetX, float cameraOffsetY) {
        playerBody.update();

        // movement
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            playerBody.move(WorldDirection.UP, 1, 15);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            playerBody.move(WorldDirection.DOWN, 1, 15);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            playerBody.move(WorldDirection.LEFT, 1, 15);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            playerBody.move(WorldDirection.RIGHT, 1, 15);
        }
    }

    @Override
    public void onInteract(Entity other) {

    }

    @Override
    public void onRoomEnter() {
        // spawn body
    }

    @Override
    public void onFirstRoomEntry() {

    }

    @Override
    public boolean canExist() {
        return true;
    }
}
