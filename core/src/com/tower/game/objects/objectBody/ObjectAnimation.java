package com.tower.game.objects.objectBody;

import com.tower.game.objects.entites.AnimationType;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.UniversalTimer;
import com.tower.game.world.enums.WorldDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ObjectAnimation {
    private final ArrayList<String> frames;
    private final UniversalTimer timer;
    private final AnimationType type;
    private final float xOffset;
    private final float yOffset;
    private final int defaultDuration;



    public ObjectAnimation(String[] frames, int defaultDuration, AnimationType type, float xOffset, float yOffset){
        this.frames = new ArrayList<String>(Arrays.asList(frames));
        timer = new UniversalTimer(defaultDuration);
        this.type = type;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.defaultDuration = defaultDuration;
    }

    public boolean isAnimationDone(){
        return timer.isReady() && type != AnimationType.LOOP;
    }

    public String getCurrentSprite(){
        return frames.get((int)Math.floor(timer.getAsPercentage() * (frames.size() - 1)));
    }

    public void update(){
        timer.progress();
        if (type == AnimationType.LOOP && timer.isReady()) timer.reset();
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void startAnimation(){
        startAnimation(defaultDuration);
    }

    public void startAnimation(int duration){
        timer.setMaxValue(duration);
        timer.reset();
        DebugUtils.debugMessage("started animation" + timer.getValue(), true);

    }

    public AnimationType getType() {
        return type;
    }
}
