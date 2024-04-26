package com.tower.game.drawing;

public enum WorldViewportType {

    GAME(3),
    HUD_LEFT(2),
    HUD_RIGHT(0);


    public final int index;
    WorldViewportType(int index){
        this.index = index;
    }
}
