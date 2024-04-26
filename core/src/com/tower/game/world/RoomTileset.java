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
                tileSprites.put(WorldTile.FLOOR, "floor");
                tileSprites.put(WorldTile.WALL, "wall");
                break;
        }
    }

}
