package com.tower.game.drawing;

public enum FlipDirection {
    NONE(false, false),
    VERTICAL(true, false),
    HORIZONTAL(false, true),
    BOTH(true, true);

    public final boolean flipX;
    public final boolean flipY;

    FlipDirection(boolean flipX, boolean flipY){
        this.flipX = flipX;
        this.flipY = flipY;
    }
}
