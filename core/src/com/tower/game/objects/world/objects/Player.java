package com.tower.game.objects.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.tower.game.objects.entites.AnimatorFactory;
import com.tower.game.objects.entites.Entity;
import com.tower.game.objects.objectBody.MoveAnimationFunction;
import com.tower.game.objects.objectBody.ObjectBody;
import com.tower.game.objects.world.WorldObject;
import com.tower.game.utils.DebugUtils;
import com.tower.game.world.enums.WorldDirection;

public class Player extends WorldObject {

    private final ObjectBody playerBody;

    public Player(Vector2 position, WorldDirection direction){
        DebugUtils.debugMessage("spawned at : " + position.x + ", " + position.y);
        this.playerBody = new ObjectBody(position, direction, AnimatorFactory.getAnimatorForPlayer()).allowInteraction().setMoveSpeed(10).changeMovementAnimationFunction(MoveAnimationFunction.SQRT);
    }
    @Override
    public void update(float cameraOffsetX, float cameraOffsetY) {
        playerBody.update();

        // movement
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            playerBody.move(WorldDirection.UP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            playerBody.move(WorldDirection.DOWN);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            playerBody.move(WorldDirection.LEFT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            playerBody.move(WorldDirection.RIGHT);
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

    @Override
    public boolean canBeInteracted() {
        return false;
    }
}
