package com.tower.game.objects;

public abstract class WorldObject {
    private ObjectType type;


    public abstract void update();
    public ObjectType getType(){
        return type;
    }
}
