package com.tower.game.objects.world;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.objects.world.objects.Door;
import com.tower.game.objects.world.objects.Player;
import com.tower.game.utils.DebugUtils;
import com.tower.game.world.TowerFloor;
import com.tower.game.world.World;
import com.tower.game.world.enums.WorldDirection;

import java.util.ArrayList;
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
}
