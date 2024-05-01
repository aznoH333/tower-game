package com.tower.game.ui.hud;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.drawing.FlipDirection;
import com.tower.game.drawing.WorldViewportType;
import com.tower.game.world.FloorCoordinates;
import com.tower.game.world.TowerRoom;
import com.tower.game.world.World;

import java.util.HashMap;
import java.util.Map;

public class Minimap {

    private final Drawing drawing;
    private final World world;
    private final int MAX_DRAW_DISTANCE = 2;
    private final int MAP_MARKER_SIZE = 6;
    private final int CENTER_OFFSET = 14;
    public Minimap(){
        this.drawing = Drawing.getInstance();
        this.world = World.getInstance();
    }

    public void draw(){
        drawing.drawTexture("minimap_frame", new Vector2(0,0), FlipDirection.NONE, DrawingLayers.FLOOR, WorldViewportType.HUD_MINIMAP);

        HashMap<FloorCoordinates, TowerRoom> rooms = world.getCurrentFloor().getRooms();
        FloorCoordinates coordinates = world.getCurrentFloor().getCurrentCoordinates();

        for (Map.Entry<FloorCoordinates, TowerRoom> room : rooms.entrySet()){
            if (Math.abs(room.getKey().getX() - coordinates.getX()) <= MAX_DRAW_DISTANCE && Math.abs(room.getKey().getY() - coordinates.getY()) <= MAX_DRAW_DISTANCE){
                drawCell(room);
            }
        }
    }

    private void drawCell(Map.Entry<FloorCoordinates, TowerRoom> room){
        if (!room.getValue().isDiscovered()){
            return;
        }
        FloorCoordinates coordinates = world.getCurrentFloor().getCurrentCoordinates();

        String sprite = getCellSprite(room);
        Vector2 position = new Vector2((room.getKey().getX() - coordinates.getX()) * MAP_MARKER_SIZE + CENTER_OFFSET, (room.getKey().getY() - coordinates.getY()) * MAP_MARKER_SIZE + CENTER_OFFSET);
        drawing.drawTexture(sprite, position, FlipDirection.NONE, DrawingLayers.WALLS, WorldViewportType.HUD_MINIMAP);
    }

    private String getCellSprite(Map.Entry<FloorCoordinates, TowerRoom> room){
        if (room.getKey().equals(world.getCurrentFloor().getCurrentCoordinates())){
            return "minimap_markers_2";
        }
        return room.getValue().getMinimapSprite();
    }
}
