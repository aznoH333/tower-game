package com.tower.game.objects.objectBody;

import com.tower.game.utils.InterpolationFunctions;
import com.tower.game.utils.Utils;

public enum MoveAnimationFunction {
    SMOOTH_STEP(InterpolationFunctions::smoothStep),
    SQRT(InterpolationFunctions::sqrt),
    LINEAR(InterpolationFunctions::linear);

    public final TransitionAnimation animation;

    MoveAnimationFunction(TransitionAnimation animation){
        this.animation = animation;
    }

}
