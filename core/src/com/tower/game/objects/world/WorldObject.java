package com.tower.game.objects.world;

import com.tower.game.objects.entites.Entity;

/**
 * World objects are static entities with simplified world interaction
 * Example uses:
 * Doors, Enemy spawners, Dart traps, Item pedestals, buttons and other stuff
 */
public abstract class WorldObject {
    protected int x;
    protected int y;
    private ObjectType type;
    public ObjectType getType(){
        return type;
    }
    public abstract void update(float cameraOffsetX, float cameraOffsetY);
    public abstract void onInteract(Entity other);
    public abstract void onRoomEnter();
    public abstract void onFirstRoomEntry();
    public int getX(){
        return x;
    }
    public int getY() {
        return y;
    }
}
