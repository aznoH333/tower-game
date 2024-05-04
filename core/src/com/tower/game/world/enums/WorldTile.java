package com.tower.game.world.enums;

public enum WorldTile {
    NONE(true),
    FLOOR(false),
    WALL_U(true),
    WALL_R(true),
    WALL_D(true),
    WALL_L(true),
    WALL_RU(true),
    WALL_RD(true),
    WALL_LD(true),
    WALL_LU(true),
    WALL_RU_C(true),
    WALL_RD_C(true),
    WALL_LD_C(true),
    WALL_LU_C(true),
    DECORATION_U(true),
    DECORATION_R(true),
    DECORATION_D(true),
    DECORATION_L(true),
    DECORATION_RU(true),
    DECORATION_RD(true),
    DECORATION_LD(true),
    DECORATION_LU(true),
    DECORATION_RU_C(true),
    DECORATION_RD_C(true),
    DECORATION_LD_C(true),
    DECORATION_LU_C(true);




    public final boolean isWall;

    WorldTile(boolean isWall){
        this.isWall = isWall;

    }
}
