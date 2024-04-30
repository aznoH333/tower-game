package com.tower.game.drawing;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.FileWrapper;
import com.tower.game.utils.Utils;

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
    private final HashMap<Integer, WorldViewport> viewports;
    private final int WORLD_WIDTH = 304;
    private final int WORLD_HEIGHT = 304;
    private final int MAX_HUD_WIDTH = 96;
    private ShaderProgram ambientShader;
    private Color backgroundColor = new Color(0.13333f,0.101960784f, 0.098039216f, 1.0f);



    public Drawing(){
        spriteBatch = new SpriteBatch();
        textureMap = new HashMap<>();
        loadTextures();
        viewports = new HashMap<>();
        initViewPorts();
        setupDrawingQueue();
        initShaders();

    }

    private void initViewPorts(){
        // game view port
        FitViewport gameViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        gameViewport.getCamera().position.x = (float) WORLD_WIDTH / 2;
        gameViewport.getCamera().position.y = (float) WORLD_HEIGHT / 2;
        viewports.put(WorldViewportType.GAME.index, new WorldViewport(gameViewport));

        // hud left viewport
        Viewport hudLeftViewport = new FillViewport(WORLD_WIDTH,WORLD_HEIGHT);
        hudLeftViewport.getCamera().position.x = (float) WORLD_WIDTH / 2;
        hudLeftViewport.getCamera().position.y = (float) WORLD_HEIGHT / 2;
        viewports.put(WorldViewportType.HUD_LEFT.index, new WorldViewport(hudLeftViewport));

        // hud right
        Viewport hudRightViewport = new FillViewport(WORLD_WIDTH,WORLD_HEIGHT);
        hudRightViewport.getCamera().position.x = (float) WORLD_WIDTH / 2;
        hudRightViewport.getCamera().position.y = (float) WORLD_HEIGHT / 2;
        viewports.put(WorldViewportType.HUD_RIGHT.index, new WorldViewport(hudRightViewport));
    }

    private void initShaders(){
        ambientShader = new ShaderProgram(new FileHandle("./assets/gamedata/shaders/default.glsl"), new FileHandle("./assets/gamedata/shaders/ambientColor.fs"));
        if (ambientShader.isCompiled()){
            spriteBatch.setShader(ambientShader);
            ambientShader.bind();

            return;
        }
        DebugUtils.fatalCrash("Shader compilation failed : " + ambientShader.getLog());
    }
    private void loadTextures(){
        ArrayList<File> textureFiles = Utils.getAllFilesInFolder("./assets/sprites");
        for(File f : textureFiles){
            textureMap.put(Utils.removeFileExtensionFromName(f.getName()), new Texture(f.getPath()));
        }
    }

    private void setupDrawingQueue(){
        for (WorldViewport port : viewports.values()){
            port.setupDrawingQueue();
        }
    }


    /**
     * Redraws drawing queue, should be called every frame
     */
    public void renderUpdate(){
        spriteBatch.begin();



        ScreenUtils.clear(backgroundColor);
        // scuffed garbage, done to ensure that viewports are draw in the correct order
        for (int type : viewports.keySet()){
            renderViewPort(viewports.get(type));
        }

        // experimental garbage collector call
        System.gc();

        spriteBatch.end();
    }

    private void renderViewPort(WorldViewport viewport){
        spriteBatch.setProjectionMatrix(viewport.getViewport().getCamera().combined);
        viewport.getViewport().apply();
        for (HashMap<Integer, ArrayList<RenderData>> layer : viewport.getDrawingQueue().values()){
            for (ArrayList<RenderData> row : layer.values()){
                for (RenderData data : row){
                    renderRenderData(data);
                }
                row.clear();
            }
        }
    }

    /**
     * Unloads textures from memory and disposes of objects
     */
    public void dispose(){
        spriteBatch.dispose();
        for (Texture t : textureMap.values()){
            t.dispose();
        }
        ambientShader.dispose();
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

    private final WorldViewportType DEFAULT_VIEWPORT = WorldViewportType.GAME;
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
     * @param viewportType to which viewport should sprite be drawn (default = game)
     */
    public void drawTexture(String textureName, Vector2 position, FlipDirection flip, float rotation, float scale, Color color, DrawingLayers layer, int priority, WorldViewportType viewportType){
        HashMap<Integer, ArrayList<RenderData>> layerQueue = viewports.get(viewportType.index).getDrawingQueue().get(layer);
        if (!layerQueue.containsKey(priority)){
            layerQueue.put(priority, new ArrayList<>());
        }

        layerQueue.get(priority).add(new RenderData(textureName, position, flip, rotation, scale, color));
    }
    private final Color WHITE = new Color(1, 1, 1, 1);
    private final int DEFAULT_PRIORITY = 0;

    /**
     * Simplified version of drawTexture, draws to game
     * @param textureName name of texture
     * @param position in world position
     * @param layers layer to draw on
     */
    public void drawTexture(String textureName, Vector2 position, DrawingLayers layers){
        drawTexture(textureName, position, FlipDirection.NONE, 0.0f, 1.0f, WHITE, layers, DEFAULT_PRIORITY, DEFAULT_VIEWPORT);
    }

    /**
     * Simplified version of drawTexture, draws to specified viewport
     * @param textureName name of texture
     * @param position in world position
     * @param direction how should sprite be flipped
     * @param layer layer to draw on
     * @param type viewport to draw to
     */
    public void drawTexture(String textureName, Vector2 position, FlipDirection direction, DrawingLayers layer, WorldViewportType type){
        drawTexture(textureName, position, direction, 0.0f, 1.0f, WHITE, layer, DEFAULT_PRIORITY, type);

    }

    public void resize(int width, int height) {
        // shitty hardcoded logic
        Viewport gameViewport = viewports.get(WorldViewportType.GAME.index).getViewport();
        gameViewport.update(width, height);

        Viewport leftViewport = viewports.get(WorldViewportType.HUD_LEFT.index).getViewport();
        leftViewport.update(gameViewport.getLeftGutterWidth(), height);
        // fuck this bullshit fr fr
        leftViewport.setScreenPosition(gameViewport.getLeftGutterWidth() - (gameViewport.getScreenWidth() / 20),0);

        Viewport rightViewport = viewports.get(WorldViewportType.HUD_RIGHT.index).getViewport();
        rightViewport.update(gameViewport.getRightGutterWidth(), height);
        rightViewport.setScreenPosition(gameViewport.getRightGutterX(), 0);

        // update shader values


        ambientShader.setUniformf("u_windowWidth", width);
        ambientShader.setUniformf("u_windowHeight", height);
    }
}
