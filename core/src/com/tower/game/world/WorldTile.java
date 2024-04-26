package com.tower.game.world;

public enum WorldTile {
    FLOOR(false),
    WALL(true);

    public final boolean isWall;

    WorldTile(boolean isWall){
        this.isWall = isWall;
    }
}
