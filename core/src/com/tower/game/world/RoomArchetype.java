package com.tower.game.world;

public enum RoomArchetype {
    NORMAL("normal", "minimap_markers_1");

    public final String layoutFolderName;
    public final String mapMarker;

    RoomArchetype(String layoutFolderName, String mapMarker){
        this.layoutFolderName = layoutFolderName;
        this.mapMarker = mapMarker;
    }
}
