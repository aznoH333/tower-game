package com.tower.game.objects.entites;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position;
    protected Vector2 size;

    public abstract void update();

    public Vector2 getPosition(){
        return position;
    }

    public Vector2 getSize(){
        return size;
    }
}
