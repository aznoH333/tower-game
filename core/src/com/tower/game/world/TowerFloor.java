package com.tower.game.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.objects.world.WorldObjectManager;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.GameConstants;
import com.tower.game.utils.UniversalTimer;
import com.tower.game.utils.Utils;
import com.tower.game.world.enums.FloorLevel;
import com.tower.game.world.enums.RoomArchetype;
import com.tower.game.world.enums.WorldDirection;
import com.tower.game.world.enums.WorldTile;

import java.util.HashMap;

public class TowerFloor {
    private final HashMap<FloorCoordinates, TowerRoom> rooms;
    private FloorCoordinates currentCoordinates;
    private FloorCoordinates targetCoordinates;
    private Color backgroundColor = new Color(0.090196078f,0.105882353f,0.101960784f,1.0f);
    private final UniversalTimer transitionTimer = new UniversalTimer(80);
    private int transitionX;
    private int transitionY;
    private float cameraOffsetX = 0.0f;
    private float cameraOffsetY = 0.0f;
    private float fakeBackgroundX = 0.0f;
    private float fakeBackgroundY = 0.0f;
    private boolean repeatedRoomEntryFlag = false;
    private boolean hasBeenInitialized = false;

    public TowerFloor(){
        // temporary floor generation
        rooms = new HashMap<>();
        currentCoordinates = new FloorCoordinates(0,0);

        rooms.put(new FloorCoordinates(0, 0), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(0, 1), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(-1, 1), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(0, 2), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(1, 2), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(2, 2), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(3, 2), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(4, 2), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
        rooms.put(new FloorCoordinates(5, 2), RoomGenerator.getInstance().getRoom(FloorLevel.FLOOR_1, RoomArchetype.NORMAL));
    }

    public TowerRoom getCurrentRoom(){
        return rooms.get(currentCoordinates);
    }

    public void init(){
        hasBeenInitialized = true;
        enteredRoom();
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
        if (!hasBeenInitialized){
            DebugUtils.fatalCrash("Tower floor needs to be initialized first");
        }
        rooms.get(currentCoordinates).renderRoom(cameraOffsetX, cameraOffsetY);

        // transition
        transitionTimer.progress();

        if (!transitionTimer.isReady()){
            drawFakeBackground(fakeBackgroundX - GameConstants.ROOM_SIZE * transitionX, fakeBackgroundY - GameConstants.ROOM_SIZE * transitionY);

            // transition is happening
            cameraOffsetX = Utils.smoothStep(transitionTimer.getAsPercentage()) * transitionX * GameConstants.ROOM_SIZE * 2;
            cameraOffsetY = Utils.smoothStep(transitionTimer.getAsPercentage()) * transitionY * GameConstants.ROOM_SIZE * 2;
            fakeBackgroundX = cameraOffsetX;
            fakeBackgroundY = cameraOffsetY;

            if (transitionTimer.getAsPercentage() >= 0.5){
                currentCoordinates = targetCoordinates;
                cameraOffsetX -= GameConstants.ROOM_SIZE * transitionX * 2;
                cameraOffsetY -= GameConstants.ROOM_SIZE * transitionY * 2;
                enteredRoom();
            }
        }else {
            repeatedRoomEntryFlag = false;
        }

    }
    public float getCameraOffsetX() {
        return cameraOffsetX;
    }

    public float getCameraOffsetY() {
        return cameraOffsetY;
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

    public HashMap<FloorCoordinates, TowerRoom> getRooms() {
        return rooms;
    }
    private void enteredRoom(){
        if (repeatedRoomEntryFlag){
            return;
        }
        repeatedRoomEntryFlag = true;

        TowerRoom currentRoom = rooms.get(currentCoordinates);

        WorldObjectManager w = WorldObjectManager.getInstance();
        w.clearObjects();
        // add objects from room
        currentRoom.spawnEntities();

        w.onRoomEntry(!rooms.get(currentCoordinates).isEntered());

        rooms.get(currentCoordinates).markAsEntered();

        // mark nearby as discovered
        markAsDiscovered(-1,0);
        markAsDiscovered(1,0);
        markAsDiscovered(0,1);
        markAsDiscovered(0,-1);

    }

    private void markAsDiscovered(int x, int y){
        FloorCoordinates target = new FloorCoordinates(currentCoordinates.getX() + x, currentCoordinates.getY() + y);
        if (rooms.containsKey(target)){
            rooms.get(target).markAsDiscovered();
        }
    }

    /**
     * Checks if room exists (based on current floor coordinates)
     * @param x coordinate
     * @param y coordinate
     * @return does room exist at (currentX + x, currentY + y)
     */
    public boolean checkIfRoomExists(int x, int y){
        return rooms.containsKey(new FloorCoordinates(currentCoordinates.getX() + x, currentCoordinates.getY() + y));
    }

    /**
     * get the distance to the closest wall
     * @param x starting x
     * @param y starting y
     * @param direction search direction
     * @return distance to the closest wall in specified direction (ignores objects)
     */
    public int findDistanceToNextWall(int x, int y, WorldDirection direction){
        int result = 0;
        int searchX = x;
        int searchY = y;
        TowerRoom currentRoom = getCurrentRoom();
        while (!currentRoom.isTileWall(searchX, searchY)){
            searchX += direction.x;
            searchY += direction.y;
            result++;
        }
        result--;
        return result;
    }
}
