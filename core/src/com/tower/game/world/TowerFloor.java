package com.tower.game.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.GameConstants;
import com.tower.game.utils.UniversalTimer;
import com.tower.game.utils.Utils;

import java.util.HashMap;

public class TowerFloor {
    private final HashMap<FloorCoordinates, TowerRoom> rooms;
    private FloorCoordinates currentCoordinates;
    private FloorCoordinates targetCoordinates;
    private Color backgroundColor = new Color(0.090196078f,0.105882353f,0.101960784f,1.0f);
    private UniversalTimer transitionTimer = new UniversalTimer(80);
    private int transitionX;
    private int transitionY;

    public TowerFloor(){
        // temporary floor generation
        rooms = new HashMap<>();
        currentCoordinates = new FloorCoordinates(0,0);

        rooms.put(new FloorCoordinates(0, 0), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(0, 1), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(0, 2), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(1, 2), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));

    }

    public TowerRoom getCurrentRoom(){
        return rooms.get(currentCoordinates);
    }

    public FloorCoordinates getCurrentCoordinates(){
        return currentCoordinates;
    }

    /**
     * Moves position in floor
     * @param x adds to current x
     * @param y adds to current y
     */
    public void moveCoordinatesBy(int x, int y){

        if (!transitionTimer.isReady()){
            return;
        }

        FloorCoordinates target = new FloorCoordinates(currentCoordinates.getX() + x, currentCoordinates.getY() + y);
        if (rooms.containsKey(target)){
            DebugUtils.info("Entering room : " + target.getX() +" , " + target.getY());
            transitionAnimation(x, y);
        }else {
            DebugUtils.warn("Attempted to enter non existant floor space : " + target.getX() +" , " + target.getY());
        }
    }

    /**
     * Plays animation for entering a new room
     * @param x moved by (positive moving right negative moving left)
     * @param y moved by (positive moving up negative moving down)
     */
    private void transitionAnimation(int x, int y){
        transitionTimer.reset();
        transitionX = x;
        transitionY = y;
        targetCoordinates = new FloorCoordinates(currentCoordinates.getX() + x, currentCoordinates.getY() + y);
    }

    public void render(){

        // transition
        transitionTimer.progress();
        float xOffset = 0;
        float yOffset = 0;

        if (!transitionTimer.isReady()){
            final int ROOM_SIZE = GameConstants.TILE_SIZE * GameConstants.TILES_IN_ROOM;
            // transition is happening
            xOffset = Utils.smoothStep(transitionTimer.getAsPercentage()) * transitionX * ROOM_SIZE * 2;
            yOffset = Utils.smoothStep(transitionTimer.getAsPercentage()) * transitionY * ROOM_SIZE * 2;
            drawFakeBackground(xOffset - ROOM_SIZE * transitionX, yOffset - ROOM_SIZE * transitionY);

            if (transitionTimer.getAsPercentage() >= 0.5){
                currentCoordinates = targetCoordinates;
                xOffset -= ROOM_SIZE * transitionX * 2;
                yOffset -= ROOM_SIZE * transitionY * 2;
            }
        }



        rooms.get(currentCoordinates).renderRoom(xOffset, yOffset);

    }

    private void drawFakeBackground(float xOffset, float yOffset){
        Drawing d = Drawing.getInstance();
        RoomTileset currentTileset = getCurrentRoom().getTileset();
        for (int x = 0; x < GameConstants.TILES_IN_ROOM; x++){
            for (int y = 0; y < GameConstants.TILES_IN_ROOM; y++){
                d.drawTexture(currentTileset.getSpriteForTile(WorldTile.NONE, 0), new Vector2(x * GameConstants.TILE_SIZE - xOffset, y * GameConstants.TILE_SIZE - yOffset), DrawingLayers.WALLS);
            }
        }
    }
}
