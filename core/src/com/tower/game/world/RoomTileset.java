package com.tower.game.world;

import java.util.HashMap;

public class RoomTileset {
    private HashMap<WorldTile, String> tileSprites;

    public String getSpriteForTile(WorldTile tile){
        return tileSprites.get(tile);
    }

    public RoomTileset(TilesetType type){
        tileSprites = new HashMap<>();
        switch (type){
            case GREEN_DEFAULT:
                tileSprites.put(WorldTile.FLOOR, "green_dungeon_tiles_3");
                loadGenericTileset("green_dungeon_tiles_", 4);
                break;
        }
    }

    private void loadGenericTileset(String name, int startIndex){
        for (int i = 0; i < 8; i++){
            // TODO : corner tiles
            tileSprites.put(WorldTile.values()[i + 2], name + (startIndex + i));
        }
    }

}
