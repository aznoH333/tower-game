package com.tower.game.drawing;

public enum WorldViewportType {

    GAME(1),
    HUD_LEFT(2),
    HUD_RIGHT(3);


    public final int index;
    WorldViewportType(int index){
        this.index = index;
    }
}
