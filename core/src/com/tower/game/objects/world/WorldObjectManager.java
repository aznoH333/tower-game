package com.tower.game.objects.world;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.objects.entites.Entity;
import com.tower.game.objects.world.objects.Door;
import com.tower.game.objects.world.objects.Player;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.GameConstants;
import com.tower.game.utils.IVec;
import com.tower.game.world.TowerFloor;
import com.tower.game.world.World;
import com.tower.game.world.enums.WorldDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class WorldObjectManager {
    private static WorldObjectManager instance;
    private Vector2 playerSpawnLocation = new Vector2(0,0);
    public static WorldObjectManager getInstance(){
        if (instance == null) instance = new WorldObjectManager();
        return instance;
    }

    private final ArrayList<WorldObject> worldObjects = new ArrayList<WorldObject>();

    public void clearObjects(){
        worldObjects.clear();
    }

    public void addObject(WorldObject object){
        if (object.canExist()){
            worldObjects.add(object);
        }
    }

    public void onRoomEntry(boolean firstEntry){
        playerSpawnLocation = new Vector2(0,0);

        for (WorldObject o : worldObjects){

            o.onRoomEnter();
            if (firstEntry){
                o.onFirstRoomEntry();
            }
        }
        // spawn player
        addObject(new Player(playerSpawnLocation, World.getInstance().getCurrentFloor().getLastMoveDirection()));
    }

    public void update(){
        TowerFloor currentFloor = World.getInstance().getCurrentFloor();

        for (WorldObject o : worldObjects){
            o.update(currentFloor.getCameraOffsetX(), currentFloor.getCameraOffsetY());
        }
    }

    public void spawnObjectFromId(int x, int y, int id){
        ObjectIds type = ObjectIds.values()[id];

        switch (type){
            case DOOR_UP:       addObject(new Door(x, y, WorldDirection.UP)); return;
            case DOOR_DOWN:     addObject(new Door(x, y, WorldDirection.DOWN)); return;
            case DOOR_LEFT:     addObject(new Door(x, y, WorldDirection.LEFT)); return;
            case DOOR_RIGHT:    addObject(new Door(x, y, WorldDirection.RIGHT)); return;
            case GENERIC_ENEMY_SPAWN: DebugUtils.warn("Warning enemy spawners not implemented! \n x = " + x + ", y = " + y);return;
        }
        DebugUtils.fatalCrash("Unknown object id : " + id);
    }

    public Vector2 getPlayerSpawnLocation() {
        return playerSpawnLocation;
    }

    public void setPlayerSpawnLocation(Vector2 playerSpawnLocation) {
        this.playerSpawnLocation = playerSpawnLocation;
    }

    public int getMaxPossibleMoveDistance(int x, int y, WorldDirection direction){
        int result = GameConstants.TILES_IN_ROOM;

        for(WorldObject object : worldObjects){
            if (object.blocksMovement &&
                    ((direction.x * (x - object.getInWorldX()) < 0) || (direction.x == 0 && x == object.getInWorldX())) &&
                    ((direction.y * (y - object.getInWorldY()) < 0) || (direction.y == 0 && y == object.getInWorldY()))
            ){
                result = Math.min(result, Math.max(Math.abs(x - object.getInWorldX()), Math.abs(y - object.getInWorldY()))) - 1;
            }
        }

        return result;
    }

    /**
     * Return object on tile. Returns null if nothing is there
     * @param x in world x
     * @param y in world y
     * @return found object
     */
    public WorldObject getWorldObjectAtPosition(int x, int y){
        for (WorldObject object : worldObjects){
            if (object.getInWorldX() == x && object.getInWorldY() == y){
                return object;
            }
        }
        return null;
    }

    /**
     * Interact with world object
     * @param x tile x
     * @param y tile y
     */
    public boolean interactWithObject(Entity caller, int x, int y){

        WorldObject obj = getWorldObjectAtPosition(x, y);
        if (obj != null){
            obj.onInteract(caller);
        }
        return false;
    }
}
