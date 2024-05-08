package com.tower.game.objects.objectBody;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.utils.DebugUtils;
import com.tower.game.world.enums.WorldDirection;

import java.util.HashMap;

public class ObjectAnimator {

    private final HashMap<ObjectAnimationType, HashMap<WorldDirection, ObjectAnimation>> animations;
    private ObjectAnimationType currentAnimationType;
    private ObjectAnimation currentAnimation = null;


    private WorldDirection currentDirection = WorldDirection.UP;


    public ObjectAnimator(){
        animations = new HashMap<>();
    }

    public void addAnimation(ObjectAnimationType type, WorldDirection direction,ObjectAnimation animation){
        if(!animations.containsKey(type)){
            animations.put(type, new HashMap<>());
        }

        animations.get(type).put(direction, animation);

        if (type == ObjectAnimationType.IDLE){
            playAnimation(ObjectAnimationType.IDLE);
        }
    }


    public void update(Vector2 position){
        currentAnimation.update();
        Drawing.getInstance().drawTexture(currentAnimation.getCurrentSprite(), new Vector2(position.x + currentAnimation.getXOffset(), position.y + currentAnimation.getYOffset()), DrawingLayers.OBJECTS);
    }


    public void playAnimation(ObjectAnimationType type, int animationDuration){
        if (animations.containsKey(type) && animations.get(type).containsKey(currentDirection)){
            currentAnimationType = type;
            currentAnimation = animations.get(type).get(currentDirection);
            currentAnimation.startAnimation(animationDuration);
        }else {
            DebugUtils.fatalCrash("Animation not found " + type.name());
        }
    }

    public void setCurrentDirection(WorldDirection currentDirection) {
        this.currentDirection = currentDirection;
        this.currentAnimation = animations.get(currentAnimationType).get(currentDirection);
    }


    public void playAnimation(ObjectAnimationType type){
        playAnimation(type, 0);
    }


}
