package com.tower.game.objects.objectBody;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.objects.entites.Entity;
import com.tower.game.objects.world.WorldObjectManager;
import com.tower.game.objects.world.WorldSearchResult;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.GameConstants;
import com.tower.game.utils.UniversalTimer;
import com.tower.game.utils.Utils;
import com.tower.game.world.World;
import com.tower.game.world.enums.WorldDirection;

public class ObjectBody extends Entity {

    private WorldDirection direction;
    private final ObjectAnimator animator;
    private final UniversalTimer moveTimer = new UniversalTimer(10); // TODO : this
    private Vector2 moveStartPosition;
    private Vector2 moveTargetPosition;
    private boolean isMoving = false;
    private final boolean alternateMoveAnimations = true;
    private int moveCounter = 0;
    public boolean canInteract = false;
    private int moveSpeed = 10;
    private MoveAnimationFunction moveAnimationFunction = MoveAnimationFunction.SMOOTH_STEP;
    public ObjectBody(Vector2 position, WorldDirection direction, ObjectAnimator animator){
        this.position = position;
        this.direction = direction;
        this.animator = animator;
        this.size = new Vector2(16.0f, 16.0f);
        animator.setCurrentDirection(direction);
    }

    public void update(){
        draw();
        updateMovement();
    }

    private void draw(){
        animator.update(position);
    }

    public void move(WorldDirection direction, int tileAmount, int timePerTile){
        if (!moveTimer.isReady()){
            return;
        }
        int tileX = Utils.convertWorldToTile(position.x);
        int tileY = Utils.convertWorldToTile(position.y);
        WorldSearchResult result = World.getInstance().getMaxPossibleMoveDistance(tileX, tileY, direction);
        int targetDistance = Math.min(result.maxAvailableDistance, tileAmount);

        if (targetDistance <= 0){
            // interact with objects
            if (result.isObject && canInteract){
                DebugUtils.debugMessage("interacting with object", true);
                WorldObjectManager.getInstance().interactWithObject(this,tileX + (direction.x), tileY + (direction.y));
            }

            return;
        }
        moveTimer.setMaxValue(timePerTile * targetDistance);

        this.direction = direction;
        animator.setCurrentDirection(this.direction);

        // play animation
        {
            ObjectAnimationType type;
            if (alternateMoveAnimations && moveCounter % 2 == 0){
                type = ObjectAnimationType.MOVE_2;
            }else{
                type = ObjectAnimationType.MOVE_1;
            }
            animator.playAnimation(type, timePerTile * targetDistance);
        }

        moveTimer.reset();
        moveStartPosition = new Vector2(position.x, position.y);
        moveTargetPosition = new Vector2(position.x + (GameConstants.TILE_SIZE * direction.x * targetDistance), position.y + (GameConstants.TILE_SIZE * direction.y * targetDistance));
        isMoving = true;
        moveCounter++;
    }

    public void move(WorldDirection direction){
        move(direction, 1, moveSpeed);
    }

    private void updateMovement(){
        moveTimer.progress();

        if (!moveTimer.isReady()) { // move animation
            position.x = moveStartPosition.x + (moveAnimationFunction.animation.animation(moveTimer.getAsPercentage()) * (moveTargetPosition.x - moveStartPosition.x));
            position.y = moveStartPosition.y + (moveAnimationFunction.animation.animation(moveTimer.getAsPercentage()) * (moveTargetPosition.y - moveStartPosition.y));

        } else if (isMoving){ // snap to tile
            position = moveTargetPosition;
            isMoving = false;
        }
    }


    // chain setter functions
    public ObjectBody setMoveSpeed(int speed){
        this.moveSpeed = speed;
        return this;
    }

    public ObjectBody allowInteraction(){
        canInteract = true;
        return this;
    }

    public ObjectBody changeMovementAnimationFunction(MoveAnimationFunction function){
        this.moveAnimationFunction = function;
        return this;
    }
}
