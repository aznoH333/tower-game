package com.tower.game.world;

public enum WorldTile {
    NONE(false, 0, 0),
    FLOOR(false, 0, 0),
    WALL_DOWN(true, 0,0),
    WALL_RIGHT_DOWN(true, 0, 0),
    WALL_RIGHT(true, 0, 0),
    WALL_RIGHT_UP(true, 0, 6),
    WALL_UP(true, 0, 6),
    WALL_LEFT_UP(true,-6 ,6),
    WALL_LEFT(true, -6, 0),
    WALL_LEFT_DOWN(true,-6,0);



    public final boolean isWall;
    public final int xOffset;
    public final int yOffset;

    WorldTile(boolean isWall, int xOffset, int yOffset){
        this.isWall = isWall;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
