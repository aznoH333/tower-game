package com.tower.game.drawing;

public enum DrawingLayers {
    FLOOR(0),
    WALLS(1),
    WORLD_OBJECTS(2),
    OBJECTS(3);


    public final int index;
    DrawingLayers(int index){
        this.index = index;
    }

}
