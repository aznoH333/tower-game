package com.tower.game.world;

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


}
