package com.tower.game.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tower.game.utils.Utils;
import jdk.internal.classfile.impl.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Drawing {
    // Singleton
    private static Drawing instance;
    public static Drawing getInstance(){
        if (instance == null) instance = new Drawing();
        return instance;
    }

    // rendering variables
    private final SpriteBatch spriteBatch;
    private final HashMap<String, Texture> textureMap;
    private final HashMap<DrawingLayers, HashMap<Integer, ArrayList<RenderData>>> drawingQueue;



    private Color backgroundColor = new Color(0.090196078f,0.105882353f,0.101960784f,1.0f);


    public Drawing(){
        spriteBatch = new SpriteBatch();
        textureMap = new HashMap<>();
        drawingQueue = new HashMap<>();
        loadTextures();
        setupDrawingQueue();
    }

    private void loadTextures(){
        ArrayList<File> textureFiles = Utils.getAllFilesInFolder("./assets/sprites");
        for(File f : textureFiles){
            textureMap.put(Utils.removeFileExtensionFromName(f.getName()), new Texture(f.getPath()));
        }
    }

    private void setupDrawingQueue(){
        for (DrawingLayers layer : DrawingLayers.values()){
            drawingQueue.put(layer, new HashMap<>());
        }
    }


    /**
     * Redraws drawing queue, should be called every frame
     */
    public void renderUpdate(){
        spriteBatch.begin();

        ScreenUtils.clear(backgroundColor);

        for (HashMap<Integer, ArrayList<RenderData>> layer : drawingQueue.values()){
            for (ArrayList<RenderData> row : layer.values()){
                for (RenderData data : row){
                    renderRenderData(data);
                }
                row.clear();
            }
        }
        // experimental garbage collector call
        System.gc();

        spriteBatch.end();
    }

    /**
     * Unloads textures from memory and disposes of objects
     */
    public void dispose(){
        spriteBatch.dispose();
        for (Texture t : textureMap.values()){
            t.dispose();
        }
    }

    private void renderRenderData(RenderData data){
        spriteBatch.setColor(data.getColor());
        Texture t = textureMap.get(data.getTextureName());
        spriteBatch.draw(
                t,
                data.getPosition().x, data.getPosition().y,
                0, 0,
                t.getWidth(), t.getHeight(),
                data.getScale(), data.getScale(),
                data.getRotation(),
                0, 0,
                t.getWidth(), t.getHeight(),
                data.getFlip().flipX,
                data.getFlip().flipY
                );
    }

    /**
     * Sets background color
     * @param backgroundColor new background color
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Draws a texture
     * will be drawn over if not called next frame
     * @param textureName file name of the texture
     * @param position position for the texture (affected by camera)
     * @param flip how should the texture be flipped
     * @param rotation texture rotation in radians
     * @param scale how big should the texture be (1.0 = default)
     * @param color texture tint (1.0, 1.0, 1.0, 1.0 = no tint)
     * @param layer on which layer should texture be drawn
     * @param priority higher priority textures draw over lower priority ones if on the same layer
     */
    public void drawTexture(String textureName, Vector2 position, FlipDirection flip, float rotation, float scale, Color color, DrawingLayers layer, int priority){

        HashMap<Integer, ArrayList<RenderData>> layerQueue = drawingQueue.get(layer);
        if (!layerQueue.containsKey(priority)){
            layerQueue.put(priority, new ArrayList<>());
        }

        layerQueue.get(priority).add(new RenderData(textureName, position, flip, rotation, scale, color));
    }
    private final Color WHITE = new Color(1, 1, 1, 1);
    private final int DEFAULT_PRIORITY = 0;
    public void drawTexture(String textureName, Vector2 position, DrawingLayers layers){
        drawTexture(textureName, position, FlipDirection.NONE, 0.0f, 1.0f, WHITE, layers, DEFAULT_PRIORITY);
    }
}
