package com.tower.game.world;

import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;

public class TowerFloor {
    private final HashMap<FloorCoordinates, TowerRoom> rooms;
    private FloorCoordinates currentCoordinates;
    private Color backgroundColor = new Color(0.090196078f,0.105882353f,0.101960784f,1.0f);

    public TowerFloor(){
        // temporary floor generation
        rooms = new HashMap<>();
        currentCoordinates = new FloorCoordinates(0,0);

        rooms.put(new FloorCoordinates(0, 0), new TowerRoom());
        rooms.put(new FloorCoordinates(0, 1), new TowerRoom());
        rooms.put(new FloorCoordinates(0, 2), new TowerRoom());
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
        currentCoordinates = new FloorCoordinates(currentCoordinates.getX() + x, currentCoordinates.getY() + y);
    }

    public void render(){

        rooms.get(currentCoordinates).renderRoom(0, 0);
    }
}
