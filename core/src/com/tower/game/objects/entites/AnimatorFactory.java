package com.tower.game.objects.entites;

import com.tower.game.objects.objectBody.ObjectAnimation;
import com.tower.game.objects.objectBody.ObjectAnimationType;
import com.tower.game.objects.objectBody.ObjectAnimator;
import com.tower.game.world.enums.WorldDirection;

public class AnimatorFactory {
    public static ObjectAnimator getAnimatorForPlayer(){
        ObjectAnimator out = new ObjectAnimator();
        // idle animation
        {
            String[] up = {"player_back_1"};
            out.addAnimation(ObjectAnimationType.IDLE, WorldDirection.UP, new ObjectAnimation(up, 10, AnimationType.HOLD_LAST_FRAME, -1.5f, 2));
            String[] down = {"player_front_1"};
            out.addAnimation(ObjectAnimationType.IDLE, WorldDirection.DOWN, new ObjectAnimation(down, 10, AnimationType.HOLD_LAST_FRAME, -1.5f, 2));
            String[] left = {"player_side_4"};
            out.addAnimation(ObjectAnimationType.IDLE, WorldDirection.LEFT, new ObjectAnimation(left, 10, AnimationType.HOLD_LAST_FRAME, -2.5f, 2));
            String[] right = {"player_side_1"};
            out.addAnimation(ObjectAnimationType.IDLE, WorldDirection.RIGHT, new ObjectAnimation(right, 10, AnimationType.HOLD_LAST_FRAME, -0.5f, 2));
        }
        // walk 1
        {
            String[] up = {"player_back_1", "player_back_2", "player_back_3"};
            out.addAnimation(ObjectAnimationType.MOVE_1, WorldDirection.UP, new ObjectAnimation(up, 10, AnimationType.HOLD_LAST_FRAME, -1.5f, 2));
            String[] down = {"player_front_1", "player_front_2", "player_front_3"};
            out.addAnimation(ObjectAnimationType.MOVE_1, WorldDirection.DOWN, new ObjectAnimation(down, 10, AnimationType.HOLD_LAST_FRAME, -1.5f, 2));
            String[] left = {"player_side_4", "player_side_5", "player_side_6"};
            out.addAnimation(ObjectAnimationType.MOVE_1, WorldDirection.LEFT, new ObjectAnimation(left, 10, AnimationType.HOLD_LAST_FRAME, -2.5f, 2));
            String[] right = {"player_side_1", "player_side_2", "player_side_3"};
            out.addAnimation(ObjectAnimationType.MOVE_1, WorldDirection.RIGHT, new ObjectAnimation(right, 10, AnimationType.HOLD_LAST_FRAME, -0.5f, 2));
        }
        // walk 2
        {
            String[] up = {"player_back_3", "player_back_2", "player_back_1"};
            out.addAnimation(ObjectAnimationType.MOVE_2, WorldDirection.UP, new ObjectAnimation(up, 10, AnimationType.HOLD_LAST_FRAME, -1.5f, 2));
            String[] down = {"player_front_3", "player_front_2", "player_front_1"};
            out.addAnimation(ObjectAnimationType.MOVE_2, WorldDirection.DOWN, new ObjectAnimation(down, 10, AnimationType.HOLD_LAST_FRAME, -1.5f, 2));
            String[] left = {"player_side_6", "player_side_5", "player_side_4"};
            out.addAnimation(ObjectAnimationType.MOVE_2, WorldDirection.LEFT, new ObjectAnimation(left, 10, AnimationType.HOLD_LAST_FRAME, -2.5f, 2));
            String[] right = {"player_side_3", "player_side_2", "player_side_1"};
            out.addAnimation(ObjectAnimationType.MOVE_2, WorldDirection.RIGHT, new ObjectAnimation(right, 10, AnimationType.HOLD_LAST_FRAME, -0.5f, 2));
        }


        return out;
    }
}
