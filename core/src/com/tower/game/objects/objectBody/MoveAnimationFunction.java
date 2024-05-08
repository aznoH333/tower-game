package com.tower.game.objects.objectBody;

import com.tower.game.utils.InterpolationFunctions;
import com.tower.game.utils.Utils;

public enum MoveAnimationFunction {
    SMOOTH_STEP(InterpolationFunctions::smoothStep),
    LOG(InterpolationFunctions::sqrt);

    public final TransitionAnimation animation;

    MoveAnimationFunction(TransitionAnimation animation){
        this.animation = animation;
    }

}
