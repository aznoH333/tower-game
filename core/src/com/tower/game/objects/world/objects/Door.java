package com.tower.game.objects.world.objects;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.objects.entites.Entity;
import com.tower.game.objects.world.WorldObject;
import com.tower.game.objects.world.WorldObjectManager;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.GameConstants;
import com.tower.game.world.World;
import com.tower.game.world.enums.WorldDirection;

public class Door extends WorldObject {

    private final WorldDirection direction;
    private final String sprite;
    private final int offsetX;
    private final int offsetY;
    public Door(int x, int y, WorldDirection direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        // set sprite
        switch (direction){
            default:
            case UP:    sprite = "door_up";     offsetX = -6;   offsetY = 0; break;
            case DOWN:  sprite = "door_down";   offsetX = -6;   offsetY = -6; break;
            case LEFT:  sprite = "door_left";   offsetX = -6;   offsetY = -6; break;
            case RIGHT: sprite = "door_right";  offsetX = 0;    offsetY = -6; break;
        }

    }
    @Override
    public void update(float cameraOffsetX, float cameraOffsetY) {
        Drawing.getInstance().drawTexture(sprite, new Vector2(x + offsetX - cameraOffsetX, y + offsetY - cameraOffsetY), DrawingLayers.WORLD_OBJECTS);

    }

    @Override
    public void onInteract(Entity other) {

    }

    @Override
    public void onRoomEnter() {
        WorldDirection worldDirection = World.getInstance().getCurrentFloor().getLastMoveDirection();

        if (direction.isOppositeTo(worldDirection)){
            WorldObjectManager.getInstance().setPlayerSpawnLocation(new Vector2(x + (worldDirection.x * GameConstants.TILE_SIZE), y + (worldDirection.y * GameConstants.TILE_SIZE)));
        }
    }

    @Override
    public void onFirstRoomEntry() {

    }

    @Override
    public boolean canExist() {
        return World.getInstance().getCurrentFloor().checkIfRoomExists(direction.x, direction.y);
    }
}
