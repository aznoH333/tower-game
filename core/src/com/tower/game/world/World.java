package com.tower.game.world;

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

    public int getMaxPossibleMoveDistance(int x, int y, WorldDirection direction){
        return currentFloor.findDistanceToNextWall(x, y, direction);
    }

}
