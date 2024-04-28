package com.tower.game.world;

public enum RoomArchetype {
    NORMAL("normal");

    public final String layoutFolderName;

    RoomArchetype(String layoutFolderName){
        this.layoutFolderName = layoutFolderName;
    }
}
