package com.tower.game.objects.world;

import com.tower.game.objects.world.objects.Door;
import com.tower.game.utils.DebugUtils;
import com.tower.game.world.TowerFloor;
import com.tower.game.world.World;
import com.tower.game.world.enums.WorldDirection;

import java.util.ArrayList;

public class WorldObjectManager {
    private static WorldObjectManager instance;
    public static WorldObjectManager getInstance(){
        if (instance == null) instance = new WorldObjectManager();
        return instance;
    }

    private final ArrayList<WorldObject> worldObjects = new ArrayList<WorldObject>();

    public void clearObjects(){
        worldObjects.clear();
    }

    public void addObject(WorldObject object){
        worldObjects.add(object);
    }

    public void onRoomEntry(boolean firstEntry){
        for (WorldObject o : worldObjects){
            o.onFirstRoomEntry();
            if (firstEntry){
                o.onFirstRoomEntry();
            }
        }
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
        }
        DebugUtils.fatalCrash("Unknown object id : " + id);
    }
}
