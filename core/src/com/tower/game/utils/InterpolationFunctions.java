package com.tower.game.utils;

public class InterpolationFunctions {
    public static float smoothStep(float value){
        float x = value * value;
        float y = 1 - ((1 - value) * (1 - value));

        return Utils.lerp(x, y, value);
    }

    public static float sqrt(float value){

        return (float) Math.pow(value, 0.5f);
    }
}
