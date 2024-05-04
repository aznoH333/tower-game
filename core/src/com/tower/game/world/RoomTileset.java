package com.tower.game.world;

import com.tower.game.world.enums.TilesetType;
import com.tower.game.world.enums.WorldTile;

import java.util.HashMap;

public class RoomTileset {
    private final HashMap<WorldTile, TileVariations> tileSprites;

    public String getSpriteForTile(WorldTile tile, int tileRandomness){
        return tileSprites.get(tile).getSprite(tileRandomness);
    }

    public RoomTileset(TilesetType type){
        tileSprites = new HashMap<>();
        switch (type){
            case GREEN_DEFAULT:
                TileVariations temp = new TileVariations("green_dungeon_tiles_1");
                temp.addSprite("green_dungeon_tiles_2");
                temp.addSprite("green_dungeon_tiles_3");


                tileSprites.put(WorldTile.FLOOR, temp);
                tileSprites.put(WorldTile.NONE, new TileVariations("green_dungeon_tiles_28"));
                loadGenericTileset("green_dungeon_tiles_", 4);
                break;
        }
    }

    private void loadGenericTileset(String name, int startIndex){
        for (int i = 0; i < 24; i++){
            tileSprites.put(WorldTile.values()[i + 2], new TileVariations(name + (startIndex + i)));
        }
    }

}
