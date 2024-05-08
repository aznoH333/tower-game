package com.tower.game.objects.world;

import com.tower.game.objects.entites.Entity;
import com.tower.game.utils.Utils;
import jdk.internal.classfile.impl.Util;

/**
 * World objects are static entities with simplified world interaction
 * Example uses:
 * Doors, Enemy spawners, Dart traps, Item pedestals, buttons and other stuff
 */
public abstract class WorldObject {
    protected int x;
    protected int y;
    protected boolean blocksMovement = false;
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
    public int getInWorldX(){
        return Utils.convertWorldToTile(x);
    }

    public int getInWorldY(){
        return Utils.convertWorldToTile(y);
    }

    /**
     * Should be used to determine whether object should spawn.
     * example: doors cant spawn if they lead to no room
     * enemy spawners have a random chance to not exist
     * @return true = yes, false = no
     */
    public abstract boolean canExist();

    public abstract boolean canBeInteracted();

}
