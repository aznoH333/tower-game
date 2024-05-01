package com.tower.game.ui.hud;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.drawing.FlipDirection;
import com.tower.game.drawing.WorldViewportType;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.GameConstants;
import com.tower.game.world.RoomContents;

import javax.swing.text.Position;

public class Hud {
    private static Hud instance;


    public static Hud getInstance(){
        if (instance == null) instance = new Hud();
        return instance;
    }

    public void update(){
        renderLeftSide();
        renderRightSide();
    }

    private void renderLeftSide(){
        Drawing drawing = Drawing.getInstance();


        for (int i = 0; i < 20; i++){
            drawing.drawTexture("side_bar_1", new Vector2(gutterWidth, i * 16), FlipDirection.VERTICAL, DrawingLayers.FLOOR, WorldViewportType.HUD_LEFT);
        }
    }

    private void renderRightSide(){
        Drawing drawing = Drawing.getInstance();

        for (int i = 0; i < GameConstants.TILES_IN_ROOM; i++){
            drawing.drawTexture("side_bar_1", new Vector2(0, i * 16), FlipDirection.NONE, DrawingLayers.FLOOR, WorldViewportType.HUD_RIGHT);
        }
    }
    private float gutterWidth = 0;
    public void setGutterWidth(float gutterWidth) {
        this.gutterWidth = gutterWidth;
    }

}
