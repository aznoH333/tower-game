package com.tower.game.objects.objectBody;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.utils.GameConstants;
import com.tower.game.utils.UniversalTimer;
import com.tower.game.utils.Utils;
import com.tower.game.world.World;
import com.tower.game.world.enums.WorldDirection;

public class ObjectBody {

    private Vector2 position;
    private WorldDirection direction;
    private final ObjectAnimator animator;
    private final UniversalTimer moveTimer = new UniversalTimer(10); // TODO : this
    private Vector2 moveStartPosition;
    private Vector2 moveTargetPosition;
    private boolean isMoving = false;
    private final boolean alternateMoveAnimations = true;
    private int moveCounter = 0;
    private MoveAnimationFunction moveAnimationFunction = MoveAnimationFunction.LOG;
    public ObjectBody(Vector2 position, WorldDirection direction, ObjectAnimator animator){
        this.position = position;
        this.direction = direction;
        this.animator = animator;
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
        int targetDistance = Math.min(World.getInstance().getMaxPossibleMoveDistance(
                Utils.convertWorldToTile(position.x),
                Utils.convertWorldToTile(position.y), direction), tileAmount);

        if (targetDistance <= 0){
            return;
        }
        moveTimer.setMaxValue(timePerTile * targetDistance);

        this.direction = direction;
        animator.setCurrentDirection(direction);

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
}
