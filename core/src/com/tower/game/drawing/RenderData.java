package com.tower.game.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class RenderData {

    private final String textureName;
    private final Vector2 position;
    private final FlipDirection flip;
    private final float rotation;
    private final float scale;
    private final Color color;

    public RenderData(String textureName, Vector2 position, FlipDirection flip, float rotation, float scale, Color color) {
        this.textureName = textureName;
        this.position = position;
        this.flip = flip;
        this.rotation = rotation;
        this.scale = scale;
        this.color = color;
    }

    public String getTextureName() {
        return textureName;
    }

    public Vector2 getPosition() {
        return position;
    }

    public FlipDirection getFlip() {
        return flip;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Color getColor() {
        return color;
    }
}
