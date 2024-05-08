package com.tower.game.world;

import com.tower.game.objects.world.WorldObjectManager;
import com.tower.game.objects.world.WorldSearchResult;
import com.tower.game.utils.DebugUtils;
import com.tower.game.world.enums.WorldDirection;

public class World {
    private static World instance;
    public static World getInstance(){
        if (instance == null) instance = new World();
        return instance;
    }

    private final TowerFloor currentFloor = new TowerFloor();

    public void render(){
        currentFloor.render();
    }

    public TowerFloor getCurrentFloor(){
        return currentFloor;
    }

    public WorldSearchResult getMaxPossibleMoveDistance(int x, int y, WorldDirection direction){
        int tileSearchResult = currentFloor.findDistanceToNextWall(x, y, direction);
        int objectSearchResult = WorldObjectManager.getInstance().getMaxPossibleMoveDistance(x, y, direction);

        return new WorldSearchResult(Math.min(tileSearchResult, objectSearchResult), objectSearchResult <= tileSearchResult);
    }

}
