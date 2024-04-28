package com.tower.game.world;

public enum FloorLevel {
    FLOOR_1("floor_1",new RoomTileset(TilesetType.GREEN_DEFAULT));


    public final String layoutFileFolderName;
    public final RoomTileset defaultRoomTileset;

    FloorLevel(String layoutFileFolderName, RoomTileset defaultRoomTileset){
        this.defaultRoomTileset = defaultRoomTileset;
        this.layoutFileFolderName = layoutFileFolderName;
    }
}
