package com.tower.game.drawing;

import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldViewport {
    private final HashMap<DrawingLayers, HashMap<Integer, ArrayList<RenderData>>> drawingQueue;
    private final Viewport viewport;

    public WorldViewport(Viewport viewport) {
        this.drawingQueue = new HashMap<>();
        this.viewport = viewport;
    }

    public HashMap<DrawingLayers, HashMap<Integer, ArrayList<RenderData>>> getDrawingQueue() {
        return drawingQueue;
    }

    public Viewport getViewport() {
        return viewport;
    }


    public void setupDrawingQueue(){
        for (DrawingLayers layer : DrawingLayers.values()){
            drawingQueue.put(layer, new HashMap<>());
        }
    }
}
