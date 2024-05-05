package com.tower.game.objects.entites;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.GameConstants;
import com.tower.game.utils.UniversalTimer;
import com.tower.game.utils.Utils;
import com.tower.game.world.World;
import com.tower.game.world.enums.WorldDirection;

public class ObjectBody {

    private Vector2 position;
    private WorldDirection direction;
    private final String sprite = "player"; // TODO : this
    private final UniversalTimer moveTimer = new UniversalTimer(10); // TODO : this
    private Vector2 moveStartPosition;
    private Vector2 moveTargetPosition;
    private boolean isMoving = false;
    public ObjectBody(Vector2 position, WorldDirection direction){
        this.position = position;
        this.direction = direction;
    }

    public void update(){
        draw();
        updateMovement();
    }

    private void draw(){
        Drawing.getInstance().drawTexture(sprite, position, DrawingLayers.OBJECTS);
    }

    public void move(WorldDirection direction, int tileAmount, int timePerTile){
        if (!moveTimer.isReady()){
            return;
        }
        int targetDistance = Math.min(World.getInstance().getMaxPossibleMoveDistance(
                Utils.convertWorldToTile(position.x),
                Utils.convertWorldToTile(position.y), direction), tileAmount);

        DebugUtils.debugMessage(targetDistance + "");
        if (targetDistance <= 0){
            return;
        }
        moveTimer.setMaxValue(timePerTile * targetDistance);
        DebugUtils.debugMessage(moveTimer.getMaxValue() + "");

        this.direction = direction;
        moveTimer.reset();
        moveStartPosition = new Vector2(position.x, position.y);
        moveTargetPosition = new Vector2(position.x + (GameConstants.TILE_SIZE * direction.x * targetDistance), position.y + (GameConstants.TILE_SIZE * direction.y * targetDistance));
        isMoving = true;
    }

    private void updateMovement(){
        moveTimer.progress();

        if (!moveTimer.isReady()) { // move animation
            position.x = moveStartPosition.x + (Utils.smoothStep(moveTimer.getAsPercentage()) * (moveTargetPosition.x - moveStartPosition.x));
            position.y = moveStartPosition.y + (Utils.smoothStep(moveTimer.getAsPercentage()) * (moveTargetPosition.y - moveStartPosition.y));

        } else if (isMoving){ // snap to tile
            position = moveTargetPosition;
            isMoving = false;
        }
    }
}
