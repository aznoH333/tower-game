package com.tower.game.ui.hud;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.drawing.FlipDirection;
import com.tower.game.drawing.WorldViewportType;
import com.tower.game.utils.GameConstants;

public class Hud {
    private static Hud instance;


    public static Hud getInstance(){
        if (instance == null) instance = new Hud();
        return instance;
    }

    private final Minimap minimap;
    public Hud(){
        this.minimap = new Minimap();
    }

    public void update(){
        renderLeftSide();
        renderRightSide();
    }

    private void renderLeftSide(){
        Drawing drawing = Drawing.getInstance();


        for (int i = 0; i < 20; i++){
            drawing.drawTexture("side_bar_1", new Vector2(gutterWidth - 16, i * GameConstants.TILE_SIZE), FlipDirection.VERTICAL, DrawingLayers.WALLS, WorldViewportType.HUD_LEFT);
        }

        for (int i = 0; i < Math.ceil(gutterWidth / GameConstants.TILE_SIZE) - 1; i++){
            for (int j = 0; j < GameConstants.TILES_IN_ROOM; j++){
                drawing.drawTexture("side_bar_2", new Vector2(i * GameConstants.TILE_SIZE, j * GameConstants.TILE_SIZE), FlipDirection.NONE, DrawingLayers.FLOOR, WorldViewportType.HUD_LEFT);
            }
        }

    }

    private void renderRightSide(){
        Drawing drawing = Drawing.getInstance();

        for (int i = 0; i < GameConstants.TILES_IN_ROOM; i++){
            drawing.drawTexture("side_bar_1", new Vector2(0, i * GameConstants.TILE_SIZE), FlipDirection.NONE, DrawingLayers.FLOOR, WorldViewportType.HUD_RIGHT);
        }

        // draw rest of tiles
        for (int i = 1; i < Math.ceil(gutterWidth / GameConstants.TILE_SIZE); i++){
            for (int j = 0; j < GameConstants.TILES_IN_ROOM; j++){
                drawing.drawTexture("side_bar_2", new Vector2(i * GameConstants.TILE_SIZE, j * GameConstants.TILE_SIZE), FlipDirection.NONE, DrawingLayers.FLOOR, WorldViewportType.HUD_RIGHT);
            }
        }

        // render minimap
        minimap.draw();
    }
    private float gutterWidth = 0;
    public void setGutterWidth(float gutterWidth) {
        this.gutterWidth = gutterWidth;
    }

}
