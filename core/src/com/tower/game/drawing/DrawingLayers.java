package com.tower.game.drawing;

public enum DrawingLayers {
    FLOOR(0),
    WALLS(1);


    public final int index;
    DrawingLayers(int index){
        this.index = index;
    }

}
